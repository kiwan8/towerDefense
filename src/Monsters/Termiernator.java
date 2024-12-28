package src.Monsters;

import src.*;

public class Termiernator extends Ennemy {
    /**
     * Constructeur pour Termiernator.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Termiernator(double spawnTime, double x, double y, Map map) {
        super(999, 999, 15.0, 0, Element.Neutre, 0.5, spawnTime, x, y, map);
    }
}
