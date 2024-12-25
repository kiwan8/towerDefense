package src;

import src.Monsters.*;
import java.io.*;
import java.util.*;

public class Wave {

    private List<Ennemy> enemies; // Liste des ennemis dans la vague
    private Level level;          // Référence au niveau auquel appartient la vague

    /**
     * Constructeur de Wave.
     *
     * @param level Référence au niveau associé à la vague.
     */
    public Wave(Level level) {
        this.level = level;
        this.enemies = new ArrayList<>();
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


                //TODO : Gérer le cas x et y 
                // Récupérer les coordonnées x et y de la case de spawn
                // Tile spawnTile = level.getMap().getSpawnTile();
                // int x = spawnTile.getRow();
                // int y = spawnTile.getCol();

                // Créer l'ennemi en fonction de son nom
                Ennemy enemy = createEnemy(enemyName, spawnTime, 0, 0);
                enemies.add(enemy);
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
    private Ennemy createEnemy(String name, double spawnTime, int x, int y) {
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
    public List<Ennemy> getEnemies() {
        return enemies;
    }
}
