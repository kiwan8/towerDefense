package src.Towers;

import java.awt.Color;

import src.*;

public class IceCaster extends Tower {
    public IceCaster(Tile position) {
        super(40, 1, 2.0, 5, Element.Eau, position, ModeAttaque.NEAREST_FROM_BASE, 70, new Color(173, 216, 230));
    }
}
