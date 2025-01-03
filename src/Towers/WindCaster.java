package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe WindCaster représente une tour de type lanceur de vent dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class WindCaster extends Tower {

    /**
     * Constructeur de la classe WindCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public WindCaster(Tile position) {
        super(30, 5, 1.5, 6, Element.Air, position, ModeAttaque.NEAREST, 50, Color.YELLOW);
    }
}