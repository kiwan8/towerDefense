package src;

import java.awt.Color;

public abstract class Tower extends Warrior {

    private final double cout; // Coût de la tour
    private final Color color; // Couleur de la tour

    public Tower(int PV, int ATK, double ATKSpeed, double Range, Element element, Tile position, ModeAttaque modeAttaque, double cout, Color color) {
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.cout = cout;
        this.color = color;
    }

    public double getCout() {
        return cout;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void attaquer(Warrior cible) {
        if (cible instanceof Ennemy) {
            super.attaquer(cible);
        }
    }
}
