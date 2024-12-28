package src.Monsters;

import src.*;

public class FireGrognard extends Ennemy {
    /**
     * Constructeur pour Fire Grognard.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public FireGrognard(double spawnTime, double x, double y, Map map) {
        super(1, 7, 2.0, 3, Element.Feu, 2.0, spawnTime, x, y, map);
    }
}
