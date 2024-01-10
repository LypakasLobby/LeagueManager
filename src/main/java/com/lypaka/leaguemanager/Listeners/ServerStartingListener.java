package com.lypaka.leaguemanager.Listeners;

import com.lypaka.leaguemanager.LeagueManager;
import com.lypaka.leaguemanager.Leagues.LeagueHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Mod.EventBusSubscriber(modid = LeagueManager.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) throws ObjectMappingException {

        LeagueHandler.loadGymsAndLeagues();

    }

}
