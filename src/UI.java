package src;

import java.awt.Font;
import java.awt.Color;

import src.libraries.StdDraw;

public class UI {

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

        // Draw background rectangle
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);

        // Draw border
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        // Draw text
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
        // Zone dimensions
        double centerX = 856;
        double centerY = 641;
        double halfWidth = 144;
        double halfHeight = 25;

        // Draw the white background and green border
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);

        // pour les pièces
        // Cercle 1 jaune
        StdDraw.setPenColor(StdDraw.YELLOW); // Définit la couleur du cercle
        StdDraw.filledCircle(centerX - 120, centerY, 20);

        // Cercle 2 gris petit
        StdDraw.setPenColor(StdDraw.GRAY); // Définit la couleur du cercle
        StdDraw.filledCircle(centerX - 120, centerY, 15);

        StdDraw.text(centerX - 70, centerY, "" + coinCount);

        // partie coeur et santé

        StdDraw.setPenColor(StdDraw.RED);
        drawHeart(centerX + halfWidth - 25, centerY, 21); // Heart symbol

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
        // Définir les coordonnées X du cœur
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

        // Définir les coordonnées Y du cœur
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

        // Appliquer la couleur et dessiner le cœur
        StdDraw.setPenColor(new Color(223, 75, 95)); // Couleur du cœur
        StdDraw.filledPolygon(listX, listY);
    }

   
    /**
     * Draw the map zone with a grid.
     */
    private static void drawMapZone() {
        double centerX = 350;
        double centerY = 350;
        double halfLength = 350; 

        
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(centerX, centerY, halfLength);

        
        // StdDraw.setPenColor(StdDraw.BLACK);
        // int gridSize = 10; 
        // double cellSize = halfLength * 2 / gridSize;
        // for (int i = 0; i <= gridSize; i++) {
        //     StdDraw.line(centerX - halfLength, centerY - halfLength + i * cellSize,
        //             centerX + halfLength, centerY - halfLength + i * cellSize); 
        //     StdDraw.line(centerX - halfLength + i * cellSize, centerY - halfLength,
        //             centerX - halfLength + i * cellSize, centerY + halfLength); 
        // }
    }

    /**
     * Draw the shop zone with a border.
     */
    private static void drawShopZone() {
        double centerX = 856;
        double centerY = 303;
        double halfWidth = 144;
        double halfHeight = 303;

        // Draw the background
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);

        // Draw the border
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

        // Parameters for the tower cards
        double cardWidth = halfWidth * 2; // Full width of the shop zone
        double cardHeight = 25;
        double startY = centerY + halfHeight - cardHeight / 2;

        // Define the towers
        Tower[] towers = {
                new Tower("Archer", 30, 5, 1.0, 2, "NONE", 20, StdDraw.BLACK),
                new Tower("Wind Caster", 30, 5, 1.5, 6, "WIND", 50, StdDraw.YELLOW),
                new Tower("Water Caster", 30, 3, 1.0, 4, "WATER", 50, StdDraw.BLUE),
                new Tower("Earth Caster", 50, 7, 0.5, 2.5, "EARTH", 100, StdDraw.GREEN),
                new Tower("Fire Caster", 30, 10, 0.5, 2.5, "FIRE", 100, StdDraw.RED)
        };

        // Draw the towers
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
         * Initialize and draw all UI components.
         */
        public static void initUI() {
            initCanvas();
            drawMapZone();
            drawPlayerInfoZone(100, 100);
            drawShopZone();
            drawGameInfoZone("1/3", "1/4");
            StdDraw.show();
        }

        /**
         * Updates the UI by redrawing all dynamic components with current game data.
         */
        public void update() {
            throw new UnsupportedOperationException("Not implemented yet");
            // // Exemple : récupération des données dynamiques
            // int currentLifeCount = Game.getLifeCount();
                                                             
            // int currentCoinCount = Game.getCoinCount(); 
            // String currentLevel = Game.getCurrentLevel(); 
            // String currentWave = Game.getCurrentWave(); 

            // Tower[] currentTowers = Game.getShopTowers(); 

        
            // StdDraw.clear();

           
            // drawMapZone();
            // drawPlayerInfoZone(currentLifeCount, currentCoinCount);
            // drawShopZone();
            // drawGameInfoZone(currentLevel, currentWave);

            // // Montre le nouvel écran
            // StdDraw.show();
        }

    
}
