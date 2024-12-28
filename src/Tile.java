package src;

public class Tile {
    private final int row;           
    private final int col;          
    private final TileType type;     // Type de la case (défini par l'enum)
    private boolean occupiedByTower;        // Si la case est occupée par une TOUR uniquement (par défaut : false)

    /**
     * Constructor of the class Tile.
     *
     * @param row  Row of the tile.
     * @param col  Col of the tile.
     * @param type Type of the tile.
     */
    public Tile(int row, int col, TileType type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.occupiedByTower = false; 
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public TileType getType() {
        return type;
    }

    public boolean isOccupiedByTower() {
        return occupiedByTower;
    }

    public void setOccupiedByTower(boolean occupied) {
        this.occupiedByTower = occupied;
    }

    /**
     * Check if the tile is clickable.
     *
     * @return True if the tile is clickable, else False.
     */
    public boolean isClickable() {
        return type.isClickable();
    }

    /**
     * Check if the tile is constructible.
     *
     * A tile is considered constructible if it is of type CONSTRUCTIBLE
     * and is not already occupied by a tower.
     *
     * @return True if the tile is constructible, else False.
     */
    public boolean isConstructible() {
        return type == TileType.CONSTRUCTIBLE && !occupiedByTower;
    }
}
