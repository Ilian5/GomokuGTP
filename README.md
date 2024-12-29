# Gomoku - Jeu de stratégie avec IA Minimax

## 📝 Description

Ce projet implémente le **jeu Gomoku** avec une interface en ligne de commande et un **bot IA** utilisant l'algorithme **Minimax**. Le jeu permet à un joueur humain de jouer contre une IA ou contre un autre joueur humain. 

### Fonctionnalités principales :
- **Jeu à 2 joueurs** : Le joueur peut jouer contre un autre joueur humain en mode local.
- **IA avec Minimax** : L'IA utilise un algorithme Minimax pour essayer de maximiser ses chances de gagner tout en bloquant les coups de l'adversaire.
- **Commandes disponibles** :
  - `boardsize <taille>` : Définit la taille du plateau de jeu (ex. : `boardsize 15`).
  - `play <couleur> <coordonnées>` : Joue un coup pour la couleur spécifiée (ex. : `play B D5`).
  - `clear_board` : Réinitialise le plateau de jeu.
  - `genmove <couleur>` : L'IA génère un coup pour la couleur spécifiée.
  - `showboard` : Affiche l'état actuel du plateau.
  - `set_player <couleur> <type> [profondeur]` : Change un joueur en un certain type (ex. : `set_player black minimax 5`)
  - `quit` : Met fin à la session de jeu.

---

## ⚠️ Problème Actuel

Le **bot Minimax** ne fonctionne pas comme prévu. Il choisit des coups suboptimaux en privilégiant parfois des blocages plutôt que de chercher à gagner. Ce comportement est probablement dû à un problème dans l'évaluation des coups ou l'implémentation de l'algorithme de Minimax.

---

## 💻 État actuel du projet

- **Logique de jeu** : Le jeu de base fonctionne correctement, avec une gestion fluide des coups et de l'affichage du plateau.
- **IA** : L'IA utilise Minimax mais nécessite des améliorations pour prendre de meilleures décisions stratégiques.
- **Affichage** : Le plateau est bien affiché avec une notation des cases à l'aide de lettres (pour les colonnes) et de chiffres (pour les lignes).

---

## 🚧 Prochaines étapes

1. **Réparer l'IA Minimax** : Améliorer l'algorithme de Minimax pour qu'il fasse de meilleurs choix de coups.
2. **Optimisation des performances** : Tester et ajuster l'IA pour s'assurer qu'elle prend les meilleures décisions rapidement.
3. **Amélioration de l'interface** : Ajouter des fonctionnalités pour rendre le jeu plus interactif et améliorer l'expérience utilisateur.

---

## 👥 Participants

- [DESBOIS Ilian](https://github.com/Ilian5)
- [PIERRE Ychnightder](https://github.com/Ychnightder)
- [ABOUELKHEIR Emad](https://github.com/Emadabouelkheir)
- [LOPES Esteban](https://github.com/lopesteban1)

---

## 🔧 Installation

1. Clonez ce repository :
   ```bash
   git clone https://github.com/votre-utilisateur/gomoku.git
