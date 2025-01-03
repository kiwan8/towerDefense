package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe EarthCaster représente une tour de type lanceur de terre dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class EarthCaster extends Tower {

    /**
     * Constructeur de la classe EarthCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public EarthCaster(Tile position) {
        super(50, 7, 0.5, 2.5, Element.Terre, position, ModeAttaque.NEAREST, 100, Color.GREEN);
    }
}
