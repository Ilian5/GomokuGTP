# Gomoku - Jeu de stratégie avec IA Minimax

## NOTE 15

## CORRECTION
Il faut respecter les consignes : je ne vois pas les membres du projet listés ici, je ne vois pas diagramme d'architecture !

Les tests unitaires sont très insuffisnts.

Il manques information sur le nom des players "human" et "Human" ne marchent pas. J'ai du lire le code pour voir qu'il faut mettre "humain" !

Je ne vois pas de mode partie ! C'est fastidieux.

Les coordonnées ne sont pas conformes (le goban est tourné).

J'ai joué contre minimax qui semble fontionner correctement.


## 📝 Description

Ce projet implémente le **jeu Gomoku** avec une interface en ligne de commande et un **bot IA** utilisant l'algorithme **Minimax**. Le jeu permet à un joueur humain de jouer contre une IA ou contre un autre joueur humain.

### Fonctionnalités principales :
- **Jeu à 2 joueurs** : Le joueur peut jouer contre un autre joueur humain en mode local.
- **IA avec Minimax** : L'IA utilise un algorithme Minimax pour maximiser ses chances de gagner tout en bloquant les coups de l'adversaire.
- **Commandes disponibles** :
  - `boardsize <taille>` : Définit la taille du plateau de jeu (ex. : `boardsize 15`).
  - `play <couleur> <coordonnées>` : Joue un coup pour la couleur spécifiée (ex. : `play B D5`).
  - `clear_board` : Réinitialise le plateau de jeu.
  - `genmove <couleur>` : L'IA génère un coup pour la couleur spécifiée.
  - `showboard` : Affiche l'état actuel du plateau.
  - `set_player <couleur> <type> [profondeur]` : Change un joueur en un certain type (ex. : `set_player black minimax 5`).
  - `quit` : Met fin à la session de jeu.

---

## 💻 État actuel du projet

- **Logique de jeu** : Le jeu de base fonctionne parfaitement, avec une gestion fluide des coups et de l'affichage du plateau.
- **IA** : L'IA utilise un algorithme Minimax capable de prioriser les alignements tout en bloquant les adversaires efficacement.
- **Affichage** : Le plateau est bien affiché avec une notation des cases à l'aide de lettres (pour les colonnes) et de chiffres (pour les lignes).

---

## 🚀 Fonctionnement

### Installation

1. Clonez ce repository :
   ```bash
   git clone https://github.com/Ilian5/GomokuGTP.git
