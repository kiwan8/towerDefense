package src.Towers;

import java.awt.Color;
import src.*;

/**
 * La classe Archer représente une tour de type archer dans le jeu.
 * Elle hérite de la classe Tower.
 */
public class Archer extends Tower {

    /**
     * Constructeur de la classe Archer.
     *
     * @param position La position de la tour sur la carte.
     */
    public Archer(Tile position) {
        super(30, 5, 1.0, 2, Element.Neutre, position, ModeAttaque.NEAREST_FROM_BASE, 20, Color.BLACK);
    }
}
