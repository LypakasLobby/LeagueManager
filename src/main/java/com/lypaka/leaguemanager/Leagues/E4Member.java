package com.lypaka.leaguemanager.Leagues;

import java.util.List;

public class E4Member extends LeagueMember {

    private final String memberName;
    private final boolean isChampion;

    public E4Member (String memberName, boolean isChampion, Location location, List<String> permissionsNeededToBattle, String npcLocation, String playerUUID, List<String> commandRewards) {

        super(location, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
        this.isChampion = isChampion;
        this.memberName = memberName;

    }

    public String getMemberName() {

        return this.memberName;

    }

    public boolean isChampion() {

        return this.isChampion;

    }

}
