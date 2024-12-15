package src;

import java.awt.Font;
import java.io.IOException;
import java.awt.Color;

import src.libraries.StdDraw;

public class UI {

    private Map map;

    public UI(Map Map) {
        this.map = Map;
        initCanvas();
        drawMapZone(map);
        drawPlayerInfoZone(Game.getPlayer().getHP(), Game.getPlayer().getArgent());
        drawShopZone();
        drawGameInfoZone("1/3", "1/4");
        StdDraw.show();
    }

    /**
     * Initialize the canvas with specified dimensions and scaling.
     */
    private static void initCanvas() {
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Draws the game info zone, including the level and wave indicators.
     *
     * @param level The current level, e.g., "1/4".
     * @param wave  The current wave, e.g., "1/2".
     */
    private static void drawGameInfoZone(String level, String wave) {
        double centerX = 856;
        double centerY = 688;
        double halfWidth = 144;
        double halfHeight = 12;

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        Font font = new Font("Arial", Font.BOLD, 23);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(centerX - halfWidth / 2, centerY, "LVL: " + level);
        StdDraw.text(centerX + halfWidth / 2, centerY, "WAVE: " + wave);
    }

    /**
     * Draw the player info zone with dynamic life and coin count.
     *
     * @param lifeCount Number of lives.
     * @param coinCount Number of coins.
     */
    private static void drawPlayerInfoZone(int lifeCount, int coinCount) {
        double centerX = 856;
        double centerY = 641;
        double halfWidth = 144;
        double halfHeight = 25;

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);

        // pour les pièces
        // Cercle 1 jaune
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledCircle(centerX - 120, centerY, 20);

        // Cercle 2 gris petit
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(centerX - 120, centerY, 15);

        StdDraw.text(centerX - 70, centerY, "" + coinCount);

        // partie coeur et santé

        StdDraw.setPenColor(StdDraw.RED);
        drawHeart(centerX + halfWidth - 25, centerY, 21);

        StdDraw.text(centerX + 70, centerY, String.valueOf(lifeCount));
    }

    /**
     * Draws a heart shape at a specified location.
     *
     * @param centerX X-coordinate of the heart center.
     * @param centerY Y-coordinate of the heart center.
     * @param size    Size of the heart.
     */
    private static void drawHeart(double centerX, double centerY, double halfHeight) {
        double[] listX = new double[] {
                centerX,
                centerX - halfHeight,
                centerX - halfHeight,
                centerX - 0.66 * halfHeight,
                centerX - 0.33 * halfHeight,
                centerX,
                centerX + 0.33 * halfHeight,
                centerX + 0.66 * halfHeight,
                centerX + halfHeight,
                centerX + halfHeight
        };

        double[] listY = new double[] {
                centerY - halfHeight,
                centerY,
                centerY + 0.5 * halfHeight,
                centerY + halfHeight,
                centerY + halfHeight,
                centerY + 0.5 * halfHeight,
                centerY + halfHeight,
                centerY + halfHeight,
                centerY + 0.5 * halfHeight,
                centerY
        };

        StdDraw.setPenColor(new Color(223, 75, 95));
        StdDraw.filledPolygon(listX, listY);
    }

    /**
     * Draw the map zone with a grid.
     *
     * @param map L'objet Map à dessiner.
     */
    private static void drawMapZone(Map map) {
        double centerX = 350; 
        double centerY = 350; 
        double halfLength = 350; 

        // Taille d'une cellule (dynamique selon les dimensions de la carte) //TODO: A
        // optimiser
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());

        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                Tile tile = map.getTile(row, col);

                // Calcule du centre de chaque cellule
                double x = centerX - halfLength + col * cellSize + cellSize / 2;
                double y = centerY + halfLength - row * cellSize - cellSize / 2;

                // Remplir la cellule avec la couleur appropriée
                StdDraw.setPenColor(tile.getType().getColor());
                StdDraw.filledSquare(x, y, cellSize / 2);

                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.square(x, y, cellSize / 2);
                // pour plus tard
                if (tile.isOccupied()) {
                    StdDraw.setPenColor(Color.GRAY);
                    StdDraw.filledCircle(x, y, cellSize / 4);
                }
            }
        }
    }

    /**
     * Draw the shop zone with a border.
     */
    private static void drawShopZone() {
        double centerX = 856;
        double centerY = 303;
        double halfWidth = 144;
        double halfHeight = 303;

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        // Taille des articles magasins
        double cardWidth = halfWidth * 2;
        double cardHeight = 25;
        double startY = centerY + halfHeight - cardHeight / 2;

        // TODO: Uniquement pour test : A supprimer
        Tower[] towers = {
                new Tower("Archer", 30, 5, 1.0, 2, "NONE", 20, StdDraw.BLACK),
                new Tower("Wind Caster", 30, 5, 1.5, 6, "WIND", 50, StdDraw.YELLOW),
                new Tower("Water Caster", 30, 3, 1.0, 4, "WATER", 50, StdDraw.BLUE),
                new Tower("Earth Caster", 50, 7, 0.5, 2.5, "EARTH", 100, StdDraw.GREEN),
                new Tower("Fire Caster", 30, 10, 0.5, 2.5, "FIRE", 100, StdDraw.RED)
        };

        for (int i = 0; i < towers.length; i++) {
            double yPosition = startY - i * cardHeight;
            drawTowerCard(centerX, yPosition, cardWidth, cardHeight, towers[i]);
        }
    }

    /**
     * Draw a single tower card in the shop zone.
     *
     * @param x      The center x position of the card.
     * @param y      The center y position of the card.
     * @param width  The width of the card.
     * @param height The height of the card.
     * @param tower  The tower to display.
     */
    private static void drawTowerCard(double x, double y, double width, double height, Tower tower) {
        // Draw the card background
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(x, y, width / 2, height / 2);

        // Draw the border
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.rectangle(x, y, width / 2, height / 2);

        // Draw the tower details
        StdDraw.setPenColor(tower.color);
        StdDraw.filledCircle(x - width / 2 + height, y, height / 3);

        Font font = new Font("Arial", Font.PLAIN, 10);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.textLeft(x - width / 2 + height * 2, y,
                "PV: " + tower.pv + " | ATK: " + tower.atk +
                        " | SPD: " + tower.atkSpeed + " | PO: " + tower.range +
                        " | Cost: " + tower.cost);
    }

    /**
     * TODO : A remplacer par la vrai classe Tower
     */
    static class Tower {
        String name;
        int pv;
        int atk;
        double atkSpeed;
        double range;
        String element;
        int cost;
        Color color;

        private Tower(String name, int pv, int atk, double atkSpeed, double range, String element, int cost,
                Color color) {
            this.name = name;
            this.pv = pv;
            this.atk = atk;
            this.atkSpeed = atkSpeed;
            this.range = range;
            this.element = element;
            this.cost = cost;
            this.color = color;
        }
    }

    /**
     * Updates the UI by redrawing all dynamic components with current game data.
     */
    public void update() {
        // throw new UnsupportedOperationException("Not implemented yet");
        // Exemple : récupération des données dynamiques
        int currentLifeCount = Game.getPlayer().getHP();

        int currentCoinCount = Game.getPlayer().getArgent();
        //String currentLevel = Game.getCurrentLevel();
        //String currentWave = Game.getCurrentWave();

        //Tower[] currentTowers = Game.getShopTowers();

        StdDraw.clear();

        drawMapZone(Game.getMap());
        drawPlayerInfoZone(currentLifeCount, currentCoinCount);
        drawShopZone();
        //drawGameInfoZone(currentLevel, currentWave);

        // Montre le nouvel écran
        StdDraw.show();
    }

    public static void main(String[] args) throws IOException, GameExceptions.GameException {

        // Initialiser la carte à partir d'un fichier
        Map map = new Map("resources\\maps\\10-10.mtp");
        // Initialiser l'interface utilisateur
        UI ui = new UI(map);
        while (true) {
            if (StdDraw.isMousePressed()) {
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();

                ui.handleClick(mouseX, mouseY);

                StdDraw.show();
                StdDraw.pause(100);
            }
        }

    }
    ///////////////////////////////////////////////////////////////////
    ////////////////////Gestion des clicks/////////////////////////////
    ///////////////////////////////////////////////////////////////////
    
    /**
     * Vérifie si un clic est sur une case et, si la case est cliquable, y place un
     * cercle.
     */
    public void handleClick(double mouseX, double mouseY) {
        double centerX = 350; 
        double centerY = 350; 
        double halfLength = 350; 

        // Taille d'une cellule
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());

        // Détermination des indices de la case cliquée
        int col = (int) ((mouseX - (centerX - halfLength)) / cellSize);
        int row = (int) ((centerY + halfLength - mouseY) / cellSize);

        // Vérifie que les indices sont dans les limites de la carte
        if (row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols()) {
            Tile clickedTile = map.getTile(row, col);


            //TODO: A supp uniquement pour debug
            if (clickedTile.isClickable()) {
                drawCircleOnTile(row, col, cellSize / 3); 
            }
        }
    }

   
    private void drawCircleOnTile(int row, int col, double radius) {
        double centerX = 350; 
        double centerY = 350; 
        double halfLength = 350; 

        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());

        double x = centerX - halfLength + col * cellSize + cellSize / 2;
        double y = centerY + halfLength - row * cellSize - cellSize / 2;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(x, y, radius);
    }

    
    public void listenForClicks() {
        while (true) {
            if (StdDraw.isMousePressed()) {
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();

                handleClick(mouseX, mouseY);

                StdDraw.show();
                StdDraw.pause(100); 
            }
        }
    }

}
