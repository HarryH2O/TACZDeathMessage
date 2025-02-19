package com.harry_h2o.taczdeathmessage.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import java.util.concurrent.CompletableFuture;

public class WorldNameSuggestionProvider implements SuggestionProvider<CommandSourceStack> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            CommandContext<CommandSourceStack> context, SuggestionsBuilder builder
    ) {
        MinecraftServer server = context.getSource().getServer();
        server.getAllLevels().forEach(level -> {
            String worldName = level.dimension().location().toString();
            builder.suggest(worldName);
        });
        return builder.buildFuture();
    }
}