package src;

/**
 * Contains all exceptions related to the game.
 */
public class GameExceptions {

    /**
     * Base class for all game-related exceptions.
     */
    public static class GameException extends Exception {
        public GameException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when the map has an invalid path.
     */
    public static class InvalidMapPathException extends GameException {
        public InvalidMapPathException(String message) {
            super(message);
        }
    }

    /**
     * Thrown for generic map validation errors.
     */
    public static class MapException extends GameException {
        public MapException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when no enemy spawn is found on the map.
     */
    public static class NoEnemySpawnException extends MapException {
        public NoEnemySpawnException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when no player base is found on the map.
     */
    public static class NoPlayerBaseException extends MapException {
        public NoPlayerBaseException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when multiple player bases are found on the map.
     */
    public static class MultiplePlayerBaseException extends MapException {
        public MultiplePlayerBaseException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when multiple enemy spawns are found on the map.
     */
    public static class MultipleEnemySpawnException extends MapException {
        public MultipleEnemySpawnException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an enemy is unknown.
     */
    public static class UnknownEnemyException extends GameException {
        public UnknownEnemyException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when the wave file is invalid.
     */
    public static class InvalidWaveFileException extends GameException {
        public InvalidWaveFileException(String message) {
            super(message);
        }
    }
}
