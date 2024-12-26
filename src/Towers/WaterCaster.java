package src.Towers;

import java.awt.Color;

import src.*;

public class WaterCaster extends Tower {
    public WaterCaster(Tile position) {
        super(30, 3, 1.0, 4, Element.Eau, position, ModeAttaque.NEAREST, 50, Color.BLUE);
    }
}
