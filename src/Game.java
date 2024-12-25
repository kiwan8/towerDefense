package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.GameExceptions.GameException;
import src.libraries.StdDraw;

public class Game {
    // Méthode principale pour lancer le jeu

    private static Player joueur;
    private UI ui;
    private static Map map;
    
    public void launch() throws GameException, IOException {
        init();
        long previousTime = System.currentTimeMillis();
        
        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double) (currentTime - previousTime) / 1000;  // Temps écoulé depuis la dernière frame
            previousTime = currentTime;
    
            if (StdDraw.isMousePressed()) {  // Si le bouton de la souris est pressé
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
    
                ui.handleClick(mouseX, mouseY);
    
                StdDraw.show();
                StdDraw.pause(100);

            update(deltaTimeSec);  // Met à jour l'état du jeu
            }
        }
    }
    
    // Vérifie si le jeu est encore en cours d'exécution
    private boolean isGameRunning() {
        return joueur.isAlive();  // Le jeu est en cours tant que le joueur est en vie
    }

   
    
    // Initialise le jeu
    private void init() throws GameException, IOException {
        joueur = new Player(50, 100);  // Crée un joueur avec 200 pièces d'or et 100 points de vie
        map = new Map("resources/maps/10-10.mtp");  // Charge la carte à partir du fichier 10-10.mtp
        ui = new UI(map);  // Crée l'interface utilisateur
    }
    
    // Met à jour l'état du jeu en fonction du temps écoulé
    private void update(double deltaTimeSec) {
        ui.update();  // Met à jour l'interface utilisateur
    }
    
    public static Map getMap() {  // Getter pour la carte
        return map;
    }

    public static Player getPlayer() {  // Getter pour le joueur
        return joueur;
    }
}
