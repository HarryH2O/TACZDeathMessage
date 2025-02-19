package com.harry_h2o.taczdeathmessage.event;

import com.harry_h2o.taczdeathmessage.config.ConfigLoader;
import com.harry_h2o.taczdeathmessage.config.GlobalConfig;
import com.harry_h2o.taczdeathmessage.config.WorldConfig;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GunKillEventHandler {

    @SubscribeEvent
    public void onEntityKilledByGun(EntityKillByGunEvent event) {
        if (event.getLogicalSide().isClient()) return;

        LivingEntity attacker = event.getAttacker();
        LivingEntity killed = event.getKilledEntity();
        if (!(attacker instanceof ServerPlayer killer)) return;

        ServerLevel world = null;
        if (killed != null) {
            world = (ServerLevel) killed.level();
        }
        String worldName = null;
        if (world != null) {
            worldName = world.dimension().location().toString();
        }

        // 检查世界是否启用
        WorldConfig.WorldData worldData = ConfigLoader.worldConfig.worlds.get(worldName);
        boolean worldEnabled = (worldData != null && worldData.enabled != null) ?
                worldData.enabled : ConfigLoader.globalConfig.defaultWorldEnabled;
        if (!worldEnabled) return;

        GlobalConfig.Messages messages = mergeMessages(worldData);

        if (killed instanceof ServerPlayer victim) {
            handlePlayerKill(killer, victim, worldName, messages, event.isHeadShot(), event);
        } else if (killed instanceof Animal) {
            handleEntityKill(killer, killed, worldName, messages.animal, event.isHeadShot(), event);
        } else if (killed instanceof Monster) {
            handleEntityKill(killer, killed, worldName, messages.monster, event.isHeadShot(), event);
        }
    }

    private GlobalConfig.Messages mergeMessages(WorldConfig.WorldData worldData) {
        GlobalConfig.Messages merged = new GlobalConfig.Messages();
        if (worldData == null || worldData.messages == null) return ConfigLoader.globalConfig.messages;

        merged.player.normal = worldData.messages.player.normal != null ?
                worldData.messages.player.normal : ConfigLoader.globalConfig.messages.player.normal;
        merged.player.headshot = worldData.messages.player.headshot != null ?
                worldData.messages.player.headshot : ConfigLoader.globalConfig.messages.player.headshot;

        merged.animal.normal = worldData.messages.animal.normal != null ?
                worldData.messages.animal.normal : ConfigLoader.globalConfig.messages.animal.normal;
        merged.animal.headshot = worldData.messages.animal.headshot != null ?
                worldData.messages.animal.headshot : ConfigLoader.globalConfig.messages.animal.headshot;

        merged.monster.normal = worldData.messages.monster.normal != null ?
                worldData.messages.monster.normal : ConfigLoader.globalConfig.messages.monster.normal;
        merged.monster.headshot = worldData.messages.monster.headshot != null ?
                worldData.messages.monster.headshot : ConfigLoader.globalConfig.messages.monster.headshot;

        merged.headshotSuffix = worldData.messages.headshotSuffix != null ?
                worldData.messages.headshotSuffix : ConfigLoader.globalConfig.messages.headshotSuffix;

        return merged;
    }

    private void handlePlayerKill(ServerPlayer killer, ServerPlayer victim, String worldName,
                                  GlobalConfig.Messages messages, boolean isHeadShot, EntityKillByGunEvent event) {
        String template = isHeadShot ? messages.player.headshot : messages.player.normal;
        String message = template
                .replace("{killer}", killer.getScoreboardName())
                .replace("{victim}", victim.getScoreboardName())
                .replace("{gun}", event.getGunId().toString())
                .replace("{headshot}", isHeadShot ? messages.headshotSuffix : "");
        sendWorldMessage(killer, worldName, message);
        if (ConfigLoader.globalConfig.messages.postCommands != null) {
            for (GlobalConfig.CommandEntry entry : ConfigLoader.globalConfig.messages.postCommands) {
                String processedCmd = entry.command
                        .replace("%player1%", killer.getScoreboardName())
                        .replace("%player2%", victim.getScoreboardName())
                        .replace("%gun%", event.getGunId().toString());

                executeCommand(killer, entry.identity, processedCmd);
            }
        }
    }

    private void executeCommand(ServerPlayer player, String identity, String command) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        switch (identity.toLowerCase()) {
            case "op":
                boolean wasOp = server.getPlayerList().isOp(player.getGameProfile());
                try {
                    server.getPlayerList().op(player.getGameProfile());
                    server.getCommands().performPrefixedCommand(
                            player.createCommandSourceStack().withPermission(4), command);
                } finally {
                    if (!wasOp) server.getPlayerList().deop(player.getGameProfile());
                }
                break;
            case "console":
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withPermission(4), command);
                break;
            default:
                server.getCommands().performPrefixedCommand(
                        player.createCommandSourceStack(), command);
        }
    }

    private void handleEntityKill(ServerPlayer killer, LivingEntity entity, String worldName,
                                  GlobalConfig.EntityMessages template, boolean isHeadShot, EntityKillByGunEvent event) {
        String message = (isHeadShot && !template.headshot.isEmpty()) ?
                template.headshot : template.normal;
        message = message
                .replace("{killer}", killer.getScoreboardName())
                .replace("{victim}", entity.getType().getDescription().getString())
                .replace("{gun}", event.getGunId().toString())
                .replace("{headshot}", isHeadShot ? ConfigLoader.globalConfig.messages.headshotSuffix : "");
        sendWorldMessage(killer, worldName, message);
    }

    private void sendWorldMessage(ServerPlayer killer, String worldName, String message) {
        MinecraftServer server = killer.getServer();
        if (server == null) return;
        ResourceKey<Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(worldName));
        ServerLevel world = server.getLevel(dimensionKey);
        if (world != null) {
            // 将颜色代码转换为 Component
            Component formattedMessage = Component.literal(message.replace("&", "§"));
            world.players().forEach(player ->
                    player.sendSystemMessage(formattedMessage)
            );
        }
    }
}