package com.harry_h2o.taczdeathmessage.command;

import com.harry_h2o.taczdeathmessage.config.ConfigLoader;
import com.harry_h2o.taczdeathmessage.config.WorldConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandHandler {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(buildCommand());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> buildCommand() {
        return Commands.literal("taczdeathmessage")
                .then(Commands.literal("reload")
                        .executes(context -> {
                            ConfigLoader.loadAll();
                            context.getSource().sendSuccess(() -> Component.literal("配置已重载"), false);
                            return 1;
                        }))
                .then(Commands.literal("world")
                        .then(Commands.argument("world", StringArgumentType.string())
                                .suggests(new WorldNameSuggestionProvider())
                                .then(Commands.literal("enable")
                                        .executes(context -> setWorldEnabled(context, true)))
                                .then(Commands.literal("disable")
                                        .executes(context -> setWorldEnabled(context, false)))
                                .then(Commands.literal("animals")
                                        .then(Commands.literal("enable")
                                                .executes(context -> setWorldAnimals(context, true)))
                                        .then(Commands.literal("disable")
                                                .executes(context -> setWorldAnimals(context, false))))
                                .then(Commands.literal("monsters")
                                        .then(Commands.literal("enable")
                                                .executes(context -> setWorldMonsters(context, true)))
                                        .then(Commands.literal("disable")
                                                .executes(context -> setWorldMonsters(context, false)))))
                        .then(Commands.literal("global")
                                .then(Commands.literal("animals")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(context -> {
                                                    ConfigLoader.globalConfig.enableAnimalMessages = BoolArgumentType.getBool(context, "enabled");
                                                    ConfigLoader.saveGlobalConfig();
                                                    context.getSource().sendSuccess(() -> Component.literal("全局动物消息已 " + (ConfigLoader.globalConfig.enableAnimalMessages ? "启用" : "禁用")), false);
                                                    return 1;
                                                })))
                                .then(Commands.literal("monsters")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(context -> {
                                                    ConfigLoader.globalConfig.enableMonsterMessages = BoolArgumentType.getBool(context, "enabled");
                                                    ConfigLoader.saveGlobalConfig();
                                                    context.getSource().sendSuccess(() -> Component.literal("全局怪物消息已 " + (ConfigLoader.globalConfig.enableMonsterMessages ? "启用" : "禁用")), false);
                                                    return 1;
                                                })))));
    }

    private static int setWorldEnabled(CommandContext<CommandSourceStack> context, boolean enabled) {
        String worldName = StringArgumentType.getString(context, "world");
        WorldConfig.WorldData data = ConfigLoader.worldConfig.worlds.computeIfAbsent(worldName, k -> new WorldConfig.WorldData());
        data.enabled = enabled;
        ConfigLoader.saveWorldConfig();
        context.getSource().sendSuccess(() -> Component.literal("世界 " + worldName + " 的击杀消息已 " + (enabled ? "启用" : "禁用")), false);
        return 1;
    }

    private static int setWorldAnimals(CommandContext<CommandSourceStack> context, boolean enabled) {
        String worldName = StringArgumentType.getString(context, "world");
        WorldConfig.WorldData data = ConfigLoader.worldConfig.worlds.computeIfAbsent(worldName, k -> new WorldConfig.WorldData());
        data.enableAnimals = enabled;
        ConfigLoader.saveWorldConfig();
        context.getSource().sendSuccess(() -> Component.literal("世界 " + worldName + " 的动物消息已 " + (enabled ? "启用" : "禁用")), false);
        return 1;
    }

    private static int setWorldMonsters(CommandContext<CommandSourceStack> context, boolean enabled) {
        String worldName = StringArgumentType.getString(context, "world");
        WorldConfig.WorldData data = ConfigLoader.worldConfig.worlds.computeIfAbsent(worldName, k -> new WorldConfig.WorldData());
        data.enableMonsters = enabled;
        ConfigLoader.saveWorldConfig();
        context.getSource().sendSuccess(() -> Component.literal("世界 " + worldName + " 的怪物消息已 " + (enabled ? "启用" : "禁用")), false);
        return 1;
    }
}