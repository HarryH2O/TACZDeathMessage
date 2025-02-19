

# TACZDeathMessageMod 使用方法

## 目录
1. [简介]
2. [功能特性](#功能特性)  
3. [安装要求](#安装要求)  
4. [安装步骤](#安装步骤)  
5. [配置文件](#配置文件)  
6. [命令列表](#命令列表)  
7. [常见问题](#常见问题)   

---

## 简介
**TACZDeathMessageMod** 是一款基于 Minecraft Forge 的模组，旨在为服务器或单人游戏提供高度可定制的击杀消息系统。通过该模组，玩家可以自定义不同场景下的击杀提示，并支持多世界独立配置。

[注意:该mod专为服务端制作,在客户端上可能不起作用甚至报错崩溃!]
---

## 功能特性
- **击杀消息定制**：支持玩家、动物、怪物击杀的普通和爆头消息自定义。
- **多世界配置**：为不同世界（维度）设置独立的启用状态和消息模板。
- **命令触发**：击杀后可执行自定义命令（如广播、奖励等）。
- **依赖集成**：与 `Timeless and Classics: Zero` 模组无缝兼容[本mod实际需要依赖TACZ MOD运行]。

---

## 安装要求
- **Minecraft 版本**：1.20.1  
- **Forge 版本**：47.3.22 或更高  
- **必需依赖**：[Timeless and Classics: Zero](https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero)（版本 1.1.4）  
- **Java 版本**：JDK 17  

---

## 安装步骤
### 客户端安装
1. 下载模组文件：`taczdeathmessage-xxx.jar`  
2. 将文件放入 `.minecraft/mods` 文件夹。  
3. 确保已安装依赖模组 `Timeless and Classics: Zero`。[注意:在当前版本下(1.0),依赖需要1.1.4版本或更高]

### 服务器安装
1. 将模组文件放入服务器 `mods` 目录。  
2. 在 `mods` 目录中添加依赖模组 `timeless-and-classics-zero-1.1.4.jar`。  
3. 重启服务器。

---

## 配置文件
配置文件位于 `config/taczdeathmessage/` 目录下：

### `global_config.json`
```json
{
  "defaultWorldEnabled": true,
  "messages": {
    "player": {
      "normal": "&e击杀提示 &e{killer} &r击杀了 &e{victim}!",
      "headshot": "&e击杀提示 &e{killer} &r爆头击杀了 &e{victim}!"
    },
    "animal": {
      "normal": "&e{killer} 用 {gun} 猎杀了 &e{victim}!",
      "headshot": "&e{killer} 用 {gun} 精准猎杀了 &e{victim}!"
    },
    "headshotSuffix": "（爆头）"
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
          "normal": "&a[PvP] &e{killer} 击败了 {victim}!"
        }
      }
    }
  }
}
```

---

## 命令列表
| 命令 | 权限 | 描述 |  
|------|------|------|  
| `/tacz reload` | OP | 重载配置文件 |  
| `/tacz toggle <world>` | OP | 启用/禁用某世界的击杀消息 |  

---

## 常见问题
### 1. 模组加载时报错 `Missing dependency: timeless-and-classics-zero`
- **原因**：未安装依赖模组或版本不匹配。  
- **解决**：下载并安装 [Timeless and Classics: Zero 1.1.4](https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero/files/5529117)。  

### 2. 配置修改后未生效
- **解决**：执行 `/taczdeathmessage reload` 进行热重载配置，或重启服务器(推荐)。
- 
---

**注意**：使用前请确保依赖模组已正确安装！
