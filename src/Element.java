package src;
import java.awt.Color;


public enum Element { // Enumération des éléments

    Neutre(Color.BLACK),
    Feu(new Color(184, 22, 1)), // Element feu
    Eau(new Color(6, 0, 160)), // Element eau
    Terre(new Color(0, 167, 15)), // Element terre
    Air(new Color(242, 211, 0)); // Element air

    private Color color; // Couleur associée à l'élément

    Element(Color color) { // Constructeur
        this.color = color;
    }

    public Color getColor() { // Getter
        return color;
    }

};