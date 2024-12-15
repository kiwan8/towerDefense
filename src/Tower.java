package src;

public abstract class Tower extends Entite{

    private int cout; // Coût de la tour

    public Tower(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque, int cout) { // Constructeur
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.cout = cout;
    }

    @Override
    public void attaquer(Entite cible) { // Attaque une entité
        if (cible instanceof Ennemy) {  // Si la cible est à portée et que c'est un ennemi
            super.attaquer(cible); 
        }
    }

    public int getCout() { // Getter
        return cout;
    }

    public void setCout(int cout) { // Setter
        this.cout = cout;
    }
}