package src.Towers;

import java.awt.Color;

import src.*;

public class Railgun extends Tower {
    public Railgun(Tile position) {
        super(20, 1, 0.0, 0, Element.Feu, position, ModeAttaque.NEAREST, 150, Color.CYAN); // Range et vitesse d'attaque à 0
    }
}
