package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import src.GameExceptions.*;

/**
 * Represents a game map loaded from a file, with tiles and paths.
 */
public class Map {
    private final int rows;
    private final int cols;
    private final Tile[][] tiles;
    private final List<int[]> path;

    /**
     * Creates a map by loading it from a file.
     *
     * @param filePath the path to the map file.
     * @throws IOException   if the file cannot be read.
     * @throws GameException if the map validation fails.
     */
    public Map(final String filePath) throws GameException, IOException {
        final int[] dimensions = FindDimensions(filePath);
        this.rows = dimensions[0];
        this.cols = dimensions[1];
        this.tiles = new Tile[rows][cols];
        this.path = loadMap(filePath);
    }

    /**
     * Find the number of rows and columns in the map file.
     *
     * @param filePath the path to the map file.
     * @return an array with the number of rows and columns.
     * @throws IOException if the file cannot be read.
     */
    private int[] FindDimensions(final String filePath) throws IOException {
        int rowCount = 0;
        int colCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rowCount++;
                colCount = Math.max(colCount, line.length());
            }
        }
        return new int[]{rowCount, colCount};
    }

    /**
     * Loads the map and initializes tiles, spawn points, and base points.
     *
     * @param filePath the path to the map file.
     * @return a path from spawn to base.
     * @throws IOException   if the file cannot be read.
     * @throws GameException if the map is invalid.
     */
    private List<int[]> loadMap(final String filePath) throws IOException, GameException {
        final List<Tile> enemySpawns = new ArrayList<>();
        final List<Tile> playerBases = new ArrayList<>();
        Tile spawn = null;
        Tile base = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (int row = 0; row < rows; row++) {
                final String line = br.readLine();
                for (int col = 0; col < line.length(); col++) {
                    final char symbol = line.charAt(col);
                    final TileType type = TileType.fromSymbol(String.valueOf(symbol));

                    if (type.equals(TileType.SPAWN)) {
                        spawn = new Tile(row, col, type);
                        enemySpawns.add(spawn);
                    } else if (type.equals(TileType.BASE)) {
                        base = new Tile(row, col, type);
                        playerBases.add(base);
                    }
                    this.tiles[row][col] = new Tile(row, col, type);
                }
            }
        }

        validateMap(enemySpawns, playerBases);

        

        return findPath(spawn, base);
    }

    /**
     * Validates the map
     *
     * @param enemySpawns the list of spawn tiles.
     * @param playerBases the list of base tiles.
     * @throws GameException if validation fails.
     */
    private void validateMap(final List<Tile> enemySpawns, final List<Tile> playerBases) throws GameException {
        if (enemySpawns.isEmpty()) {
            throw new NoEnemySpawnException("The map must contain at least one spawn tile.");
        }
        if (playerBases.isEmpty()) {
            throw new NoPlayerBaseException("The map must contain at least one base tile.");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Tile getTile(final int row, final int col) {
        return tiles[row][col];
    }

    public List<int[]> getPath(){
        return this.path;
    }

    /**
     * Finds the path from a spawn tile to a base tile.
     *
     * @param spawn the spawn tile.
     * @param base  the base tile.
     * @return the path as a list of coordinates.
     */
    private List<int[]> findPath(final Tile spawn, final Tile base) {
        final List<int[]> path = new ArrayList<>();
        final int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        int currentRow = spawn.getRow();
        int currentCol = spawn.getCol();
        int lastVisitedRow = currentRow;
        int lastVisitedCol = currentCol;

        final int baseRow = base.getRow();
        final int baseCol = base.getCol();

        while (currentRow != baseRow || currentCol != baseCol) {
            for (int[] dir : directions) {
                final int testRow = currentRow + dir[0];
                final int testCol = currentCol + dir[1];

                final boolean isRoadOrBase = tiles[testRow][testCol].getType().equals(TileType.ROAD) ||
                        tiles[testRow][testCol].getType().equals(TileType.BASE);
                final boolean isNotBacktracking = testRow != lastVisitedRow || testCol != lastVisitedCol;

                if (isRoadOrBase && isNotBacktracking) {
                    path.add(new int[]{currentRow, currentCol});
                    lastVisitedRow = currentRow;
                    lastVisitedCol = currentCol;
                    currentRow = testRow;
                    currentCol = testCol;
                    break;
                }
            }
        }
        path.add(new int[]{baseRow, baseCol});
        return path;
    }
}
