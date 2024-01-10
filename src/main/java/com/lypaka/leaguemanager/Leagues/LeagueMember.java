package com.lypaka.leaguemanager.Leagues;

import com.lypaka.leaguemanager.LeagueManager;

import java.util.List;

public abstract class LeagueMember {

    private final Location location;
    private final List<String> permissionsNeededToBattle;
    private final String npcLocation;
    private final String playerUUID;
    private final List<String> commandRewards;

    public LeagueMember (Location location, List<String> permissionsNeededToBattle, String npcLocation, String playerUUID, List<String> commandRewards) {

        this.location = location;
        this.permissionsNeededToBattle = permissionsNeededToBattle;
        this.npcLocation = npcLocation;
        this.playerUUID = playerUUID;
        this.commandRewards = commandRewards;

    }

    public Location getLocation() {

        return this.location;

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
