package src;

import src.GameExceptions.UnknownEnemyException;
import src.Monsters.*;
import java.io.*;
import java.util.*;

/**
 * Represents a wave of enemies in the game.
 * Handles the creation and management of enemies based on wave configuration
 * files.
 */
public class Wave {

    private Queue<Ennemy> spawnQueue; // Queue of enemies to spawn in this wave
    private Level level; // Reference to the level this wave belongs to
    private String WaveFilePath;

    /**
     * Constructor for Wave.
     *
     * @param level Reference to the level associated with the wave.
     */
    public Wave(Level level, String WaveFilePath) {
        this.level = level;
        this.spawnQueue = new LinkedList<>();
        this.WaveFilePath = WaveFilePath;
    }

    public void loadWave(String filePath) throws IOException, UnknownEnemyException {
        this.WaveFilePath = filePath; // Stocker le chemin du fichier vague pour les exceptions
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
    
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split("\\|");
                if (parts.length < 2) {
                    throw new IOException("Invalid line format in wave file: " + filePath);
                }
    
                double spawnTime = Double.parseDouble(parts[0]);
                String enemyName = parts[1];
    
                int[] spawnTile = level.getMap().getPath().get(0);
                int row = spawnTile[0];
                int col = spawnTile[1];
    
                double cellSize = level.getMap().getCellSize();
                double x = 350 - 350 + col * cellSize + cellSize / 2;
                double y = 350 + 350 - row * cellSize - cellSize / 2;
    
                Ennemy enemy = createEnemy(enemyName, spawnTime, x, y, level.getMap(), lineNumber, line);
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
     * @throws UnknownEnemyException
     * @throws IllegalArgumentException Si le nom de l'ennemi est inconnu.
     */
    private Ennemy createEnemy(String name, double spawnTime, double x, double y, Map map, int lineNumber, String lineContent)
        throws GameExceptions.UnknownEnemyException {
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
            throw new GameExceptions.UnknownEnemyException(
                "Unknown enemy type detected!\n" +
                "Level file: " + level.getLevelfilePath() + "\n" +
                "Wave file: " + this.WaveFilePath + "\n" +
                "Line number: " + lineNumber + "\n" +
                "Content: " + lineContent + "\n" +
                "The game will now terminate.");
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
