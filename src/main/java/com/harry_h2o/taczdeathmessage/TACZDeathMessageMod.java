package com.harry_h2o.taczdeathmessage;

import com.harry_h2o.taczdeathmessage.command.CommandHandler;
import com.harry_h2o.taczdeathmessage.config.ConfigLoader;
import com.harry_h2o.taczdeathmessage.event.GunKillEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("taczdeathmessage")
public class TACZDeathMessageMod {
    public static final String MODID = "taczdeathmessage";

    public TACZDeathMessageMod() {
        ConfigLoader.loadAll();
        MinecraftForge.EVENT_BUS.register(new GunKillEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        CommandHandler.register(event.getDispatcher());
    }
}