package com.lypaka.leaguemanager.Commands;

import com.lypaka.leaguemanager.LeagueManager;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = LeagueManager.MOD_ID)
public class LeagueManagerCommand {

    public static final List<String> ALIASES = Arrays.asList("leaguemanager", "leagues", "lmanager");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new BadgeCaseCommand(event.getDispatcher());
        new E4InfoCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
