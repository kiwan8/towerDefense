package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

import src.GameExceptions.GameException;
import src.libraries.StdDraw;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private String originalGameFileName;
    // Méthode principale pour lancer le jeu

    private static Player joueur;
    private UI ui;
    private static Level currentLevel;
    private static Wave currentWave;
    private static int cptLevel;
    private static int cptWave;
    private static List<Level> levels = new ArrayList<>();
    private static List<Ennemy> activeEnemies = new ArrayList<>();
    private static List<Tower> activeTower = new ArrayList<>();
    private static long elapsedTime;

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public static int getcptLevel() {
        return cptLevel;
    }

    public static int getcptWave() {
        return cptWave;
    }

    public static List<Level> getLevels() {
        return levels;
    }

    public static List<Ennemy> getActiveEnemies() {
        return activeEnemies;
    }

    public static List<Tower> getActiveTower() {
        return activeTower;
    }

    Long startTime = System.currentTimeMillis();
    private boolean mousePressedLastFrame = false; // Indique si la souris était déjà pressée lors de la dernière frame

    public void launch() throws GameException, IOException {
        init();
        startTime = System.currentTimeMillis(); // Temps de début de la wave
        long previousTime = startTime;
        elapsedTime = 0;

        final int TARGET_FPS = 60; // Objectif de 60 FPS
        final long FRAME_TIME = 1000 / TARGET_FPS; // Temps par frame en millisecondes

        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double) (currentTime - previousTime) / 1000; // Temps écoulé depuis la dernière frame
            previousTime = currentTime;

            // Met à jour le temps total écoulé
            elapsedTime = (long) (currentTime - startTime) / 1000; // Temps écoulé en secondes depuis le début

            // Gestion des clics de souris
            if (StdDraw.isMousePressed()) {
                if (!mousePressedLastFrame) { // Si la souris était relâchée avant ce clic
                    double mouseX = StdDraw.mouseX();
                    double mouseY = StdDraw.mouseY();
                    ui.handleClick(mouseX, mouseY); // Gérer le clic
                    StdDraw.show(); // Met à jour l'affichage
                }
                mousePressedLastFrame = true; // La souris est maintenant considérée comme pressée
            } else {
                mousePressedLastFrame = false; // Réinitialise l'état lorsque la souris est relâchée
            }

            update(deltaTimeSec, elapsedTime); // Met à jour l'état du jeu
            ui.render(); // Redessine l'interface utilisateur

            // Synchronisation pour atteindre 60 FPS
            long frameEndTime = System.currentTimeMillis();
            long frameDuration = frameEndTime - currentTime;

            if (frameDuration < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - frameDuration); // Pause pour compléter le temps restant
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        youLose();
    }

    // Vérifie si le jeu est encore en cours d'exécution
    private boolean isGameRunning() {
        return joueur.isAlive(); // Le jeu est en cours tant que le joueur est en vie
    }

    private void initLevels(String gameFilePath) throws GameException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(gameFilePath))) {
            String levelName;
            
            while ((levelName = reader.readLine()) != null) {
                String levelPath = "resources/levels/" + levelName + ".lvl";
                levels.add(new Level(levelPath));
            }
        }
    }

    private void init() throws GameException, IOException {
        String gameFilePath = "resources/games/game.g";
        originalGameFileName = new File(gameFilePath).getName(); // Récupère le nom du fichier d'origine
    
        if (!loadGameState()) { // Si aucune sauvegarde n'est trouvée, commencez une nouvelle partie
            joueur = new Player(200, 10); // Crée un joueur avec 200 pièces d'or et 100 points de vie
    
            initLevels(gameFilePath); // Charge les niveaux du fichier de jeu spécifié
            cptLevel = 0;
            cptWave = 0;
            currentLevel = levels.get(cptLevel);
            currentWave = currentLevel.getWaves().get(cptWave);
        }
    
        this.ui = new UI(currentLevel.getMap());
    }
    
    // Met à jour l'état du jeu en fonction du temps écoulé
    private void update(double deltaTimeSec, long elapsedTime) throws GameException, IOException {
        // Gestion du spawn des ennemis
        handleEnemySpawning(elapsedTime);

        // Mise à jour de la position des ennemis
        updateEnemies(deltaTimeSec);

        // Gestion des attaques des tours
        updateTowers(deltaTimeSec);

        // Gestion des attaques des ennemis
        updateEnemyAttacks(deltaTimeSec);

        // Vérification de la fin de la vague
        checkWaveCompletion();
    }

    private void handleEnemySpawning(long elapsedTime) {
        while (!currentWave.getEnemies().isEmpty() &&
                currentWave.getEnemies().peek().getSpawnTime() <= elapsedTime) {
            activeEnemies.add(currentWave.getEnemies().poll());
        }
    }

    private void updateEnemies(double deltaTimeSec) {
        Iterator<Ennemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Ennemy enemy = iterator.next();
            enemy.updatePosition(deltaTimeSec);

            // Met à jour l'effet de poison
            enemy.updatePoison(deltaTimeSec);

            if (enemy.hasReachedEnd()) {
                joueur.takeDamage(enemy.getATK());
                iterator.remove();
                continue; // Passe à l'ennemi suivant
            }

            if (enemy.getPV() <= 0) {
                joueur.setArgent((int) joueur.getArgent() + enemy.getReward());
                iterator.remove(); // Supprime l'ennemi mort
            }
        }
    }

    private void updateEnemyAttacks(double deltaTimeSec) {
        for (Ennemy enemy : activeEnemies) {
            if (!enemy.canAttack(deltaTimeSec))
                continue;

            List<Warrior> towersInRange = activeTower.stream()
                    .filter(tower -> enemy.calculateDistance(tower) <= enemy.getRange())
                    .collect(Collectors.toList());

            List<Warrior> targets = enemy.selectTargets(enemy.getModeAttaque(), towersInRange);
            for (Warrior target : targets) {
                enemy.attaquer(target);
            }

            enemy.resetAttackCooldown();
        }
    }

    private void updateTowers(double deltaTimeSec) {
        Iterator<Tower> iterator = activeTower.iterator();
        while (iterator.hasNext()) {
            Tower tower = iterator.next();
            // Vérifie si la tour est morte
            if (tower.getPV() <= 0) {
                tower.getPosition().setOccupiedByTower(false);
                iterator.remove(); // Supprime la tour morte
                continue; // Passe à la tour suivante
            }

            // Vérifie le cooldown pour attaquer
            if (!tower.canAttack(deltaTimeSec)) {
                continue;
            }

            // Récupère les ennemis dans la portée
            List<Warrior> enemiesInRange = activeEnemies.stream()
                    .filter(enemy -> tower.calculateDistance(enemy) <= tower.getRange())
                    .collect(Collectors.toList());

            // Sélectionne les cibles selon le mode d'attaque
            List<Warrior> targets = tower.selectTargets(tower.getModeAttaque(), enemiesInRange);

            // Attaque les cibles sélectionnées
            for (Warrior target : targets) {
                tower.attaquer(target);
            }

            // Réinitialise le cooldown de la tour
            tower.resetAttackCooldown();
        }
    }

    private void checkWaveCompletion() throws GameException, IOException {
        if (currentWave.getEnemies().isEmpty() && activeEnemies.isEmpty()) {
            nextWave();
        }
    }

    private void nextWave() throws GameExceptions.GameException, IOException {
        int nbVagueDuLevel = currentLevel.getWaves().size();

        // Si on doit changer de niveau
        if (cptWave == nbVagueDuLevel - 1) {
            if (cptLevel == levels.size() - 1) { // Si on a fini tous les niveaux
                youWin();
                return;
            }
            // Passe au niveau suivant
            cptLevel++;
            currentLevel = levels.get(cptLevel);
            cptWave = 0;
            currentWave = currentLevel.getWaves().get(cptWave);

            activeEnemies.clear(); // Vide les ennemis actifs
            activeTower.clear();
            ui.updateMap(currentLevel.getMap());
        } else {
            // Passe à la vague suivante
            cptWave++;
            currentWave = currentLevel.getWaves().get(cptWave);
        }
        // Réinitialise le temps écoulé pour la nouvelle vague
        elapsedTime = 0;
        this.startTime = System.currentTimeMillis();
    }

    private void youWin() {
        StdDraw.clear();
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
        StdDraw.text(512, 360, "YOU WIN!");
        StdDraw.show();

        // Supprime le fichier de sauvegarde associé
        File saveFile = new File("resources/saved/gameState.sav");
        if (saveFile.exists()) {
            saveFile.delete();
            System.out.println("Game save deleted as the game is finished.");
        }
    }

    private void youLose() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
        StdDraw.text(512, 360, "YOU LOSE");
        StdDraw.show();

        joueur.setHP(200);

        saveGameState(); // Sauvegarde l'état actuel du jeu

    }

    private void saveGameState() {
        try {
            // Assurez-vous que le dossier existe
            File savedDir = new File("resources/saved");
            if (!savedDir.exists()) {
                savedDir.mkdirs(); // Crée le dossier si nécessaire
            }
    
            // Utilise le nom du fichier d'origine pour la sauvegarde
            String saveFileName = "resources/saved/" + originalGameFileName.replace(".g", ".sav");
    
            // Sauvegarde l'état du jeu
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFileName))) {
                oos.writeObject(joueur);            // Sauvegarde l'état du joueur
                oos.writeInt(cptLevel);            // Sauvegarde le niveau actuel
                oos.writeInt(cptWave);             // Sauvegarde la vague actuelle
                oos.writeObject(originalGameFileName); // Sauvegarde le nom du fichier d'origine
                System.out.println("Game state saved successfully in " + saveFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save game state.");
        }
    }
    

    private boolean loadGameState() {
        // Chemin du fichier de sauvegarde basé sur le fichier de jeu actuel
        String saveFileName = "resources/saved/" + originalGameFileName.replace(".g", ".sav");
    
        File saveFile = new File(saveFileName);
        if (!saveFile.exists()) {
            return false; // Aucun fichier de sauvegarde trouvé
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            joueur = (Player) ois.readObject();           // Charge l'état du joueur
            cptLevel = ois.readInt();                    // Charge le niveau
            cptWave = ois.readInt();                     // Charge la vague
            originalGameFileName = (String) ois.readObject(); // Charge le nom du fichier d'origine
    
            initLevels("resources/games/" + originalGameFileName); // Dynamise les niveaux depuis le fichier
            currentLevel = levels.get(cptLevel);
            currentWave = currentLevel.getWaves().get(cptWave);
            this.ui = new UI(currentLevel.getMap());
            System.out.println("Game state loaded successfully from " + saveFileName);
            return true;
        } catch (IOException | ClassNotFoundException | GameException e) {
            e.printStackTrace();
            System.err.println("Failed to load game state.");
            return false;
        }
    }
    

    public static Player getPlayer() { // Getter pour le joueur
        return joueur;
    }
}