package src;

import src.GameExceptions.UnknownEnemyException;
import src.Monsters.*;
import java.io.*;
import java.util.*;

/**
 * Représente une vague d'ennemis dans le jeu.
 * Gère la création et la gestion des ennemis en fonction des fichiers de configuration des vagues.
 */
public class Wave {

    /**
     * File d'attente des ennemis à faire apparaître dans cette vague.
     */
    private Queue<Ennemy> spawnQueue; // Queue of enemies to spawn in this wave

    /**
     * Référence au niveau auquel appartient cette vague.
     */
    private Level level; // Reference to the level this wave belongs to

    /**
     * Chemin du fichier de configuration de la vague.
     */
    private String WaveFilePath;

    /**
     * Constructeur pour la classe Wave.
     *
     * @param level         Référence au niveau associé à la vague.
     * @param WaveFilePath  Chemin vers le fichier de configuration de la vague.
     */
    public Wave(Level level, String WaveFilePath) {
        this.level = level;
        this.spawnQueue = new LinkedList<>();
        this.WaveFilePath = WaveFilePath;
    }

    /**
     * Charge la vague à partir d'un fichier de configuration.
     *
     * @param filePath Chemin vers le fichier de configuration de la vague.
     * @throws IOException               Si le fichier ne peut pas être lu ou si le format est invalide.
     * @throws UnknownEnemyException     Si un type d'ennemi inconnu est détecté dans le fichier.
     */
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
     * @param name          Nom de l'ennemi.
     * @param spawnTime     Temps d'apparition de l'ennemi.
     * @param x             Coordonnée X initiale de l'ennemi.
     * @param y             Coordonnée Y initiale de l'ennemi.
     * @param map           Référence à la carte associée.
     * @param lineNumber    Numéro de la ligne dans le fichier de configuration (pour les erreurs).
     * @param lineContent   Contenu de la ligne dans le fichier de configuration (pour les erreurs).
     * @return              Un objet Ennemy correspondant au nom.
     * @throws UnknownEnemyException Si le type d'ennemi est inconnu.
     */
    private Ennemy createEnemy(String name, double spawnTime, double x, double y, Map map, int lineNumber, String lineContent)
        throws UnknownEnemyException {
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
                throw new UnknownEnemyException(
                    "Unknown enemy type detected!\n" +
                    "Level file: " + level.getLevelfilePath() + "\n" +
                    "Wave file: " + this.WaveFilePath + "\n" +
                    "Line number: " + lineNumber + "\n" +
                    "Content: " + lineContent + "\n" +
                    "The game will now terminate.");
        }
    }

    /**
     * Récupère la file d'attente des ennemis dans la vague.
     *
     * @return File d'attente des ennemis.
     */
    public Queue<Ennemy> getEnemies() {
        return spawnQueue;
    }
}
