# TowerDefense

## Noms des étudiants
Etudiant : Kiyan Le Gall & Tristan Roy-Salas

## Explications des fonctionnalités réalisées

### Architecture générale

Le projet repose sur une **séparation claire entre le backend et le frontend** :
- **Backend :** Gère la logique du jeu, le chargement des données, les interactions entre les entités (ennemis, tours) et les mécaniques principales. Les classes clés incluent `Game`, `Level`, `Wave`, `Map`, et `Tile`.
- **Frontend :** Responsable de l’affichage graphique et des interactions utilisateur. Cela inclut la classe `UI`, qui affiche les ennemis, tours, informations du joueur, et gère les clics de souris.

---

### Gestion de la carte

1. **Structure de la carte :**
   - La carte est représentée par une instance de la classe `Map`, elle-même composée de plusieurs `Tile`.
   - Chaque `Tile` possède des attributs comme :
     - **Coordonnées** : Ligne (`row`) et colonne (`col`).
     - **Type de case** : Défini par l’enum `TileType` (ex. SPAWN, BASE, ROAD, CONSTRUCTIBLE).
     - **Statut :** Indique si une case est occupée par une tour ou non.

2. **Types de cases (`TileType`) :**
   - Les types de cases sont définis dans une enum pour une gestion claire et centralisée :
     - `SPAWN` : Point d’apparition des ennemis.
     - `BASE` : Base du joueur, cible des ennemis.
     - `ROAD` : Chemin emprunté par les ennemis.
     - `CONSTRUCTIBLE` : Zone où les tours peuvent être placées.
     - `NON_CONSTRUCTIBLE` : Zone inaccessible pour les constructions.
     - `UNKNOWN` : Case inconnue utilisée pour les erreurs.

---

### Gestion des niveaux et vagues

1. **Niveaux (`Level`) :**
   - Chaque niveau est lié à une carte spécifique et contient une liste de vagues.
   - Les données des niveaux sont chargées depuis des fichiers `.lvl`, qui référencent la carte et les vagues associées.

2. **Vagues (`Wave`) :**
   - Les vagues contiennent une liste d’ennemis à faire apparaître, stockés dans des **queues** pour optimiser leur gestion.
   - Les ennemis apparaissent selon un timing précis, défini dans les fichiers `.wve`.

---

### Entités du jeu

1. **Héritage commun (`Warrior`) :**
   - Toutes les entités présentes sur la carte (ennemis, tours) héritent de la classe abstraite `Warrior`, qui regroupe les propriétés et comportements communs :
     - Points de vie (`PV`).
     - Portée d’attaque (`Range`).
     - Mode d’attaque (`ModeAttaque`).
     - Position sur la carte (`Tile`).
     - Calcul de vulnérabilité aux éléments (Feu, Eau, Terre, Air).

2. **Tours :**
   - Les tours sont fixes sur la carte et attaquent les ennemis dans leur portée.
   - Chaque tour a des caractéristiques propres, comme ses points de vie, sa portée, et son mode d’attaque.

3. **Ennemis :**
   - Les ennemis se déplacent sur la carte en suivant le chemin prédéfini.
   - Ils attaquent les tours ou infligent des dégâts à la base du joueur s’ils atteignent la fin du chemin.

4. **Portée et distance :**
   - La portée d’attaque est calculée en utilisant la **distance euclidienne** entre les coordonnées des tuiles.
   - Pour plus de précision dans la sélection des cibles (ex. l’ennemi le plus proche), la distance est recalculée en fonction des **coordonnées en pixels**.

---

### Gestion 

1. **Mises à jour à chaque frame :**
   - Les ennemis avancent, attaquent les tours ou sont éliminés.
   - Les tours attaquent les ennemis à portée ou sont éliminés.

2. **Rendu graphique :**
   - L’interface utilisateur est redessinée à chaque frame pour refléter l’état actuel du jeu, incluant les ennemis, tours, et informations du joueur.

---

### Sauvegarde et chargement

- Le jeu inclut des fonctions pour sauvegarder l’état (argent, niveau, vague) et charger une sauvegarde existante. Les données sont stockées dans le dossier `resources/saved` sous forme de fichiers `.sav`.


## Fonctionnalités supplémentaires
Pour une question **d'équilibrage**, nous avons ajouté une mécanique où, lors d'un changement de niveau, le joueur récupère une partie de l'argent des tours encore présentes sur l'échiquier. Cette récupération est calculée en fonction des points de vie restants de chaque tour.

## Guide pour exécuter le projet


### Prérequis
- **Java 11** (ou une version supérieure) doit être installé.


1. **Compiler le projet :**
   - Compilez tous les fichiers Java :
     ```bash
     javac -d bin src/*.java src/Towers/*.java src/Monsters/*.java src/libraries/*.java
     ```

2. **Exécuter le projet :**
   - Lancez le jeu avec la commande suivante :
     ```bash
     java -cp bin src.App
     ```


## Description de l'interface

Pour l’interface, on a décidé de rester principalement fidèle à celle proposée dans le projet de base, avec quelques ajustements pour simplifier l'expérience utilisateur.

### Changements principaux :
- **Affichage de la boutique des tours :** On a préféré une approche en lignes pour les rendre plus claires et lisibles. 

- **Messages de fin de partie :** On a ajouté des messages qui s’affichent à l’écran pour indiquer si vous avez gagné ou perdu, afin de rendre le tout plus interactif et engageant.

- **Popup pour le Merchant King :** Lorsqu'il arrive à la base du joueur, une fenêtre popup  permet de choisir facilement entre les bonus proposés, ce qui rend cette mécanique plus intuitive.

