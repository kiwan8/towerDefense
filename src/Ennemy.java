package src;

import java.util.List;

/**
 * Abstract class representing a generic enemy in the game.
 * Handles common attributes and behaviors for all enemies, such as movement
 * and interaction with the game map.
 */
public abstract class Ennemy extends Warrior {

    // Immutable properties
    private final double movingSpeed; // Movement speed of the enemy
    private final double spawnTime;   // Spawn time in the wave
    private final List<int[]> path;   // Path in tile coordinates
    private final List<double[]> pixelPath; // Path in pixel coordinates
    private final Map map;           // Reference to the game map

    // Mutable properties
    private double x; // Current X coordinate (in pixels)
    private double y; // Current Y coordinate (in pixels)
    private int currentStep; // Current step in the path

    /**
     * Constructor for the Ennemy class.
     *
     * @param PV          Health points of the enemy.
     * @param ATK         Attack points of the enemy.
     * @param ATKSpeed    Attack speed of the enemy.
     * @param range       Attack range of the enemy (in tiles).
     * @param element     Element type of the enemy.
     * @param movingSpeed Movement speed of the enemy.
     * @param spawnTime   Time at which the enemy spawns in the wave.
     * @param x           Initial X coordinate (in pixels).
     * @param y           Initial Y coordinate (in pixels).
     * @param map         Reference to the game map.
     */
    public Ennemy(int PV, int ATK, double ATKSpeed, int range, Element element,
                  double movingSpeed, double spawnTime, double x, double y, Map map) {
        super(PV, ATK, ATKSpeed, range, element, null, ModeAttaque.NEAREST);
        this.movingSpeed = movingSpeed;
        this.spawnTime = spawnTime;
        this.x = x;
        this.y = y;
        this.map = map;
        this.pixelPath = map.getPixelPath();
        this.path = map.getPath();
        this.currentStep = 0;
    }

        /**
     * Met à jour la position de l'ennemi sur la carte en fonction de son
     * déplacement.
     * 
     * Cette méthode gère le déplacement d'un ennemi sur son chemin défini
     * (pixelPath)
     * et met à jour ses coordonnées (x, y) ainsi que sa case actuelle (Tile)
     * lorsque l'ennemi traverse une frontière de case.
     * Elle prend en compte la vitesse de déplacement de l'ennemi et le temps écoulé
     * depuis la dernière mise à jour pour calculer la progression.
     *
     * @param deltaTimeSec Le temps écoulé en secondes depuis la dernière mise à
     *                     jour.
     *
     */
    public void updatePosition(double deltaTimeSec) {
        if (currentStep >= pixelPath.size()) {
            return; // Ennemi a atteint la fin du chemin
        }

        // Récupérer la prochaine position cible en pixels
        double[] target = pixelPath.get(currentStep);
        double targetX = target[0];
        double targetY = target[1];

        // Calcul de la distance que l'ennemi peut parcourir cette frame
        double cellSize = Math.min(700.0 / map.getRows(), 700.0 / map.getCols()); // Taille d'un carreau en pixels
        double distanceToTravel = movingSpeed * cellSize * deltaTimeSec; // Distance en pixels

        // Calcul du vecteur directionnel
        double dx = targetX - x;
        double dy = targetY - y;
        double distanceToTarget = Math.sqrt(dx * dx + dy * dy);

        if (distanceToTravel >= distanceToTarget) {
            // Atteint la prochaine étape
            x = targetX;
            y = targetY;
            currentStep++; // Avancer dans le chemin
        } else {
            // Déplacement partiel
            x += distanceToTravel * dx / distanceToTarget;
            y += distanceToTravel * dy / distanceToTarget;
        }
        // TODO : A optimiser en utilisant le path et en calculant qu'une valeur de
        // moitié de case au début
        // Calculer la case actuelle après mise à jour de la position
        int currentRow = (int) ((350 + 350 - y) / cellSize);
        int currentCol = (int) ((x - (350 - 350)) / cellSize);

        // Si l'ennemi change de case, mettre à jour sa position sur la carte
        Tile currentTile = map.getTile(currentRow, currentCol);
        if (!currentTile.equals(this.getPosition())) {
            this.setPosition(currentTile);
        }
    }


    /**
     * Gets the current X coordinate of the enemy.
     *
     * @return The X coordinate (in pixels).
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current Y coordinate of the enemy.
     *
     * @return The Y coordinate (in pixels).
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the movement speed of the enemy.
     *
     * @return The movement speed.
     */
    public double getMovingSpeed() {
        return movingSpeed;
    }

    /**
     * Gets the spawn time of the enemy.
     *
     * @return The spawn time.
     */
    public double getSpawnTime() {
        return spawnTime;
    }

    /**
     * Checks if the enemy has reached the end of its path.
     *
     * @return True if the enemy has completed its path, false otherwise.
     */
    public boolean hasReachedEnd() {
        return currentStep >= pixelPath.size();
    }

    /**
     * Gets the tile path of the enemy.
     *
     * @return The list of tile coordinates representing the path.
     */
    public List<int[]> getPath() {
        return path;
    }

    /**
     * Gets the pixel path of the enemy.
     *
     * @return The list of pixel coordinates representing the path.
     */
    public List<double[]> getPixelPath() {
        return pixelPath;
    }
}
