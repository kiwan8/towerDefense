package src;

public abstract class Ennemy extends Warrior{

    private double MovingSpeed;

    public Ennemy(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque, double Movingspeed) { // Constructeur
        super(PV, ATK, ATKSpeed, Range, element, position, modeAttaque);
        this.MovingSpeed = MovingSpeed;
    }

    
}