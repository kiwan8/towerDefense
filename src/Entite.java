package src; 

import java.awt.Color;

enum Element { // Enumération des éléments

    Neutre(Color.BLACK),
    Feu(new Color(184, 22, 1)),   //Element feu
    Eau(new Color(6, 0, 160)),    //Element eau
    Terre(new Color(0, 167, 15)), //Element terre
    Air(new Color(242, 211, 0));  //Element air

    private Color color; // Couleur associée à l'élément

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
        NEAREST_FROM_BASE,     // Attaque la cible la plus proche de la base
        STRONGEST_ATK,         // Attaque la cible avec l'attaque la plus élevée
        STRONGEST_PERCENT_PV;  // Attaque la cible avec le pourcentage de PV le plus élevé
};

public abstract class Entite { // Classe abstraite Entite (représente les entités du jeu : tours, ennemis)

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

    public double vulnérabilité(Element element) { // Méthode qui retourne le coefficient de vulnérabilité de l'entité par rapport à un élément
        if (this.element == Element.Feu) {         // Si l'entité est de type feu
            if (element == Element.Eau) {
                return 1.5;
            } else if (element == Element.Terre) {
                return 0.5;
            }
        } else if (this.element == Element.Eau) {  // Si l'entité est de type eau
            if (element == Element.Air) { 
                return 1.5;
            } else if (element == Element.Feu) {
                return 0.5;
            }
        } else if (this.element == Element.Terre) { // Si l'entité est de type terre
            if (element == Element.Feu) {
                return 1.5;
            } else if (element == Element.Air) {
                return 0.5;
            }
        } else if (this.element == Element.Air) {   // Si l'entité est de type air
            if (element == Element.Terre) {
                return 1.5;
            } else if (element == Element.Eau) {
                return 0.5;
            }
        }
        return 1;
    }

    public void attaquer(Entite cible) { // Méthode qui permet à l'entité d'attaquer
            cible.PV -= (int) (this.ATK * cible.vulnérabilité(this.element));
    }

    public int distance(Entite cible) { // Méthode qui retourne la distance entre l'entité et une autre entité
        return (int) Math.sqrt(Math.pow(this.position.getCol() - cible.position.getCol(), 2) + Math.pow(this.position.getRow() - cible.position.getRow(), 2)); //TODO: à vérifier
    }
    
}
