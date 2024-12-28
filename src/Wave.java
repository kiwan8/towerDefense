package src;

import src.Monsters.*;
import java.io.*;
import java.util.*;

/**
 * Represents a wave of enemies in the game.
 * Handles the creation and management of enemies based on wave configuration files.
 */
public class Wave {

    private Queue<Ennemy> spawnQueue; // Queue of enemies to spawn in this wave
    private Level level; // Reference to the level this wave belongs to

    /**
     * Constructor for Wave.
     *
     * @param level Reference to the level associated with the wave.
     */
    public Wave(Level level) {
        this.level = level;
        this.spawnQueue = new LinkedList<>();
    }

    /**
     * Loads a wave configuration from a file.
     *
     * @param filePath Path to the .wve file describing the wave.
     * @throws IOException If an error occurs during file reading.
     */
    public void loadWave(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                double spawnTime = Double.parseDouble(parts[0]);
                String enemyName = parts[1];

                // Get spawn coordinates from the map
                int[] spawnTile = level.getMap().getPath().get(0);
                int row = spawnTile[0];
                int col = spawnTile[1];

                // Calculate pixel coordinates for the spawn point
                double cellSize = Math.min(700.0 / level.getMap().getRows(), 700.0 / level.getMap().getCols());
                double x = 350 - 350 + col * cellSize + cellSize / 2;
                double y = 350 + 350 - row * cellSize - cellSize / 2;

                // Create the enemy and add it to the spawn queue
                Ennemy enemy = createEnemy(enemyName, spawnTime, x, y, level.getMap());
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
 * @param map       Référence à la carte associée.
 * @return Un objet Ennemy correspondant au nom.
 * @throws IllegalArgumentException Si le nom de l'ennemi est inconnu.
 */
private Ennemy createEnemy(String name, double spawnTime, double x, double y, Map map) {
    switch (name) {
        case "Minion":
            return new Minion(spawnTime, x, y, map);
        case "Wind Grognard":
            return new WindGrognard(spawnTime, x, y, map);
        case "Fire Grognard":
            return new FireGrognard(spawnTime, x, y, map);
        case "Water Brute":
            return new WaterBrute(spawnTime, x, y, map);
        case "Earth Brute":
            return new EarthBrute(spawnTime, x, y, map);
        case "Boss":
            return new Boss(spawnTime, x, y, map);
        case "Healer":
            return new Healer(spawnTime, x, y, map);
        case "Buffer":
            return new Buffer(spawnTime, x, y, map);
        case "Bomb":
            return new Bomb(spawnTime, x, y, map);
        case "Merchant King":
            return new MerchantKing(spawnTime, x, y, map);
        case "Termiernator":
            return new Termiernator(spawnTime, x, y, map);
        default:
            throw new IllegalArgumentException("Unknown enemy name: " + name);
    }
}

    /**
     * Retrieves the queue of enemies in the wave.
     *
     * @return Queue of enemies.
     */
    public Queue<Ennemy> getEnemies() {
        return spawnQueue;
    }
}
