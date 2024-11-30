package src;

public class Tile {
    private final int row;           
    private final int col;          
    private final TileType type;     // Type de la case (défini par l'enum)
    private boolean occupied;        // Si la case est occupée (par défaut : false)

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
        this.occupied = false; 
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

    public boolean isOccupied() {
        return occupied;
    }

    
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Check if the tile is clickable.
     *
     * @return True if the tile is clickable, else False.
     */
    public boolean isClickable() {
        return type.isClickable();
    }
}
