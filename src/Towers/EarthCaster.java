package src.Towers;

import java.awt.Color;

import src.*;

public class EarthCaster extends Tower {
    public EarthCaster(Tile position) {
        super(50, 7, 0.5, 2.5, Element.Terre, position, ModeAttaque.NEAREST, 100, Color.GREEN);
    }
}
