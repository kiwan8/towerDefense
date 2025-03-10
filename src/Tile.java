package src;

/**
 * Classe représentant une case (tile) sur la carte du jeu.
 */
public class Tile {

    /**
     * Ligne de la case sur la carte.
     */
    private final int row;           

    /**
     * Colonne de la case sur la carte.
     */
    private final int col;          

    /**
     * Type de la case, défini par l'énumération {@link TileType}.
     */
    private final TileType type;     

    /**
     * Indique si la case est occupée par une tour.

     * Par défaut, cette valeur est {@code false}.
     */
    private boolean occupiedByTower; 

    /**
     * Constructeur de la classe Tile.
     *
     * @param row  Ligne de la case.
     * @param col  Colonne de la case.
     * @param type Type de la case.
     */
    public Tile(int row, int col, TileType type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.occupiedByTower = false; 
    }

    /**
     * Retourne la ligne de la case.
     *
     * @return La ligne de la case.
     */
    public int getRow() {
        return row;
    }

    /**
     * Retourne la colonne de la case.
     *
     * @return La colonne de la case.
     */
    public int getCol() {
        return col;
    }

    /**
     * Retourne le type de la case.
     *
     * @return Le type de la case.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Vérifie si la case est occupée par une tour.
     *
     * @return True si la case est occupée par une tour, sinon False.
     */
    public boolean isOccupiedByTower() {
        return occupiedByTower;
    }

    /**
     * Définit si la case est occupée par une tour.
     *
     * @param occupied True si la case est occupée par une tour, sinon False.
     */
    public void setOccupiedByTower(boolean occupied) {
        this.occupiedByTower = occupied;
    }

    /**
     * Vérifie si la case est cliquable.
     *
     * @return True si la case est cliquable, sinon False.
     */
    public boolean isClickable() {
        return type.isClickable();
    }

    /**
     * Vérifie si la case est constructible et non occupée.
     *
     * Une case est considérée comme constructible si elle est de type CONSTRUCTIBLE
     * et n'est pas déjà occupée par une tour.
     *
     * @return True si la case est constructible et non occupée, sinon False.
     */
    public boolean isConstructibleAndNotOccupied() {
        return type == TileType.CONSTRUCTIBLE && !occupiedByTower;
    }

    /**
     * Vérifie si la case est constructible.
     *
     * Une case est considérée comme constructible si elle est de type CONSTRUCTIBLE.
     *
     * @return True si la case est constructible, sinon False.
     */
    public boolean isConstructibleBis() {
        return type == TileType.CONSTRUCTIBLE;
    }
}