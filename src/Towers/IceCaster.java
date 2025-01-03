package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe IceCaster représente une tour de type lanceur de glace dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class IceCaster extends Tower {

    /**
     * Constructeur de la classe IceCaster.
     *
     * @param position La position de la tour sur la carte.
     */
    public IceCaster(Tile position) {
        super(40, 1, 2.0, 5, Element.Eau, position, ModeAttaque.NEAREST_FROM_BASE, 70, new Color(173, 216, 230));
    }
}
