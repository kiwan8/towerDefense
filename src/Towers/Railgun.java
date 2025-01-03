package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe Railgun représente une tour de type railgun dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class Railgun extends Tower {

    /**
     * Constructeur de la classe Railgun.
     *
     * @param position La position de la tour sur la carte.
     */
    public Railgun(Tile position) {
        super(20, 1, 0.0, 0, Element.Feu, position, ModeAttaque.NEAREST, 150, Color.CYAN); // Range et vitesse d'attaque à 0
    }
}
