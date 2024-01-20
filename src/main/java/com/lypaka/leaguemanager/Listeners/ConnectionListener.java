package com.lypaka.leaguemanager.Listeners;

import com.lypaka.leaguemanager.Accounts.AccountHandler;
import com.lypaka.leaguemanager.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;
import java.util.UUID;

public class ConnectionListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        AccountHandler.createAccount(player);

        if (ConfigGetters.e4PlayerUUIDs.containsKey(player.getUniqueID().toString())) {

            String name = ConfigGetters.e4PlayerUUIDs.get(player.getUniqueID().toString());
            String message = ConfigGetters.e4Broadcast
                    .replace("%player%", player.getName().getString())
                    .replace("%e4Member%", name);
            for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                entry.getValue().sendMessage(FancyText.getFormattedText(message), entry.getKey());

            }

        } else if (ConfigGetters.gymLeaderPlayerUUIDs.containsKey(player.getUniqueID().toString())) {

            String name = ConfigGetters.gymLeaderPlayerUUIDs.get(player.getUniqueID().toString());
            String message = ConfigGetters.gymLeaderBroadcast
                    .replace("%player%", player.getName().getString())
                    .replace("%gymName%", name);
            for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                entry.getValue().sendMessage(FancyText.getFormattedText(message), entry.getKey());

            }

        }

    }

    @SubscribeEvent
    public void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        AccountHandler.removeAccount(player);

    }

}
