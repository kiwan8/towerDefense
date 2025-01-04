package src.Monsters;

import src.*;

public class MerchantKing extends Ennemy {
    private boolean reachedBase = false; // Indique si le Merchant King a atteint la base

    public MerchantKing(double spawnTime, double x, double y, Map map) {
        super(100, 0, 0.0, 0, Element.Terre, 2, spawnTime, x, y, map, 2, ModeAttaque.NOBODY);
    }

    /**
     * Déclenche les effets lorsque le Merchant King est tué.
     */
    public void onDeath(Player player) {
        // Le joueur perd des pièces lorsqu'il tue le Merchant King
        player.setArgent(player.getArgent() - 50); // Perte de 50 pièces par défaut
        System.out.println("Le joueur a tué le Merchant King et perdu 50 pièces !");
    }

    /**
     * Déclenche les effets lorsqu'il atteint la base.
     *
     * @return true si une interaction doit être affichée.
     */
    public boolean onReachBase() {
        reachedBase = true;
        return true; // Indique qu'un popup doit s'afficher
    }

    /**
     * Vérifie si le Merchant King a atteint la base.
     *
     * @return true s'il a atteint la base.
     */
    public boolean hasReachedBase() {
        return reachedBase;
    }
}
