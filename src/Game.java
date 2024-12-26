package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void launch() throws GameException, IOException {
        init();
        startTime = System.currentTimeMillis(); // Temps de début de la partie
        long previousTime = startTime;
        elapsedTime = 0; // Temps total écoulé depuis le début de la partie

        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double) (currentTime - previousTime) / 1000; // Temps écoulé depuis la dernière frame
            previousTime = currentTime;

            // Met à jour le temps total écoulé
            elapsedTime = (long) (currentTime - startTime) / 1000; // Temps écoulé en secondes depuis le début

            if (StdDraw.isMousePressed()) { // Si le bouton de la souris est pressé
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();

                ui.handleClick(mouseX, mouseY);

                StdDraw.show();
            }

            update(deltaTimeSec, elapsedTime); // Met à jour l'état du jeu

            // Synchronisation pour maintenir une fréquence d'images stable
            ui.render();
        }
        youLose();
    }

    // Vérifie si le jeu est encore en cours d'exécution
    private boolean isGameRunning() {
        return joueur.isAlive(); // Le jeu est en cours tant que le joueur est en vie
    }

    // Initialise le jeu
    // Initialise le jeu
    private void init() throws GameException, IOException {
        joueur = new Player(50, 10000); // Crée un joueur avec 200 pièces d'or et 100 points de vie
        // Charger tous les niveaux dès le départ
        levels.add(new Level("resources/levels/level1.lvl"));
        levels.add(new Level("resources/levels/level2.lvl"));
        levels.add(new Level("resources/levels/level3.lvl"));

        currentLevel = levels.get(cptLevel);
        currentWave = currentLevel.getWaves().get(0);

        this.ui = new UI(currentLevel.getMap());
    }

    // Met à jour l'état du jeu en fonction du temps écoulé
    private void update(double deltaTimeSec, long elapsedTime) throws GameException, IOException {

        // Spawn
        while (!currentWave.getEnemies().isEmpty() && currentWave.getEnemies().peek().getSpawnTime() <= elapsedTime) {
            activeEnemies.add(currentWave.getEnemies().poll());
        }

        // Met à jour les positions des ennemis actifs
        Iterator<Ennemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Ennemy enemy = iterator.next();
            enemy.updatePosition(deltaTimeSec, currentLevel.getMap());

            // Vérifie si l'ennemi a atteint la base
            if (enemy.hasReachedEnd()) {
                joueur.takeDamage(enemy.getATK()); // Inflige des dégâts une seule fois
                iterator.remove(); // Supprime immédiatement l'ennemi de la liste
            }
            if (currentWave.getEnemies().isEmpty() && activeEnemies.isEmpty()) {
                nextWave();
            }
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
