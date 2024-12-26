package src;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private static Map currentMap;
    private Level currentLevel;
    private static List<Level> Levels = new ArrayList<>();
    private static List<Ennemy> activeEnemies = new ArrayList<>();
    private static List<Tower> activeTower = new ArrayList<>();
    private static long elapsedTime;

    public static List<Level> getLevels() {
        return Levels;
    }
    
    public static List<Ennemy> getActiveEnemies() {
        return activeEnemies;
    }
    
    public static List<Tower> getActiveTower() {
        return activeTower;
    }
    

    public void launch() throws GameException, IOException {
        init();
        long startTime = System.currentTimeMillis(); // Temps de début de la partie
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
                StdDraw.pause(100);
            }
    
            update(deltaTimeSec, elapsedTime); // Met à jour l'état du jeu
    
            // Synchronisation pour maintenir une fréquence d'images stable
            StdDraw.pause(16); // Pause de 16 ms pour environ 60 FPS
            ui.render();
        }
    }
    

    // Vérifie si le jeu est encore en cours d'exécution
    private boolean isGameRunning() {
        return joueur.isAlive(); // Le jeu est en cours tant que le joueur est en vie
    }

    // Initialise le jeu
    // Initialise le jeu
    private void init() throws GameException, IOException {
        joueur = new Player(50, 100); // Crée un joueur avec 200 pièces d'or et 100 points de vie
        Level level1 = new Level();
        this.currentLevel = level1;
        this.currentMap = level1.getMap();
        this.ui = new UI(currentMap);
    }

    // Met à jour l'état du jeu en fonction du temps écoulé
    private void update(double deltaTimeSec, long elapsedTime) {
        // Gère le spawn des ennemis depuis les vagues
        for (Wave wave : currentLevel.getWaves()) {
            for (Ennemy enemy : wave.getEnemies()) {
                if (enemy.getSpawnTime() <= elapsedTime && !activeEnemies.contains(enemy)) {
                    activeEnemies.add(enemy); // Ajoute l'ennemi à la liste active
                }
            }
        }

        // Met à jour les positions des ennemis actifs
        Iterator<Ennemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Ennemy enemy = iterator.next();
            enemy.updatePosition(deltaTimeSec, currentLevel.getMap());
    
        }
    }

    public static Map getCurrentMap() { // Getter pour la carte
        return currentMap;
    }

    public static Player getPlayer() { // Getter pour le joueur
        return joueur;
    }
}
