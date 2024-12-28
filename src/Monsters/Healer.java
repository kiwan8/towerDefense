package src.Monsters;

import src.*;

public class Healer extends Ennemy {
    /**
     * Constructeur pour Healer.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Healer(double spawnTime, double x, double y, Map map) {
        super(10, 1, 1.0, 2, Element.Neutre, 0.5, spawnTime, x, y, map);
    }
}
