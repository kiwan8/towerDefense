package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

import src.GameExceptions.GameException;
import src.libraries.StdDraw;

public class Game {
    // Méthode principale pour lancer le jeu

    private static Player joueur;
    private UI ui;
    private static Level currentLevel;
    private static Wave currentWave;
    private static int cptLevel = 0;
    private static int cptWave = 0;
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

    // Initialise le jeu
    private void init() throws GameException, IOException {
        joueur = new Player(200, 10000); // Crée un joueur avec 200 pièces d'or et 100 points de vie

        initLevels("resources/games/game.g");

        currentLevel = levels.get(cptLevel);
        currentWave = currentLevel.getWaves().get(0);

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
    
            if (enemy.hasReachedEnd()) {
                joueur.takeDamage(enemy.getATK());
                iterator.remove();
                continue; // Passe à l'ennemi suivant
            }
    
            if (enemy.getPV() <= 0) {
                joueur.setArgent((int)joueur.getArgent()+enemy.getReward());
                iterator.remove(); // Supprime l'ennemi mort
            }
        }
    }
    

    private void updateEnemyAttacks(double deltaTimeSec) {
        for (Ennemy enemy : activeEnemies) {
            if (!enemy.canAttack(deltaTimeSec)) continue;
    
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

            // Réinitialise les états
            joueur.setHP(10000);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'youWin'");
    }

    private void youLose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'youWin'");
    }

    public static Player getPlayer() { // Getter pour le joueur
        return joueur;
    }
}
