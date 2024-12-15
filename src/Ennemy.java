package src;

public abstract class Ennemy extends Entite{

    private int recompense; // Récompense donnée à la mort de l'ennemi
    private int vitesse;    // Vitesse de l'ennemi

    public Ennemy(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque, int vitesse, int recompense) { // Constructeur
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.vitesse = vitesse;
        this.recompense = recompense;
    }

    public int getRecompense() { // Getter
        return recompense;
    }

    public void setRecompense(int recompense) { // Setter
        this.recompense = recompense;
    }

    public int getVitesse() { // Getter
        return vitesse;
    }

    public void setVitesse(int vitesse) { // Setter
        this.vitesse = vitesse;
    }

    public void deplacer(int vitesse) { // Déplace l'ennemi
    }
}