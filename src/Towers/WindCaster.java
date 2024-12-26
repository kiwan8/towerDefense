package src.Towers;

import java.awt.Color;

import src.*;

public class WindCaster extends Tower {
    public WindCaster(Tile position) {
        super(30, 5, 1.5, 6, Element.Air, position, ModeAttaque.NEAREST, 50, Color.YELLOW);
    }
}
