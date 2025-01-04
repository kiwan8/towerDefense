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
    private final List<double[]> pixelPath;
    private final String levelFilePath;
    private final String mapFilePath;
    private final double cellSize;

    /**
     * Creates a map by loading it from a file.
     *
     * @throws IOException   if the file cannot be read.
     * @throws GameException if the map validation fails.
     */
    public Map(String mapFilePath, String levelFilePath) throws GameException, IOException {
        this.levelFilePath = levelFilePath;
        this.mapFilePath = mapFilePath;
        final int[] dimensions = FindDimensions(mapFilePath);
        this.rows = dimensions[0];
        this.cols = dimensions[1];
        this.tiles = new Tile[rows][cols];
        this.path = loadMap(mapFilePath);
        this.pixelPath = setPixelPath();
        this.cellSize = Math.min(700.0 / rows, 700.0 / cols);
    }

    public double getCellSize() {
        return cellSize;
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

        validateMap();

        return findPath(spawn, base);
    }

    public void validateMap() throws GameException, IOException {
        List<Tile> enemySpawns = new ArrayList<>();
        List<Tile> playerBases = new ArrayList<>();

        // Vérification de la hauteur et de la largeur constantes //TODO faire
        // différenciation des erreurs
        int expectedCols = this.getCols();
        for (int row = 0; row < this.getRows(); row++) {
            int actualCols = 0;

            for (int col = 0; col < expectedCols; col++) {
                Tile tile;

                // Vérification des colonnes hors limites
                if (col < this.getCols() && (tile = this.getTile(row, col)) != null) {
                    actualCols++;

                    // Vérification des tuiles inconnues
                    if (tile.getType() == TileType.UNKNOWN) {
                        throw new MapException(
                                "Error: Unknown tile detected!\n" +
                                        "Level file: " + levelFilePath + "\n" +
                                        "Map file: " + mapFilePath + "\n" +
                                        "Reason: Unknown tile at row " + row + ", column " + col + ".\n" +
                                        "The game will now terminate.");
                    }

                    // Ajout des spawns et bases
                    if (tile.getType() == TileType.SPAWN) {
                        enemySpawns.add(tile);
                    } else if (tile.getType() == TileType.BASE) {
                        playerBases.add(tile);
                    }
                }
            }

            // Vérification de la largeur constante
            if (actualCols != expectedCols) {
                throw new MapException(
                        "Error: Inconsistent row width detected!\n" +
                                "Level file: " + levelFilePath + "\n" +
                                "Map file: " + mapFilePath + "\n" +
                                "Reason: Row " + row + " has width " + actualCols + " instead of " + expectedCols
                                + ".\n" +
                                "The game will now terminate.");
            }
        }

        // Vérification des dimensions
        if (this.getRows() == 0 || this.getCols() == 0) {
            throw new MapException(
                    "Error: Invalid map dimensions!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "Reason: The map has invalid dimensions (zero height or width).\n" +
                            "The game will now terminate.");
        }

        // Vérification des spawns et bases
        if (enemySpawns.isEmpty()) {
            throw new NoEnemySpawnException(
                    "Error: No enemy spawns found!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "The game will now terminate.");
        }

        if (playerBases.isEmpty()) {
            throw new NoPlayerBaseException(
                    "Error: No player base found!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "The game will now terminate.");
        }

        if (playerBases.size() > 1) {
            StringBuilder positions = new StringBuilder();
            for (Tile base : playerBases) {
                positions.append(" - Row: ").append(base.getRow()).append(", Column: ").append(base.getCol())
                        .append("\n");
            }
            throw new MultiplePlayerBaseException(
                    "Error: Multiple player bases found!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "Positions of player bases:\n" + positions +
                            "The game will now terminate.");
        }

        if (enemySpawns.size() > 1) {
            StringBuilder positions = new StringBuilder();
            for (Tile spawn : enemySpawns) {
                positions.append(" - Row: ").append(spawn.getRow()).append(", Column: ").append(spawn.getCol())
                        .append("\n");
            }
            throw new MultipleEnemySpawnException(
                    "Error: Multiple enemy spawns found!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "Positions of enemy spawns:\n" + positions +
                            "The game will now terminate.");
        }
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
        return new int[] { rowCount, colCount };
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

    public List<int[]> getPath() {
        return this.path;
    }

    /**
     * Finds the path from a spawn tile to a base tile.
     *
     * @param spawn the spawn tile.
     * @param base  the base tile.
     * @return the path as a list of coordinates.
     */
    private List<int[]> findPath(final Tile spawn, final Tile base) throws InvalidMapPathException {
        final List<int[]> path = new ArrayList<>();
        final int[][] directions = {
                { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
        };

        boolean[][] visited = new boolean[rows][cols];
        int currentRow = spawn.getRow();
        int currentCol = spawn.getCol();
        int lastVisitedRow = currentRow;
        int lastVisitedCol = currentCol;

        final int baseRow = base.getRow();
        final int baseCol = base.getCol();

        while (currentRow != baseRow || currentCol != baseCol) {
            boolean moved = false;

            for (int[] dir : directions) {
                final int testRow = currentRow + dir[0];
                final int testCol = currentCol + dir[1];

                if (testRow < 0 || testRow >= rows || testCol < 0 || testCol >= cols) {
                    continue; // Ignore out-of-bounds tiles
                }

                final Tile testTile = tiles[testRow][testCol];
                final boolean isRoadOrBase = testTile.getType().equals(TileType.ROAD) ||
                        testTile.getType().equals(TileType.BASE);
                final boolean isNotBacktracking = testRow != lastVisitedRow || testCol != lastVisitedCol;

                if (isRoadOrBase && isNotBacktracking) {
                    if (visited[testRow][testCol]) {
                        throw new InvalidMapPathException(
                                "Error: Loop detected in path!\n" +
                                        "Level file: " + levelFilePath + "\n" +
                                        "Map file: " + mapFilePath + "\n" +
                                        "Reason: Path loops back on itself at row " + testRow + ", column " + testCol
                                        + ".\n" +
                                        "The game will now terminate.");
                    }

                    visited[currentRow][currentCol] = true;
                    path.add(new int[] { currentRow, currentCol });
                    lastVisitedRow = currentRow;
                    lastVisitedCol = currentCol;
                    currentRow = testRow;
                    currentCol = testCol;
                    moved = true;
                    break;
                }
            }

            if (!moved) {
                throw new InvalidMapPathException(
                        "Error: No valid path found from spawn to base!\n" +
                                "Level file: " + levelFilePath + "\n" +
                                "Map file: " + mapFilePath + "\n" +
                                "Reason: Dead end or disconnected tiles.\n" +
                                "The game will now terminate.");
            }
        }

        // Check for multiple paths (if visited tiles could lead to ambiguity)
        for (int[] dir : directions) {
            final int testRow = currentRow + dir[0];
            final int testCol = currentCol + dir[1];

            if (testRow >= 0 && testRow < rows && testCol >= 0 && testCol < cols) {
                if (tiles[testRow][testCol].getType().equals(TileType.ROAD) && !visited[testRow][testCol]) {
                    throw new InvalidMapPathException(
                            "Error: Multiple paths detected!\n" +
                                    "Level file: " + levelFilePath + "\n" +
                                    "Map file: " + mapFilePath + "\n" +
                                    "Reason: Ambiguous routes leading to the base.\n" +
                                    "The game will now terminate.");
                }
            }
        }

        path.add(new int[] { baseRow, baseCol });
        return path;
    }

    private List<double[]> setPixelPath() {
        List<double[]> pixelPath = new ArrayList<>();
        double cellSize = Math.min(700.0 / rows, 700.0 / cols); // Calcule la taille d'une cellule
        double centerX = 350;
        double centerY = 350;

        for (int[] tile : path) {
            double pixelX = centerX - 350 + tile[1] * cellSize + cellSize / 2;
            double pixelY = centerY + 350 - tile[0] * cellSize - cellSize / 2;
            pixelPath.add(new double[] { pixelX, pixelY });
        }
        return pixelPath;
    }

    public List<double[]> getPixelPath() {
        return this.pixelPath;
    }

     public static void main(String[] args) throws GameException, IOException {
        Map newMap = new Map("resources/maps/Error_Path_Loop_1.mtp", "null");
    }
}
