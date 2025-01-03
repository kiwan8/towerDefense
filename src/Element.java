package src;
import java.awt.Color;

/**
 * L'énumération Element représente les différents éléments du jeu.
 * Chaque élément est associé à une couleur spécifique.
 */
public enum Element {

    /**
     * Élément neutre, associé à la couleur noire.
     */
    Neutre(Color.BLACK),

    /**
     * Élément feu, associé à la couleur rouge.
     */
    Feu(new Color(184, 22, 1)),

    /**
     * Élément eau, associé à la couleur bleue.
     */
    Eau(new Color(6, 0, 160)),

    /**
     * Élément terre, associé à la couleur verte.
     */
    Terre(new Color(0, 167, 15)),

    /**
     * Élément air, associé à la couleur jaune.
     */
    Air(new Color(242, 211, 0));

    /**
     * La couleur associée à l'élément.
     */
    private Color color;

    /**
     * Constructeur de l'énumération Element.
     *
     * @param color La couleur associée à l'élément.
     */
    Element(Color color) {
        this.color = color;
    }

    /**
     * Retourne la couleur associée à l'élément.
     *
     * @return La couleur de l'élément.
     */
    public Color getColor() {
        return color;
    }
}