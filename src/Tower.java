package src;

import java.awt.Color;

public abstract class Tower extends Warrior {

    private final int cout; // Coût de la tour
    private final Color color; // Couleur de la tour

    public Tower(int PV, int ATK, double ATKSpeed, double Range, Element element, Tile position,
            ModeAttaque modeAttaque, int cout, Color color) {
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.cout = cout;
        this.color = color;
    }

    public int getCout() {
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
