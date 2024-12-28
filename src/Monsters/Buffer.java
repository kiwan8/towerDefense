package src.Monsters;

import src.*;

public class Buffer extends Ennemy {
    /**
     * Constructeur pour Buffer.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public Buffer(double spawnTime, double x, double y, Map map) {
        super(10, 2, 2.0, 3, Element.Air, 0.5, spawnTime, x, y, map);
    }
}
