package src; 

import java.awt.Color;

public abstract class Entite { // Classe abstraite Entite (représente les entités du jeu : tours, ennemis)
    
    enum Element { // Enumération des éléments

        Neutre(Color.BLACK),
        Feu(new Color(184, 22, 1)),   //Element feu
        Eau(new Color(6, 0, 160)),    //Element eau
        Terre(new Color(0, 167, 15)), //Element terre
        Air(new Color(242, 211, 0));  //Element air

        private final Color color; // Couleur associée à l'élément

        Element(Color color) {     // Constructeur
            this.color = color;
        }

        public Color getColor() {  // Getter
            return color;
        }

    };

    enum ModeAttaque {  // Enumération des modes d'attaque
        SINGLE_TARGET,         // Attaque une seule cible
        MULTIPLE_TARGET,       // Attaque plusieurs cibles
        RANDOM,                // Attaque une cible aléatoire
        NEAREST,               // Attaque la cible la plus proche
        STRONGEST_ATK,         // Attaque la cible avec l'attaque la plus élevée
        STRONGEST_PERCENT_PV;  // Attaque la cible avec le pourcentage de PV le plus élevé
    }

    private int PV;          // Points de vie
    private int ATK;         // Points d'attaque
    private double ATKSpeed; // Vitesse d'attaque
    private int Range;       // Portée d'attaque
    private Element element; // Element de l'entité
    private Tile position;   // Position de l'entité
    private ModeAttaque modeAttaque; // Mode d'attaque

    public Entite(int PV, int ATK, double ATKSpeed, int Range, Element element, Tile position, ModeAttaque modeAttaque) { // Constructeur
        this.PV = PV;
        this.ATK = ATK;
        this.ATKSpeed = ATKSpeed;
        this.Range = Range;
        this.element = element;
        this.position = position;
        this.modeAttaque = modeAttaque;
    }

    public int getPV() { // Getter
        return PV;
    }

    public void setPV(int PV) { // Setter
        this.PV = PV;
    }

    public int getATK() { // Getter
        return ATK;
    }

    public void setATK(int ATK) { // Setter
        this.ATK = ATK;
    }

    public double getATKSpeed() { // Getter
        return ATKSpeed;
    }

    public void setATKSpeed(double ATKSpeed) { // Setter
        this.ATKSpeed = ATKSpeed;
    }

    public int getRange() { // Getter
        return Range;
    }

    public void setRange(int Range) { // Setter
        this.Range = Range;
    }

    public Element getElement() { // Getter
        return element;
    }

    public void setElement(Element element) { // Setter
        this.element = element;
    }

    public Tile getPosition() { // Getter
        return position;
    }

    public void setPosition(Tile position) { // Setter
        this.position = position;
    }

    public ModeAttaque getModeAttaque() { // Getter
        return modeAttaque;
    }

    public void setModeAttaque(ModeAttaque modeAttaque) { // Setter
        this.modeAttaque = modeAttaque;
    }

    
    
}
