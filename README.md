# Gomoku - Jeu de stratégie avec modèle automatisé

## 👥 Participants
- [DESBOIS Ilian](https://github.com/Ilian5)
- [PIERRE Ychnightder](https://github.com/Ychnightder)
- [ABOUELKHEIR Emad](https://github.com/Emadabouelkheir)
- [LOPES Esteban](https://github.com/lopesteban1)

Ce projet est une implémentation du jeu **Gomoku** (Cinq en ligne) en Java. Il propose un mode de jeu où un humain peut jouer contre un modèle automatisé utilisant des règles et stratégies prédéfinies.

## 🧩 Fonctionnalités principales
- 📏 **Grille de jeu configurable** : par défaut en 15x15.
- 🎯 **Détection des alignements gagnants** : horizontal, vertical ou diagonal.
- 🕹️ **Modes de jeu disponibles** :
  - Humain vs Humain
  - Humain vs Modèle Aléatoire
  - Humain vs Modèle Minimax
  - Modèle Minimax vs Minimax
  - ...

## 🤖 Modèle Aléatoire
Le modèle est conçu pour simuler un adversaire jouant aléatoirement. Il **ne s'agit pas d'une intelligence artificielle** : le modèle n'utilise aucunes règles prédéfinies pour choisir ses coups et ne peut pas apprendre ou s'adapter. Il joue simplement aléatoirement.

## 🤖 Modèle Minimax
Le modèle Minimax utilise un algorithme de décision basé sur une recherche arborescente pour simuler un adversaire stratégique. Contrairement au modèle aléatoire, il évalue les coups possibles et sélectionne celui qui maximise ses chances de victoire tout en minimisant celles de l'adversaire. Bien qu'il ne s'agisse pas d'une intelligence artificielle capable d'apprentissage, il suit une logique optimisée pour prendre des décisions plus réfléchies.


## 🚀 Technologies utilisées
- **Langage** : Java
- **Algorithmes** : stratégies simples et logiques pour le modèle automatisé.

## 📜 Instructions pour exécuter le projet
1. Clonez ce dépôt sur votre machine locale :
   ```bash
   git clone https://github.com/Ilian5/GomokuGTP.git
