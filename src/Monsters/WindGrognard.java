package src.Monsters;


import src.*;

public class WindGrognard extends Ennemy {
    /**
     * Constructeur pour Wind Grognard.
     *
     * @param spawnTime Temps d'apparition.
     * @param x         Coordonnée X initiale (en pixels).
     * @param y         Coordonnée Y initiale (en pixels).
     * @param map       Référence à la carte associée.
     */
    public WindGrognard(double spawnTime, double x, double y, Map map) {
        super(1, 7, 2.0, 5, Element.Air, 2.0, spawnTime, x, y, map,1,ModeAttaque.LEAST_HP_TOWER);
        
    }
}
