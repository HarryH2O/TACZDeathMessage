# TACZDeathMessageMod Usage Guide

## Table of Contents

1. [Features](#features)

2. [Installation Requirements](#installation-requirements)

3. [Installation Steps](#installation-steps)

4. [Configuration Files](#configuration-files)

5. [Command List](#command-list)

6. [FAQ](#faq)

Open-source repository: [https://github.com/HarryH2O/TACZDeathMessageMod](https://github.com/HarryH2O/TACZDeathMessageMod)

---

## Introduction

**TACZDeathMessageMod** is a Minecraft Forge-based mod designed to provide a highly customizable kill message system for servers or single-player games. This mod allows players to customize kill messages in different scenarios and supports world-specific independent configurations.

Note: 
This mod is specifically designed for servers and may not work, or even cause crashes, on the client side!

The purpose of this Mod development is to fix these problems because the hybrid server is modified based on the Bukkit server, resulting in the TACZ Mod's built-in kill prompt not taking effect.

---

## Features

- **Customizable Kill Messages**: Supports custom kill messages for players, animals, and mobs, including normal and headshot kills.
  
- **World-Specific Configuration**: Configure independent kill messages and enable/disable options for different worlds (dimensions).

- **Command Trigger**: Execute custom commands (e.g., broadcasting, rewards) after a kill.

- **Dependency Integration**: Seamless compatibility with the `Timeless and Classics: Zero` mod [This mod requires TACZ MOD to function properly].

---

## Installation Requirements

- **Minecraft Version**: 1.20.1

- **Forge Version**: 47.3.22 or higher

- **Required Dependency**: [Timeless and Classics: Zero](https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero) (Version 1.1.4)

- **Java Version**: JDK 17

---

## Installation Steps

### Client Installation

1. Download the mod file: `taczdeathmessage-xxx.jar`.

2. Place the file into the `.minecraft/mods` folder.

3. Make sure the dependency mod `Timeless and Classics: Zero` is installed. [Note: For the current version (1.0), the dependency requires version 1.1.4 or higher].

### Server Installation

1. Place the mod file into the server's `mods` directory.

2. Add the dependency mod `timeless-and-classics-zero-1.1.4.jar` to the `mods` directory.

3. Restart the server.

---

## Configuration Files

Configuration files are located in the `config/taczdeathmessage/` directory:

### `global_config.json`

```json
{
  "defaultWorldEnabled": true,
  "messages": {
    "player": {
      "normal": "&eKill message &e{killer} &rkilled &e{victim}!",
      "headshot": "&eKill message &e{killer} &rheadshot killed &e{victim}!"
    },
    "animal": {
      "normal": "&e{killer} hunted &e{victim} using {gun}!",
      "headshot": "&e{killer} precisely hunted &e{victim} using {gun}!"
    },
    "headshotSuffix": "（Headshot）"
  }
}
```

### `world_config.json`

```json
{
  "worlds": {
    "minecraft:overworld": {
      "enabled": true,
      "messages": {
        "player": {
          "normal": "&a[PvP] &e{killer} defeated {victim}!"
        }
      }
    }
  }
}
```

---

## Command List

| Command | Permission | Description |
|---------|------------|-------------|
| `/tacz reload` | OP | Reload the configuration file |
| `/tacz toggle <world>` | OP | Enable/Disable kill messages for a specific world |

---

## FAQ

### 1. Mod load error `Missing dependency: timeless-and-classics-zero`

- **Reason**: The required dependency mod is not installed or version mismatch.

- **Solution**: Download and install [Timeless and Classics: Zero 1.1.4](https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero/files/5529117).

### 2. Configuration changes not taking effect

- **Solution**: Execute `/taczdeathmessage reload` to hot reload the configuration or restart the server (recommended).

---

**Note**: Please ensure the dependency mod is correctly installed before using!
