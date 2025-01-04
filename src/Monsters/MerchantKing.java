package src.Monsters;

import src.*;

public class MerchantKing extends Ennemy {
    /**
     * Constructeur pour Merchant King.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public MerchantKing(double spawnTime, double x, double y, Map map) {
        super(100, 0, 0.0, 0, Element.Terre, 2.0, spawnTime, x, y, map,2,ModeAttaque.NOBODY);
    }
}
