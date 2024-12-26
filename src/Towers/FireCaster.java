package src.Towers;

import java.awt.Color;

import src.*;

public class FireCaster extends Tower {
    public FireCaster(Tile position) {
        super(30, 10, 0.5, 2.5, Element.Feu, position, ModeAttaque.NEAREST, 100,Color.RED);
    }
}
