# Application de Gestion de Comptes Bancaires

## Description

Cette application permet de gérer des comptes bancaires appartenant à différents clients. Chaque compte peut subir plusieurs opérations de type DEBIT ou CREDIT. Il existe deux types de comptes : Comptes courants et Comptes épargnes. L'application est construite en utilisant Spring Boot pour le backend et Angular pour le frontend. Elle comprend également une couche de sécurité basée sur Spring Security et JSON Web Token (JWT).

## Fonctionnalités

- Gestion des clients :
  - Saisie, ajout, suppression, édition et recherche des clients.
- Gestion des comptes :
  - Ajout de comptes, recherche et administration des comptes.
  - Deux types de comptes : Comptes courants et Comptes épargnes.
- Gestion des opérations :
  - Enregistrement des opérations de type DEBIT et CREDIT pour chaque compte.
- Authentification et sécurité :
  - Gestion des utilisateurs et des mots de passe.
  - Authentification basée sur Spring Security et JWT.
- Enregistrement des actions :
  - Pour chaque client, compte et opération enregistrée, enregistrement de l'identifiant de l'utilisateur authentifié qui a effectué l'opération.
- Tableau de bord :
  - Utilisation de ChartJS pour montrer des graphiques et des statistiques utiles pour les prises de décision.
- Autres fonctionnalités supplémentaires à ajouter.

## Prérequis

- Java 17 ou plus
- Spring Boot 3.x
- Node.js et npm
- Angular CLI

## Installation

### Backend

1. Cloner le dépôt :

```bash
git clone https://github.com/votre-utilisateur/votre-repo.git
cd votre-repo
