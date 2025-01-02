package src;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
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

    public void updateMap(Map newMap) {
        this.map = newMap;
        render(); // Redessine l'interface avec la nouvelle carte
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

        String level = Game.getcptLevel() + 1 + "/" + Game.getLevels().size();
        Level LevelCourant = Game.getLevels().get(Game.getcptLevel());

        int nbTotalWavesduLevel = LevelCourant.getWaves().size();
        String wave = Game.getcptWave() + 1 + "/" + nbTotalWavesduLevel;

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

    private static List<TowerCard> towerCards = new ArrayList<>(); // Liste des cartes de tours

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

        for (int i = 0; i < towers.length; i++) { // Dessiner chaque carte de tour
            double yPosition = startY - i * cardHeight;
            TowerCard carte = new TowerCard(centerX, yPosition, cardWidth, cardHeight, towers[i]);
            carte.draw();
            towerCards.add(carte); // Ajouter la carte à la liste
        }
    }

    private class TowerCard {
        private double x;
        private double y;
        private double width;
        private double height;
        private Tower tower;

        public TowerCard(double x, double y, double width, double height, Tower tower) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.tower = tower;
        }

        public void draw() {
            // Dessine le fond de la carte
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(x, y, width / 2, height / 2);

            // Dessine la bordure
            StdDraw.setPenColor(tower.getColor());
            StdDraw.rectangle(x, y, width / 2, height / 2);

            // Dessine le symbole de la tour
            StdDraw.setPenColor(tower.getColor());
            StdDraw.filledCircle(x - width / 2 + height / 2, y, height / 3);

            // Affiche les détails de la tour
            Font font = new Font("Arial", Font.PLAIN, 10);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.BLACK);

            StdDraw.textLeft(x - width / 2 + height, y,
                    tower.getClass().getSimpleName() + " | PV: " + tower.getPV() +
                            " | ATK: " + tower.getATK() +
                            " | SPD: " + tower.getATKSpeed() +
                            " | PO: " + tower.getRange() +
                            " | Cost: " + tower.getCout());
        }

        public boolean contains(double x, double y) {
            return x >= this.x - this.width / 2 && x <= this.x + this.width / 2 &&
                    y >= this.y - this.height / 2 && y <= this.y + this.height / 2;
        }

        public Tower getTower() {
            return this.tower;
        }
    }

    public static Tower towerAtXY(double x, double y) { // Trouver la tour correspondant à une position XY
        for (TowerCard carte : towerCards) {
            if (carte.contains(x, y)) {
                return carte.getTower();
            }
        }
        return null; // Retourne null si aucune carte ne correspond
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
     * Dessine les tours sur la carte.
     *
     * @param towers La liste des tours à dessiner.
     */
    private void drawTowers(List<Tower> towers) {
        for (Tower tower : towers) {
            int col = tower.getPosition().getCol();
            int row = tower.getPosition().getRow();

            // Définir la couleur spécifique à la tour
            StdDraw.setPenColor(tower.getColor());

            // Dessiner le cercle représentant la tour
            drawCircleOnTile(row, col, 8); // Ajuster le rayon selon la taille des cellules

            // Dessiner la barre de vie au-dessus de la tour
            drawHealthBar(tower, getTileCenterX(col), getTileCenterY(row) + 10);
        }
    }

    private double getTileCenterX(int col) {
        double centerX = 350;
        double halfLength = 350;
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());
        return centerX - halfLength + col * cellSize + cellSize / 2;
    }

    private double getTileCenterY(int row) {
        double centerY = 350;
        double halfLength = 350;
        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());
        return centerY + halfLength - row * cellSize - cellSize / 2;
    }

    /**
     * Dessine une barre de vie pour une tour.
     *
     * @param tower La tour pour laquelle dessiner la barre de vie.
     * @param x     Coordonnée X de la tour.
     * @param y     Coordonnée Y au-dessus de la tour.
     */
    private void drawHealthBar(Tower tower, double x, double y) {
        double healthPercentage = (double) tower.getPV() / tower.getMaxPV();
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

    /**
     * Updates the UI by redrawing all dynamic components with current game data.
     */
    public void render() {
        StdDraw.clear();

        drawGameInfoZone();
        drawMapZone(Game.getCurrentLevel().getMap());
        drawEnemies(Game.getActiveEnemies());
        drawTowers(Game.getActiveTower());
        drawPlayerInfoZone(Game.getPlayer().getHP(), Game.getPlayer().getArgent());
        drawShopZone();

        StdDraw.show();
    }

    ///////////////////////////////////////////////////////////////////
    //////////////////// Gestion des clicks/////////////////////////////
    ///////////////////////////////////////////////////////////////////

    private static Tower selecTour = new Archer(null); // Tour sélectionnée par défaut

    /**
     * Vérifie si un clic est sur une case constructible et y place une tour.
     */
    public void handleClick(double mouseX, double mouseY) {
        double centerX = 350;
        double centerY = 350;
        double halfLength = 350;

        double cellSize = Math.min(2 * halfLength / map.getRows(), 2 * halfLength / map.getCols());

        // Détermination des indices de la case cliquée
        int col = (int) ((mouseX - (centerX - halfLength)) / cellSize);
        int row = (int) ((centerY + halfLength - mouseY) / cellSize);

        if (row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols()) {
            Tile clickedTile = map.getTile(row, col);

            try {
                // Vérification des conditions
                if (Game.getPlayer().getArgent() < selecTour.getCout()) {
                    throw new GameExceptions.NotEnoughMoneyException("Not enough money to build this tower!");
                }
                if (clickedTile.isOccupiedByTower()) {
                    throw new GameExceptions.TileOccupiedException(
                            "Map tile already built ! Cannot place new Tower !");
                }
                if (clickedTile.isConstructible()) {
                    // Construire une tour
                    Tower newTower = instanceTour(clickedTile);
                    Game.getActiveTower().add(newTower);

                    // Mise à jour du solde et état de la case
                    Game.getPlayer().setArgent(Game.getPlayer().getArgent() - selecTour.getCout());
                    clickedTile.setOccupiedByTower(true);

                }

            } catch (GameExceptions.NotEnoughMoneyException e) {
                System.out.println(e.getMessage());
            } catch (GameExceptions.TileOccupiedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Vérifier si le clic est sur une carte de tour
        Tower tower = towerAtXY(mouseX, mouseY);
        if (tower != null) {
            selecTour = tower;
        }
    }

    private Tower instanceTour(Tile clickedTile) { // Instancier une tour en fonction de la tour sélectionnée
        switch (selecTour.getClass().getSimpleName()) {
            case "Archer":
                return new Archer(clickedTile);

            case "WindCaster":
                return new WindCaster(clickedTile);

            case "WaterCaster":
                return new WaterCaster(clickedTile);

            case "EarthCaster":
                return new EarthCaster(clickedTile);

            case "FireCaster":
                return new FireCaster(clickedTile);

            case "IceCaster":
                return new IceCaster(clickedTile);

            case "PoisonCaster":
                return new PoisonCaster(clickedTile);

            case "GoldDigger":
                return new GoldDigger(clickedTile);

            case "Railgun":
                return new Railgun(clickedTile);

            default:
                return new Archer(clickedTile);
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
