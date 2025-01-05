package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import src.GameExceptions.GameException;
import src.Monsters.Bomb;
import src.Monsters.Healer;
import src.Monsters.MerchantKing;
import src.Monsters.Termiernator;
import src.Towers.Railgun;
import src.libraries.StdDraw;

/**
 * La classe Game représente le jeu principal.
 * Elle gère les niveaux, les vagues, les ennemis actifs, les tours actives et
 * le temps écoulé.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Le joueur du jeu.
     */
    private static Player joueur;

    /**
     * Le niveau actuel du jeu.
     */
    private static Level currentLevel;

    /**
     * La vague actuelle du jeu.
     */
    private static Wave currentWave;

    /**
     * Le compteur de niveaux.
     */
    private static int cptLevel;

    /**
     * Le compteur de vagues.
     */
    private static int cptWave;

    /**
     * La liste des niveaux du jeu.
     */
    private static List<Level> levels = new ArrayList<>();

    /**
     * La liste des ennemis actifs dans le jeu.
     */
    private static List<Ennemy> activeEnemies = new ArrayList<>();

    /**
     * La liste des tours actives dans le jeu.
     */
    private static List<Tower> activeTower = new ArrayList<>();

    /**
     * Le temps écoulé depuis le début du jeu.
     */
    private static double elapsedTime;

    /**
     * File d'attente des MerchantKings en attente.
     */
    private static Queue<MerchantKing> merchantKingQueue = new LinkedList<>();

    /**
     * Nombre de bonus cumulés via Merchant King.
     */
    private static int merchantKingBonuses = 0;

    /**
     * Retourne le niveau actuel du jeu.
     *
     * @return Le niveau actuel.
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Retourne le compteur de niveaux.
     *
     * @return Le compteur de niveaux.
     */
    public static int getcptLevel() {
        return cptLevel;
    }

    /**
     * Retourne le compteur de vagues.
     *
     * @return Le compteur de vagues.
     */
    public static int getcptWave() {
        return cptWave;
    }

    /**
     * Retourne la liste des niveaux du jeu.
     *
     * @return La liste des niveaux.
     */
    public static List<Level> getLevels() {
        return levels;
    }

    /**
     * Retourne la liste des ennemis actifs dans le jeu.
     *
     * @return La liste des ennemis actifs.
     */
    public static List<Ennemy> getActiveEnemies() {
        return activeEnemies;
    }

    /**
     * Retourne la liste des tours actives dans le jeu.
     *
     * @return La liste des tours actives.
     */
    public static List<Tower> getActiveTower() {
        return activeTower;
    }

    public static MerchantKing PeekMerchantKingQueue() {
        return Game.merchantKingQueue.peek();
    }

    /**
     * Retourne le nombre de bonus cumulés via Merchant King.
     *
     * @return Le nombre de bonus.
     */
    public static int getMerchantKingBonuses() {
        return merchantKingBonuses;
    }

    /**
     * Applique un bonus en fonction de celui choisi par le joueur 
     *
     * @param bonusType Le type de bonus à appliquer (1: ATK Tours, 2: Vitesse
     *                  ennemis, 3: Vitesse ATK Tours, 4: Pièces).
     */
    public static void applyMerchantKingBonus(int bonusType) {
        if (merchantKingBonuses >= 5) {
            merchantKingQueue.poll();
            return;
        }

        switch (bonusType) {
            case 1:
                // +10% de puissance d'attaque sur toutes les tours
                Tower.setBaseAttackMultiplier(Tower.getBaseAttackMultiplier() * 1.1);
                Game.getActiveTower().forEach(t -> t.setATK((t.getATK() * 1.1)));
                break;
            case 2:
                // -10% de vitesse de déplacement des ennemis
                Ennemy.setBaseSpeedMultiplier(Ennemy.getBaseSpeedMultiplier() * 0.9);
                Game.getActiveEnemies().forEach(e -> e.setMovingSpeed(e.getMovingSpeed() * 0.9));
                break;
            case 3:
                // +10% de vitesse d'attaque des tours
                Tower.setBaseAttackSpeedMultiplier(Tower.getBaseAttackSpeedMultiplier() * 0.9);
                Game.getActiveTower().forEach(t -> t.setATKSpeed((t.getATKSpeed() * 0.9)));
                break;
            case 4:
                // Aucun bonus, +30 pièces
                joueur.setArgent(joueur.getArgent() + 30);
                break;
        }

        merchantKingQueue.poll();
        merchantKingBonuses++;
    }

    /**
     * Retourne le joueur du jeu.
     *
     * @return Le joueur.
     */
    public static Player getPlayer() {
        return joueur;
    }

    private String originalGameFileName;

    /**
     * L'interface utilisateur du jeu.
     */
    private UI ui;

    /**
     * Le temps de début du jeu.
     */
    Long startTime = System.currentTimeMillis();
    /**
     * Indique si la souris était déjà pressée lors de la dernière frame.
     */
    private boolean mousePressedLastFrame = false;

    private boolean win = false;

    /**
     * Retourne et retire le premier MerchantKing de la file d'attente.
     *
     * @return Le MerchantKing suivant ou null si la file est vide.
     */
    public MerchantKing dequeueMerchantKing() {
        return merchantKingQueue.poll();
    }

    /**
     * Vérifie si la file de MerchantKings est vide.
     *
     * @return true si la file est vide, sinon false.
     */
    public boolean hasPendingMerchantKings() {
        return !merchantKingQueue.isEmpty();
    }

    public void launch() throws GameException, IOException {
        init();
        startTime = System.currentTimeMillis(); // Temps de début de la wave
        long previousTime = startTime;
        elapsedTime = 0;

        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double) (currentTime - previousTime) / 1000; // Temps écoulé depuis la dernière frame
            previousTime = currentTime;

            // Met à jour le temps total écoulé
            double elapsedTime = (double) (currentTime - startTime) / 1000; // Temps écoulé en secondes depuis le début

            // Gérer les clics de souris pour le Railgun
            handleRailgunClick();

            update(deltaTimeSec, elapsedTime); // Met à jour l'état du jeu
            ui.render(); // Redessine l'interface utilisateur
        }
        if (win) {
            youWin();
        } else {
            youLose();
        }
    }

    /**
     * Ajoute un MerchantKing à la file d'attente pour interaction.
     *
     * @param merchantKing Le MerchantKing ayant atteint la base.
     */
    private void enqueueMerchantKing(MerchantKing merchantKing) {
        merchantKingQueue.add(merchantKing);
    }

    /**
     * Gère les clics de souris pour le Railgun et attaque l'ennemi le plus proche
     * de la position du clic.
     */
    private void handleRailgunClick() {
        if (StdDraw.isMousePressed()) {
            if (!mousePressedLastFrame) { // Si la souris était relâchée avant ce clic
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();

                // Chercher l'ennemi le plus proche de la position du clic
                Warrior cible = Game.getActiveEnemies().stream()
                        .min((e1, e2) -> Double.compare(
                                Warrior.calculatePixelDistanceX(mouseX, mouseY, e1.getX(), e1.getY()),
                                Warrior.calculatePixelDistanceX(mouseX, mouseY, e2.getX(), e2.getY())))
                        .orElse(null);

                if (cible != null) {
                    // Faire attaquer toutes les instances de Railgun
                    Game.activeTower.stream()
                            .filter(t -> t instanceof Railgun)
                            .forEach(t -> t.attaquer(cible));
                }

                ui.handleClick(mouseX, mouseY); // Gérer le clic pour d'autres interactions
            }
            mousePressedLastFrame = true; // La souris est maintenant considérée comme pressée
        } else {
            mousePressedLastFrame = false; // Réinitialise l'état lorsque la souris est relâchée
        }
    }

    /**
     * Vérifie si le jeu est encore en cours d'exécution.
     *
     * @return true si le joueur est en vie, false sinon.
     */
    private boolean isGameRunning() {
        return joueur.isAlive() && !win; // Le jeu est en cours tant que le joueur est en vie
    }

    /**
     * Initialise les niveaux du jeu à partir d'un fichier.
     *
     * @param gameFilePath Le chemin du fichier de jeu.
     * @throws GameException Si une erreur de jeu se produit.
     * @throws IOException   Si une erreur d'entrée/sortie se produit.
     */
    private void initLevels(String gameFilePath) throws GameException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(gameFilePath))) {
            String levelName;

            while ((levelName = reader.readLine()) != null) {
                String levelPath = "resources/levels/" + levelName + ".lvl";
                levels.add(new Level(levelPath));
            }
        }
    }

    /**
     * Initialise le jeu.
     *
     * @throws GameException Si une erreur de jeu se produit.
     * @throws IOException   Si une erreur d'entrée/sortie se produit.
     */
    private void init() throws GameException, IOException {
        String gameFilePath = "resources/games/game.g";
        originalGameFileName = new File(gameFilePath).getName(); // Récupère le nom du fichier d'origine

        if (!loadGameState()) { // Si aucune sauvegarde n'est trouvée, commencez une nouvelle partie
            joueur = new Player(200, 100); // Crée un joueur avec 200 pièces d'or et 100 points de vie

            initLevels(gameFilePath); // Charge les niveaux du fichier de jeu spécifié
            cptLevel = 0;
            cptWave = 0;
            currentLevel = levels.get(cptLevel);
            currentWave = currentLevel.getWaves().get(cptWave);
        }

        this.ui = new UI(currentLevel.getMap());
    }

    /**
     * Met à jour l'état du jeu en fonction du temps écoulé.
     *
     * @param deltaTimeSec Le temps écoulé depuis la dernière mise à jour en
     *                     secondes.
     * @param elapsedTime  Le temps total écoulé depuis le début du jeu en secondes.
     * @throws GameException Si une erreur de jeu se produit.
     * @throws IOException   Si une erreur d'entrée/sortie se produit.
     */
    private void update(double deltaTimeSec, double elapsedTime) throws GameException, IOException {
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

    /**
     * Gère le spawn des ennemis en fonction du temps écoulé.
     *
     * @param elapsedTime Le temps total écoulé depuis le début du jeu en secondes.
     */
    private void handleEnemySpawning(double elapsedTime) {
        while (!currentWave.getEnemies().isEmpty() &&
                currentWave.getEnemies().peek().getSpawnTime() <= elapsedTime) {
            activeEnemies.add(currentWave.getEnemies().poll());
        }
    }

    /**
     * Met à jour la position des ennemis en fonction du temps écoulé.
     *
     * @param deltaTimeSec Le temps écoulé depuis la dernière mise à jour en
     *                     secondes.
     */
    private void updateEnemies(double deltaTimeSec) {
        Iterator<Ennemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Ennemy enemy = iterator.next();
            enemy.updatePosition(deltaTimeSec);

            // Met à jour l'effet de poison
            enemy.updatePoison(deltaTimeSec);

            // Vérifie si l'ennemi est un Healer et applique l'effet de soin
            if (enemy instanceof Healer) {
                Healer healer = (Healer) enemy;
                healer.updateHealerTime(deltaTimeSec); // Appelle la méthode update pour le soin
            }

            if (enemy instanceof Termiernator) {
                ((Termiernator) enemy).updateMessageState(deltaTimeSec); // Met à jour l'état du message
            }

            if (enemy instanceof MerchantKing) {
                MerchantKing king = (MerchantKing) enemy;

                if (king.hasReachedEnd()) {
                    enqueueMerchantKing(king); // Ajoute à la file d'attente
                    iterator.remove(); // Retire l'ennemi de la liste active
                    continue;
                }

                if (enemy.getPV() <= 0) {
                    king.onDeath(joueur); // Applique les pénalités
                    iterator.remove();
                    continue;
                }
            }

            if (enemy.hasReachedEnd()) {
                joueur.takeDamage(enemy.getATK());
                iterator.remove();
                continue; // Passe à l'ennemi suivant
            }

            if (enemy.getPV() <= 0) {
                if (enemy instanceof Bomb) {
                    Bomb bomb = (Bomb) enemy;

                    // Zone d'effet de l'explosion
                    double cellSize = getCurrentLevel().getMap().getCellSize();
                    double maxDistanceInPixels = 1.5 * cellSize;

                    // Trouver toutes les tours dans la zone d'effet
                    List<Warrior> toursDansZone = activeTower.stream()
                            .filter(t -> Warrior.calculatePixelDistance(t, bomb) <= maxDistanceInPixels)
                            .collect(Collectors.toList());

                    // Inflige des dégâts aux tours dans la zone
                    for (Warrior tour : toursDansZone) {
                        tour.takeDamage(bomb.getATK() * 10); // Inflige 10 fois les dégâts de la bombe
                    }
                }
                joueur.setArgent((int) joueur.getArgent() + enemy.getReward());
                iterator.remove(); // Supprime l'ennemi mort
            }
        }
    }

    /**
     * Met à jour les attaques des ennemis en fonction du temps écoulé.
     *
     * @param deltaTimeSec Le temps écoulé depuis la dernière mise à jour en
     *                     secondes.
     */
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

    /**
     * Met à jour les tours en fonction du temps écoulé.
     *
     * @param deltaTimeSec Le temps écoulé depuis la dernière mise à jour en
     *                     secondes.
     */
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

    /**
     * Vérifie si la vague actuelle est terminée et passe à la suivante si
     * nécessaire.
     *
     * @throws GameException Si une erreur de jeu se produit.
     * @throws IOException   Si une erreur d'entrée/sortie se produit.
     */
    private void checkWaveCompletion() throws GameException, IOException {
        if (currentWave.getEnemies().isEmpty() && activeEnemies.isEmpty()) {
            nextWave();
        }
    }

    /**
     * Passe à la vague suivante ou au niveau suivant si toutes les vagues sont
     * terminées.
     *
     * @throws GameException Si une erreur de jeu se produit.
     * @throws IOException   Si une erreur d'entrée/sortie se produit.
     */
    private void nextWave() throws GameException, IOException {
        int nbVagueDuLevel = currentLevel.getWaves().size();

        // Si on doit changer de niveau
        if (cptWave == nbVagueDuLevel - 1) {
            if (cptLevel == levels.size() - 1) { // Si on a fini tous les niveaux
                if (joueur.getHP() > 0)
                    win = true;
                return;
            }
            // Passe au niveau suivant
            cptLevel++;
            currentLevel = levels.get(cptLevel);
            cptWave = 0;
            currentWave = currentLevel.getWaves().get(cptWave);

            for (Tower tower : activeTower) { // Récupère l'argent des tours restantes
                joueur.setArgent(joueur.getArgent() + (int) (tower.getArgentParPv() * tower.getPV()));
            }

            activeEnemies.clear(); // Vide les ennemis actifs
            activeTower.clear();
            ui.updateMap(currentLevel.getMap());

            saveGameState(); // Sauvegarde l'état actuel du jeu
        } else {
            // Passe à la vague suivante
            cptWave++;
            currentWave = currentLevel.getWaves().get(cptWave);

            saveGameState(); // Sauvegarde l'état actuel du jeu
        }
        // Réinitialise le temps écoulé pour la nouvelle vague
        elapsedTime = 0;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Affiche l'écran de victoire et supprime le fichier de sauvegarde.
     */
    private void youWin() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
        StdDraw.text(512, 360, "YOU WIN!");
        StdDraw.show();
    }

    /**
     * Affiche l'écran de défaite.
     */
    private void youLose() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
        StdDraw.text(512, 360, "YOU LOSE");
        StdDraw.show();
    }

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier.
     * Générer en partie avec l'IA
     */
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
                oos.writeObject(joueur); // Sauvegarde l'état du joueur
                oos.writeInt(cptLevel); // Sauvegarde le niveau actuel
                oos.writeInt(cptWave); // Sauvegarde la vague actuelle
                oos.writeObject(originalGameFileName); // Sauvegarde le nom du fichier d'origine
                System.out.println("Game state saved successfully in " + saveFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save game state.");
        }
    }

    /**
     * Charge l'état du jeu à partir d'un fichier de sauvegarde.
     *
     * @return true si l'état du jeu a été chargé avec succès, false sinon.
     * Générer en partie avec l'IA
     */
    private boolean loadGameState() {
        // Chemin du fichier de sauvegarde basé sur le fichier de jeu actuel
        String saveFileName = "resources/saved/" + originalGameFileName.replace(".g", ".sav");

        File saveFile = new File(saveFileName);
        if (!saveFile.exists()) {
            return false; // Aucun fichier de sauvegarde trouvé
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            joueur = (Player) ois.readObject(); // Charge l'état du joueur
            cptLevel = ois.readInt(); // Charge le niveau
            cptWave = ois.readInt(); // Charge la vague
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
}