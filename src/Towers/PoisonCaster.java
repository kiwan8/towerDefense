package src.Towers;

import java.awt.Color;

import src.*;

public class PoisonCaster extends Tower {
    public PoisonCaster(Tile position) {
        super(50, 1, 2.0, 5, Element.Air, position, ModeAttaque.NEAREST, 80, new Color(128, 0, 128) );
    }
}
