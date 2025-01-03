package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe FireCaster représente une tour de type lanceur de feu dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class FireCaster extends Tower {

    /**
     * Constructeur de la classe FireCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public FireCaster(Tile position) {
        super(30, 10, 0.5, 2.5, Element.Feu, position, ModeAttaque.NEAREST, 100, Color.RED);
    }
}
