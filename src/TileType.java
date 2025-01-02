package src;

import java.awt.Color;

public enum TileType {
    SPAWN("S", Color.RED, false),        // Case de spawn
    BASE("B", Color.ORANGE, false),      // Case de base
    ROAD("R", new Color(194, 178, 128), false), // Case de route
    CONSTRUCTIBLE("C", Color.LIGHT_GRAY, true), // Case constructible
    NON_CONSTRUCTIBLE("X", new Color(11, 102, 35), false), // Case non constructible
    UNKNOWN("?", Color.BLACK, false);   // Case inconnue

    private final String symbol;        // Représentation du type dans le fichier
    private final Color color;          // Couleur associée au type
    private final boolean clickable;    // Si la case est cliquable ou non

    TileType(String symbol, Color color, boolean clickable) {
        this.symbol = symbol;
        this.color = color;
        this.clickable = clickable;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public boolean isClickable() {
        return clickable;
    }

    /**
     * Trouve le type de case en fonction de son symbole.
     *
     * @param symbol Symbole de la case (e.g., "S", "B").
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
