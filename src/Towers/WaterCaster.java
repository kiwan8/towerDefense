package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe WaterCaster représente une tour de type lanceur d'eau dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class WaterCaster extends Tower {

    /**
     * Constructeur de la classe WaterCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public WaterCaster(Tile position) {
        super(30, 3, 1.0, 4, Element.Eau, position, ModeAttaque.NEAREST_FROM_BASE, 50, Color.BLUE);
    }
}
