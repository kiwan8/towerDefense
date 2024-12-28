package src.Monsters;

import src.*;

public class EarthBrute extends Ennemy {
    /**
     * Constructeur pour Earth Brute.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public EarthBrute(double spawnTime, double x, double y, Map map) {
        super(30, 5, 1.0, 3, Element.Terre, 1.0, spawnTime, x, y, map);
    }
}
