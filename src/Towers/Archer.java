package src.Towers;

import java.awt.Color;

import src.*;

public class Archer extends Tower {
    public Archer(Tile position) {
        super(30, 5, 1.0, 2, Element.Neutre, position, ModeAttaque.NEAREST, 20, Color.BLACK);
    }
}
