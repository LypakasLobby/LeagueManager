package com.lypaka.leaguemanager.Leagues;

import java.util.List;

public class E4Member extends LeagueMember {

    private final boolean isChampion;

    public E4Member (boolean isChampion, Location location, List<String> permissionsNeededToBattle, String npcLocation, String playerUUID, List<String> commandRewards) {

        super(location, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
        this.isChampion = isChampion;

    }

    public boolean isChampion() {

        return this.isChampion;

    }

}
