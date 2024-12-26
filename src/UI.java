package src;

import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.awt.Color;

import src.Monsters.Boss;
import src.Monsters.Minion;
import src.Towers.Archer;
import src.Towers.EarthCaster;
import src.Towers.FireCaster;
import src.Towers.GoldDigger;
import src.Towers.IceCaster;
import src.Towers.PoisonCaster;
import src.Towers.Railgun;
import src.Towers.WaterCaster;
import src.Towers.WindCaster;
import src.libraries.StdDraw;

public class UI {

    private Map map;

    public UI(Map Map) {
        this.map = Map;
        initCanvas();
        drawMapZone(map);
        drawPlayerInfoZone(Game.getPlayer().getHP(), Game.getPlayer().getArgent());
        drawShopZone();
        drawGameInfoZone();
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
    private static void drawGameInfoZone() {
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

        String level = Game.getcptLevel()+1+"/"+Game.getLevels().size();
        Level LevelCourant = Game.getLevels().get(Game.getcptLevel());

        int nbTotalWavesduLevel = LevelCourant.getWaves().size();
        String wave = Game.getcptWave()+1+"/"+nbTotalWavesduLevel;

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
                if (tile.isOccupiedByTower()) {
                    StdDraw.setPenColor(Color.GRAY);
                    StdDraw.filledCircle(x, y, cellSize / 4);
                }
            }
        }
    }

    /**
     * Draw the shop zone with a border.
     */
    private void drawShopZone() {
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
    
        // Liste des tours disponibles
        Tower[] towers = {
                new Archer(null),
                new WindCaster(null),
                new WaterCaster(null),
                new EarthCaster(null),
                new FireCaster(null),
                new IceCaster(null),
                new PoisonCaster(null),
                new GoldDigger(null),
                new Railgun(null)
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
        // Dessine le fond de la carte
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(x, y, width / 2, height / 2);
    
        // Dessine la bordure
        StdDraw.setPenColor(tower.getColor());
        StdDraw.rectangle(x, y, width / 2, height / 2);
    
        // Dessine le symbole de la tour
        StdDraw.setPenColor(tower.getColor());
        StdDraw.filledCircle(x - width / 2 + height /2, y, height / 3);
    
        // Affiche les détails de la tour
        Font font = new Font("Arial", Font.PLAIN, 10);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BLACK);
    
        StdDraw.textLeft(x - width / 2 + height , y,
                tower.getClass().getSimpleName() + " | PV: " + tower.getPV() +
                        " | ATK: " + tower.getATK() +
                        " | SPD: " + tower.getATKSpeed() +
                        " | PO: " + tower.getRange() +
                        " | Cost: " + tower.getCout());
    }
    

    private void drawEnemies(List<Ennemy> enemies) {
        for (Ennemy enemy : enemies) {
            double x = enemy.getX();
            double y = enemy.getY();

            // Dessiner chaque ennemi en fonction de son type
            switch (enemy.getClass().getSimpleName()) {
                case "Minion":
                    StdDraw.setPenColor(StdDraw.YELLOW);
                    StdDraw.filledCircle(x, y, 5); // Cercle jaune pour Minion
                    break;

                case "WindGrognard":
                    StdDraw.setPenColor(new Color(242, 211, 0)); // Jaune foncé pour Wind Grognard
                    StdDraw.filledCircle(x, y, 7); // Cercle légèrement plus grand
                    break;

                case "FireGrognard":
                    StdDraw.setPenColor(new Color(184, 22, 1)); // Rouge pour Fire Grognard
                    StdDraw.filledCircle(x, y, 7);
                    break;

                case "WaterBrute":
                    StdDraw.setPenColor(new Color(6, 0, 160)); // Bleu pour Water Brute
                    StdDraw.filledRectangle(x, y, 8, 8); // Rectangle moyen
                    break;

                case "EarthBrute":
                    StdDraw.setPenColor(new Color(0, 167, 15)); // Vert pour Earth Brute
                    StdDraw.filledRectangle(x, y, 8, 8); // Rectangle moyen
                    break;

                case "Boss":
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledRectangle(x, y, 15, 15); // Rectangle plus grand pour Boss
                    break;

                default:
                    // Couleur grise par défaut si le type d'ennemi n'est pas spécifié
                    StdDraw.setPenColor(StdDraw.GRAY);
                    StdDraw.filledCircle(x, y, 5);
                    break;
            }

            // Dessiner une barre de vie au-dessus de l'ennemi
            drawHealthBar(enemy, x, y + 10);
        }
    }

    /**
     * Dessine une barre de vie pour un ennemi.
     *
     * @param enemy L'ennemi pour lequel dessiner la barre de vie.
     * @param x     Coordonnée X de l'ennemi.
     * @param y     Coordonnée Y au-dessus de l'ennemi.
     */
    private void drawHealthBar(Ennemy enemy, double x, double y) {
        double healthPercentage = (double) enemy.getPV() / enemy.getMaxPV();
        double barWidth = 20;
        double barHeight = 3;

        // Barre d'arrière-plan (grise)
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledRectangle(x, y, barWidth / 2, barHeight / 2);

        // Barre de vie (verte)
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(x - (barWidth / 2) * (1 - healthPercentage), y, (barWidth * healthPercentage) / 2,
                barHeight / 2);
    }

    private void drawTowers(List<Tower> towers) {
        for (Tower tower : towers) {
            // Obtenez les coordonnées x et y de la base de la tour
            int colonne = tower.getPosition().getCol();
            int ligne = tower.getPosition().getRow();
    
            // Définissez la couleur de la tour à partir de son getter
            StdDraw.setPenColor(tower.getColor());
    
            // Dessinez le cercle pour représenter la tour
            drawCircleOnTile(colonne, ligne, 8); // Rayon réduit à 8 pour un meilleur ajustement
        }
    }

    /**
     * Updates the UI by redrawing all dynamic components with current game data.
     */
    public void render() {
        // throw new UnsupportedOperationException("Not implemented yet");
        // Exemple : récupération des données dynamiques
        int currentLifeCount = Game.getPlayer().getHP();

        int currentCoinCount = Game.getPlayer().getArgent();
        // String currentLevel = Game.getCurrentLevel();
        // String currentWave = Game.getCurrentWave();

        // Tower[] currentTowers = Game.getShopTowers();

        StdDraw.clear();
        
        drawGameInfoZone();
        drawMapZone(Game.getCurrentLevel().getMap());
        drawEnemies(Game.getActiveEnemies());
        drawTowers(Game.getActiveTower());

        drawPlayerInfoZone(currentLifeCount, currentCoinCount);
        drawShopZone();
        // drawGameInfoZone(currentLevel, currentWave);

        // Montre le nouvel écran
        StdDraw.show();
    }

    ///////////////////////////////////////////////////////////////////
    //////////////////// Gestion des clicks/////////////////////////////
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

            // TODO: A supp uniquement pour debug
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
