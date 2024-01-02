# Solitaire Game

Solitaire, also known as Klondike, is a popular card game for one player with a rich history and simple rules.

## Installation

1. Clone the repository to your local environment:
   ```bash
   git clone git@github.com:marekdolezel/klondike-solitaire-game.git
   ```
2. Navigate to the project folder:
   ```bash
   cd klondike-solitaire-game
   ```

## Build

The project is built using the Apache Ant tool. To build, run the following command in the project folder:

```bash
ant compile
```
> **__Note__**: In the event of compilation failure, ensure you have a JDK version earlier than 11, necessary for this program.
> You can list available JDKs on your system using the command `/usr/libexec/java_home -V`. 
> To select an appropriate JDK version, set the JAVA_HOME environment variable. Use the command `export JAVA_HOME=[Your JDK Path]`,
> replacing '[Your JDK Path]' with the path to your chosen JDK, which should be a version prior to JDK 11.
## Execution

After building, you can run the game with the following command:

```bash
ant run
```

## Key Features

- Simple JavaFX user interface.
- Classic Solitaire (Klondike) game.
- Ability to save and load a game.

## Licensing

This project is distributed under [chosen license].

## Authors

- Marek Doležel
- Radim Červinka

---

Created as part of the IJA course, VUT FIT.