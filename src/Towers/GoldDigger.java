package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe GoldDigger représente une tour de type chercheur d'or dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class GoldDigger extends Tower {

    /**
     * Constructeur de la classe GoldDigger.
     *
     * @param position La position de la tour sur la carte.
     */
    public GoldDigger(Tile position) {
        super(20, 10, 2.0, 10, Element.Terre, position, ModeAttaque.NEAREST, 20, new Color(255, 223, 0)); // Couleur or
    }


    /**
     * Attaque la cible spécifiée.
     *
     * @param cible La cible à attaquer.
     */
    @Override
    public void attaquer(Warrior cible) {
        // Infliger des dégâts directs
        super.attaquer(cible);

        // Générer 1 pièce pour le joueur
        Game.getPlayer().setArgent(Game.getPlayer().getArgent() + 1);
    }
}
