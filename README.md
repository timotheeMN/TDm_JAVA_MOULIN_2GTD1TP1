﻿# TDm_JAVA_MOULIN_2GTD1TP1

## Overview
My game features a hero who can move around a snowy world, run and interact with an object, in this case lightning. The game includes features such as dynamic weather - it snows randomly, so the hero loses stamina much more quickly - stamina management and an endgame objective (to get out of the game).

## Features
- **Dynamic Weather:** Includes a snowstorm effect that doubles stamina consumption when running.
- **Stamina Management:** The hero has limited stamina that regenerates over time or by collecting lightnings.
- **Collision Detection:** Interactions with solid objects and collectible items like lightnings.
- **Victory Screen:** The game ends when the hero reaches an exit point, displaying a custom overlay.

## How to Play
1. Use the arrow keys to move the hero.
2. Hold the Shift key to run (consumes stamina).
3. Collect lightning icons to restore stamina.
4. Reach the exit point (portal) to win the game.

## Code Structure
### Main Components
- **Main:** Initializes the game window, engines, and starts the game loop.
- **DynamicSprite:** Represents the hero with movement and animation logic.
- **Playground:** Handles the game map, including solid objects and interactive items.
- **WeatherEngine:** Controls the snowstorm effect.
- **PhysicEngine:** Manages physics, including movement and collisions.
- **RenderEngine:** Handles rendering of game objects and effects.
- **VictoryPanel:** Displays the victory message overlay.

### Key Files
- `Main.java`: Entry point for the game.
- `DynamicSprite.java`: Contains logic for the hero's behavior and stamina.
- `Playground.java`: Loads the map and handles interactions with objects.
- `WeatherEngine.java`: Introduces dynamic weather.
- `PhysicEngine.java`: Handles movement and collision detection.
- `RenderEngine.java`: Manages all rendering.
- `VictoryPanel.java`: Displays the victory screen.
