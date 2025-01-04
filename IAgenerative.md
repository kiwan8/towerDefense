## Utilisation des IA génératives

Dans ce projet, nous avons utilisé **ChatGPT** et **Copilot**. Voici les différentes étapes où les IA génératives ont été utilisées :

---

### Structuration du projet

Les IA nous ont aidés à organiser le projet en classes pertinentes pour garantir une conception claire et modulaire. Cette aide a été précieuse pour :
- **Identifier les classes nécessaires** : Définir des entités clés comme `Game`, `Level`, `Wave`, `Warrior`, et leur rôle respectif.
- **Établir des relations hiérarchiques** : Par exemple, faire hériter les tours et les ennemis de `Warrior` pour centraliser les propriétés communes.
- **Définir des responsabilités claires** : Distinguer la logique de jeu (backend) de l’interface utilisateur (frontend).

---

### Aide pour les fonctions complexes

Pour certaines fonctions techniques, les IA ont été consultées pour obtenir des idées de structuration et d’implémentation. Voici deux exemples clés :

1. **Déplacement des ennemis :**
   - La logique de mise à jour des positions des ennemis sur la carte a été inspirée par des suggestions d'IA. Cela inclut la gestion des chemins en pixels, la vitesse de déplacement, et la progression étape par étape.

2. **Sélection des cibles dans `Warrior` :**
   - L’utilisation des **streams** dans Java pour filtrer, trier et sélectionner les cibles à attaquer provient des conseils d’IA. Nous avons adopté cette approche en raison de sa clarté et de son efficacité.

3. **Utilisation des itérateurs dans `Game` :**

   - Pour gérer les listes dynamiques d’ennemis et de tours, les itérateurs ont été suggérés comme une manière optimale de parcourir et modifier ces listes en temps réel.

4. **Sauvegarde et chargement :**

   - La section de sauvegarde du jeu a été partiellement générée avec l’aide des IA, notamment pour gérer les objets sérialisables (Serializable) et les opérations d’entrée/sortie.

---

### Génération de commentaires pour le binôme

Pour améliorer la compréhension mutuelle entre les membres du binôme, nous avons utilisé les IA pour :
- **Générer des commentaires dans le code** : Explications détaillées des méthodes et des choix de conception.
- **Assurer la lisibilité** : Ajouter des annotations claires sur les responsabilités des classes et des fonctions.

---

### Création des docstrings

Nous avons utilisé les IA pour générer des **docstrings** dans toutes les classes et méthodes. Ces descriptions facilitent la compréhension globale du projet et respectent les standards JavaDoc.


---


### Génération des constructeurs

Pour les classes concrètes d’ennemis et de tours, nous avons utilisé les IA pour générer la majorité des constructeurs Basés sur les tableaux d’attributs définis dans la documentation du projet.

