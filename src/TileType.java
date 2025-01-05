package src;

import java.awt.Color;

/**
 * Énumération représentant les différents types de cases sur la carte du jeu.
 */
public enum TileType {
    /**
     * Case de spawn où les ennemis apparaissent.
     */
    SPAWN("S", Color.RED, false),        

    /**
     * Case de base où les joueurs défendent.
     */
    BASE("B", Color.ORANGE, false),      

    /**
     * Case de route que les ennemis empruntent.
     */
    ROAD("R", new Color(194, 178, 128), false), 

    /**
     * Case constructible où les joueurs peuvent ériger des tours.
     */
    CONSTRUCTIBLE("C", Color.LIGHT_GRAY, true), 

    /**
     * Case non constructible où les joueurs ne peuvent pas ériger de tours.
     */
    NON_CONSTRUCTIBLE("X", new Color(11, 102, 35), false), 

    /**
     * Case inconnue utilisée lorsqu'un symbole non reconnu est rencontré.
     */
    UNKNOWN("?", Color.BLACK, false);   

    /**
     * Représentation symbolique du type de case dans le fichier de configuration.
     */
    private final String symbol;        

    /**
     * Couleur associée au type de case pour l'affichage graphique.
     */
    private final Color color;          

    /**
     * Indique si la case est cliquable ou non dans l'interface utilisateur.
     */
    private final boolean clickable;    

    /**
     * Constructeur de l'énumération TileType.
     *
     * @param symbol     Représentation symbolique de la case (par exemple, "S" pour SPAWN).
     * @param color      Couleur associée à la case pour l'affichage.
     * @param clickable  Indique si la case peut être cliquée par le joueur.
     */
    TileType(String symbol, Color color, boolean clickable) {
        this.symbol = symbol;
        this.color = color;
        this.clickable = clickable;
    }

    /**
     * Retourne le symbole représentant le type de case.
     *
     * @return Le symbole de la case.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Retourne la couleur associée au type de case.
     *
     * @return La couleur de la case.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Indique si la case est cliquable.
     *
     * @return True si la case est cliquable, sinon False.
     */
    public boolean isClickable() {
        return clickable;
    }

    /**
     * Trouve le type de case correspondant à un symbole donné.
     *
     * @param symbol Symbole de la case (par exemple, "S" pour SPAWN, "B" pour BASE).
     * @return Le type de case correspondant ou UNKNOWN si le symbole n'est pas reconnu.
     */
    public static TileType fromSymbol(String symbol) {
        for (TileType type : TileType.values()) {
            if (type.getSymbol().equals(symbol)) {
                return type;
            }
        }
        return UNKNOWN; // Retourne UNKNOWN si aucun type ne correspond
    }
}
