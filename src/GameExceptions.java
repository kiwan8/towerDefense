package src;

/**
 * Contient toutes les exceptions liées au jeu.
 */
public class GameExceptions {
    
    /**
     * Classe de base pour toutes les exceptions liées au jeu.
     */
    public static class GameException extends Exception {
        public GameException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsque la carte a un chemin invalide.
     */
    public static class InvalidMapPathException extends GameException {
        public InvalidMapPathException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée pour les erreurs de validation de carte génériques.
     */
    public static class MapException extends GameException {
        public MapException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsqu'aucun point d'apparition d'ennemi n'est trouvé sur la carte.
     */
    public static class NoEnemySpawnException extends MapException {
        public NoEnemySpawnException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception levée lorsqu'aucune base de joueur n'est trouvée sur la carte.
     */
    public static class NoPlayerBaseException extends MapException {
        public NoPlayerBaseException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsque plusieurs bases de joueur sont trouvées sur la carte.
     */
    public static class MultiplePlayerBaseException extends MapException {
        public MultiplePlayerBaseException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsque plusieurs points d'apparition d'ennemis sont trouvés sur la carte.
     */
    public static class MultipleEnemySpawnException extends MapException {
        public MultipleEnemySpawnException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsqu'un ennemi est inconnu.
     */
    public static class UnknownEnemyException extends GameException {
        public UnknownEnemyException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsque le fichier de vague est invalide.
     */
    public static class InvalidWaveFileException extends GameException {
        public InvalidWaveFileException(String message) {
            super(message);
        }
    }

    /**
     * Exception levée lorsqu'il n'y a pas assez d'argent.
     */
    public static class NotEnoughMoneyException extends Exception {
        public NotEnoughMoneyException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception levée lorsqu'une tuile est occupée.
     */
    public static class TileOccupiedException extends Exception {
        public TileOccupiedException(String message) {
            super(message);
        }
    }
    
}