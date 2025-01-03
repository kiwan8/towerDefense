package src.Monsters;

import src.*;

public class Boss extends Ennemy {
    /**
     * Constructeur pour Boss.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Boss(double spawnTime, double x, double y, Map map) {
        super(150, 100, 10.0, 2, Element.Feu, 0.5, spawnTime, x, y, map,100, ModeAttaque.NEAREST);
    }
}
