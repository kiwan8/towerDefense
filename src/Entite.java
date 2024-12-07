package src;

import java.awt.Color;

public abstract class Entite {
    
    enum Element {
        
        Neutre(Color.BLACK),
        Feu(new Color(184, 22, 1)),
        Eau(new Color(6, 0, 160)),
        Terre(new Color(0, 167, 15)),
        Air(new Color(242, 211, 0));

        private final Color color;

        Element(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

    };

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
