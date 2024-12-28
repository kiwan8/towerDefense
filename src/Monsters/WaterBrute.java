package src.Monsters;

import src.*;

public class WaterBrute extends Ennemy {
    /**
     * Constructeur pour Water Brute.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public WaterBrute(double spawnTime, double x, double y, Map map) {
        super(30, 5, 1.0, 3, Element.Eau, 1.0, spawnTime, x, y, map);
    }
}
