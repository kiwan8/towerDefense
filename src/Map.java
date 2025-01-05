package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.GameExceptions.*;

/**
 * Représente une carte de jeu chargée à partir d'un fichier, avec des tuiles et des chemins.
 */
public class Map {
    /**
     * Nombre de lignes de la carte.
     */
    private final int rows;

    /**
     * Nombre de colonnes de la carte.
     */
    private final int cols;

    /**
     * Tableau 2D de tuiles représentant la carte.
     */
    private final Tile[][] tiles;

    /**
     * Liste des coordonnées représentant le chemin sur la carte.
     */
    private List<int[]> path;

    /**
     * Liste des coordonnées en pixels représentant le chemin pour l'affichage.
     */
    private final List<double[]> pixelPath;

    /**
     * Chemin du fichier de niveau.
     */
    private final String levelFilePath;

    /**
     * Chemin du fichier de la carte.
     */
    private final String mapFilePath;

    /**
     * Taille d'une cellule en pixels.
     */
    private final double cellSize;

    /**
     * Tableau utilisé pour la recherche en profondeur (DFS) du chemin.
     * 0 : blanc (non visité), 1 : gris (en cours de visite), 2 : noir (visité).
     */
    private int[][] color; // 0: blanc, 1: gris, 2: noir

    /**
     * Crée une carte en la chargeant depuis un fichier.
     *
     * @param mapFilePath   le chemin vers le fichier de la carte.
     * @param levelFilePath le chemin vers le fichier de niveau.
     * @throws IOException     si le fichier ne peut pas être lu.
     * @throws GameException   si la validation de la carte échoue.
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

    /**
     * Retourne la taille d'une cellule en pixels.
     *
     * @return la taille de la cellule.
     */
    public double getCellSize() {
        return cellSize;
    }

    /**
     * Charge la carte et initialise les tuiles, les points de spawn et les bases.
     *
     * @param filePath le chemin vers le fichier de la carte.
     * @return un chemin allant du spawn à la base.
     * @throws IOException     si le fichier ne peut pas être lu.
     * @throws GameException   si la carte est invalide.
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

    /**
     * Valide la carte en vérifiant sa cohérence et la présence des éléments nécessaires.
     *
     * @throws GameException si la carte est invalide.
     * @throws IOException   si une erreur de lecture de fichier survient.
     */
    public void validateMap() throws GameException, IOException {
        List<Tile> enemySpawns = new ArrayList<>();
        List<Tile> playerBases = new ArrayList<>();

        // Vérification de la hauteur et de la largeur constantes 
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
     * Trouve le nombre de lignes et de colonnes dans le fichier de la carte.
     *
     * @param filePath le chemin vers le fichier de la carte.
     * @return un tableau contenant le nombre de lignes et de colonnes.
     * @throws IOException si le fichier ne peut pas être lu.
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

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return le nombre de lignes.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return le nombre de colonnes.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Retourne la tuile à une position spécifique.
     *
     * @param row la ligne de la tuile.
     * @param col la colonne de la tuile.
     * @return la tuile située à la position spécifiée.
     */
    public Tile getTile(final int row, final int col) {
        return tiles[row][col];
    }

    /**
     * Retourne le chemin trouvé sur la carte.
     *
     * @return la liste des coordonnées du chemin.
     */
    public List<int[]> getPath() {
        return this.path;
    }

    /**
     * Recherche (en DFS) un chemin UNIQUE entre le spawn et la base.
     *
     * @param spawn la tuile de spawn.
     * @param base  la tuile de la base.
     * @return la liste des coordonnées représentant le chemin.
     * @throws InvalidMapPathException si le chemin est invalide.
     */
    public List<int[]> findPath(Tile spawn, Tile base) throws InvalidMapPathException {
        // Initialise le tableau color à 0 (BLANC) partout
        color = new int[rows][cols];
        path = new ArrayList<>();

        int spawnRow = spawn.getRow();
        int spawnCol = spawn.getCol();
        int baseRow = base.getRow();
        int baseCol = base.getCol();

        // On lance la DFS : si dfs(...) renvoie true, un chemin a été trouvé
        boolean found = dfs(spawnRow, spawnCol, -1, -1, baseRow, baseCol);
        if (!found) {
            // Si DFS n'a rien trouvé, c'est qu'il n'y a pas de chemin
            throw new InvalidMapPathException(
                    "Error: No valid path found from spawn to base!\n" +
                            "Level file: " + levelFilePath + "\n" +
                            "Map file: " + mapFilePath + "\n" +
                            "Reason: Dead end or disconnected tiles.\n" +
                            "The game will now terminate.");
        }

        // 'path' contient le chemin dans l'ordre spawn -> base
        return path;
    }

    /**
     * DFS récursive pour chercher un chemin unique de (r,c) vers (baseR, baseC).
     *
     * @param r       ligne courante.
     * @param c       colonne courante.
     * @param parentR ligne du parent (pour éviter de revenir direct en arrière).
     * @param parentC colonne du parent.
     * @param baseR   ligne de la base.
     * @param baseC   colonne de la base.
     * @return true si un chemin unique vers la base est trouvé, sinon false.
     * @throws InvalidMapPathException si un cycle ou un embranchement est détecté.
     */
    private boolean dfs(int r, int c, int parentR, int parentC,
                       int baseR, int baseC) throws InvalidMapPathException {

        // Marque la case (r,c) comme "en cours de visite" (GRIS = 1)
        color[r][c] = 1;

        // Ajoute (r,c) à 'path'
        path.add(new int[] { r, c });

        // Si on a atteint la base, c'est gagné
        if (r == baseR && c == baseC) {
            return true;
        }

        // Directions : haut, bas, gauche, droite
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        for (int[] d : dirs) {
            int nr = r + d[0];
            int nc = c + d[1];

            // Vérification des bornes
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                continue;
            }

            // Vérifier que la tuile est passable (ROAD ou BASE)
            TileType type = tiles[nr][nc].getType();
            if (type != TileType.ROAD && type != TileType.BASE) {
                continue;
            }

            // Empêcher de revenir directement à la case parent
            if (nr == parentR && nc == parentC) {
                continue;
            }

            // Switch sur la couleur
            if (color[nr][nc] == 0) {
                // 0 = BLANC => pas encore visité, on explore
                boolean foundBase = dfs(nr, nc, r, c, baseR, baseC);
                if (foundBase) {
                    // Si la base est atteinte via ce chemin, on renvoie true direct
                    return true;
                }
                // Sinon, on continue à tester les autres voisins
            } else if (color[nr][nc] == 1 || color[nr][nc] == 2) {
                // On est retombé sur une tuile en cours de visite => CYCLE
                throw new InvalidMapPathException(
                        "Error: Loop detected in path!\n" +
                                "Level file: " + levelFilePath + "\n" +
                                "Map file: " + mapFilePath + "\n" +
                                "Reason: Path loops back on itself at row " + nr + ", column " + nc
                                + ".\n" +
                                "The game will now terminate.");
            }
        }

        // Si aucun voisin n'a permis d'atteindre la base, on doit faire marche arrière
        // On retire (r,c) du path (backtracking)
        path.remove(path.size() - 1);

        // On marque (r,c) comme NOIR (exploration terminée)
        color[r][c] = 2;

        // Et on retourne false => ce chemin ne mène pas à la base
        return false;
    }

    /**
     * Convertit le chemin en coordonnées de pixels pour l'affichage.
     *
     * @return la liste des coordonnées en pixels du chemin.
     */
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

    /**
     * Retourne le chemin en coordonnées de pixels pour l'affichage.
     *
     * @return la liste des coordonnées en pixels du chemin.
     */
    public List<double[]> getPixelPath() {
        return this.pixelPath;
    }

    /**
     * Méthode récursive de recherche en profondeur (DFS) pour trouver un chemin unique.
     * 
     * @param spawnRow ligne de spawn
     * @param spawnCol colonne de spawn
     * @param baseRow  ligne de la base
     * @param baseCol  colonne de la base
     * @return true si un chemin est trouvé, false sinon
     * @throws InvalidMapPathException si un cycle ou un embranchement est détecté
     */
    // Cette méthode est déjà documentée dans findPath, donc elle est omise ici pour éviter la redondance
}
