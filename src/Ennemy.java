package src;

public abstract class Ennemy extends Entite{

    public Ennemy(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque) { // Constructeur
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
    }

    
}