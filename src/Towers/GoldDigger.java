package src.Towers;

import java.awt.Color;

import src.*;

public class GoldDigger extends Tower {
    public GoldDigger(Tile position) {
        super(20, 1, 2.0, 10, Element.Terre, position, ModeAttaque.NEAREST, 20, new Color(255, 215, 0));
    }
}
