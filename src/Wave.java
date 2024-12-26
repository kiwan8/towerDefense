package src;

import src.Monsters.*;
import java.io.*;
import java.util.*;

public class Wave {

    private Queue<Ennemy> spawnQueue = new LinkedList<>();
    private Level level;          // Référence au niveau auquel appartient la vague

    /**
     * Constructeur de Wave.
     *
     * @param level Référence au niveau associé à la vague.
     */
    public Wave(Level level) {
        this.level = level;
        this.spawnQueue = new LinkedList<>();
    }

    /**
     * Charge une vague à partir d'un fichier .wve.
     *
     * @param filePath Chemin vers le fichier .wve.
     * @throws IOException Si une erreur de lecture se produit.
     */
    public void loadWave(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                double spawnTime = Double.parseDouble(parts[0]);
                String enemyName = parts[1];


               // Récupérer les coordonnées de la case de spawn en tuiles (row, col)
            int[] spawnTile = level.getMap().getPath().get(0);
            int row = spawnTile[0];
            int col = spawnTile[1];
             // Calculer les coordonnées en pixels (x, y) pour le centre de la case
             double cellSize = Math.min(700.0 / level.getMap().getRows(), 700.0 / level.getMap().getCols());
             double x = 350 - 350 + col * cellSize + cellSize / 2; // Calcul pour X
             double y = 350 + 350 - row * cellSize - cellSize / 2; // Calcul pour Y
                

                

                // Crée un ennemi avec le chemin de la carte
                Ennemy enemy = createEnemy(enemyName, spawnTime, x, y);
                enemy.setPath(level.getMap().getPath()); // Associe le chemin à l'ennemi
                spawnQueue.add(enemy);
            }
        }
    }

    

    /**
     * Crée un ennemi en fonction de son nom.
     *
     * @param name      Nom de l'ennemi.
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale.
     * @param y         Coordonnée Y initiale.
     * @return Un objet Ennemy correspondant au nom.
     * @throws IllegalArgumentException Si le nom de l'ennemi est inconnu.
     */
    private Ennemy createEnemy(String name, double spawnTime, double x, double y) {
        switch (name) {
            case "Minion":
                return new Minion(spawnTime, x, y);
            case "Wind Grognard":
                return new WindGrognard(spawnTime, x, y);
            case "Fire Grognard":
                return new FireGrognard(spawnTime, x, y);
            case "Water Brute":
                return new WaterBrute(spawnTime, x, y);
            case "Earth Brute":
                return new EarthBrute(spawnTime, x, y);
            case "Boss":
                return new Boss(spawnTime, x, y);
            default: //TODO : A modifier par une exception perso
                throw new IllegalArgumentException("Nom d'ennemi inconnu : " + name);
        }
    }

    /**
     * Récupère la liste des ennemis dans la vague.
     *
     * @return Liste des ennemis.
     */
    public Queue<Ennemy> getEnemies() {
        return spawnQueue;
    }
}
