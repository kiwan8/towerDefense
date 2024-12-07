package src;

public abstract class Entite {
    
    enum Element {Neutre, Feu, Eau, Terre, Air};

    private int PV;
    private int ATK;
    private double ATKSpeed;
    private int Range;
    private Element element;

    public Entite(int PV, int ATK, double ATKSpeed, int Range, Element element) {
        this.PV = PV;
        this.ATK = ATK;
        this.ATKSpeed = ATKSpeed;
        this.Range = Range;
        this.element = element;
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public double getATKSpeed() {
        return ATKSpeed;
    }

    public void setATKSpeed(double ATKSpeed) {
        this.ATKSpeed = ATKSpeed;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int Range) {
        this.Range = Range;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    
}
