package com.lypaka.leaguemanager.Listeners;

import com.lypaka.leaguemanager.LeagueManager;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(modid = LeagueManager.MOD_ID)
public class ServerStartedEvent {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        MinecraftForge.EVENT_BUS.register(new ConnectionListener());

        Pixelmon.EVENT_BUS.register(new BattleListeners());

    }

}
