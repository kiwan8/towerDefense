package src.Monsters;

import src.*;

public class MerchantKing extends Ennemy {
    /**
     * Constructeur pour MerchantKing.
     *
     * @param spawnTime Le moment (en secondes) où le monstre doit apparaître.
     * @param x         La coordonnée X initiale du monstre sur la carte.
     * @param y         La coordonnée Y initiale du monstre sur la carte.
     * @param map       La carte (Map) sur laquelle le monstre évolue.
     */
    public MerchantKing(double spawnTime, double x, double y, Map map) {
        super(100, 0, 0.0, 0, Element.Terre, 2, spawnTime, x, y, map, 2, ModeAttaque.NOBODY);
    }

    /**
     * Déclenche les effets lorsque le Merchant King est tué.
     */
    public void onDeath(Player player) {
        // Le joueur perd des pièces lorsqu'il tue le Merchant King
        player.setArgent(player.getArgent() - 50); 
    }

}
