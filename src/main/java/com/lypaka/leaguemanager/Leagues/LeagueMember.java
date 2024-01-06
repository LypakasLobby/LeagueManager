package com.lypaka.leaguemanager.Leagues;

import com.lypaka.leaguemanager.LeagueManager;

import java.util.List;

public abstract class LeagueMember {

    private final List<String> permissionsNeededToBattle;
    private final String npcLocation;
    private final String playerUUID;
    private final List<String> commandRewards;

    public LeagueMember (List<String> permissionsNeededToBattle, String npcLocation, String playerUUID, List<String> commandRewards) {

        this.permissionsNeededToBattle = permissionsNeededToBattle;
        this.npcLocation = npcLocation;
        this.playerUUID = playerUUID;
        this.commandRewards = commandRewards;

    }

    public List<String> getPermissionsNeededToBattle() {

        return this.permissionsNeededToBattle;

    }

    public String getNPCLocation() {

        return this.npcLocation;

    }

    public String getPlayerUUID() {

        return this.playerUUID;

    }

    public List<String> getCommandRewards() {

        return this.commandRewards;

    }

}
