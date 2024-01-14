package com.lypaka.leaguemanager.Leagues;

import java.util.List;

public class E4Member extends LeagueMember {

    private final String memberName;
    private final String displayID;
    private final List<String> lore;
    private final String displayName;
    private final boolean isChampion;

    public E4Member (String memberName, String displayID, List<String> lore, String displayName, boolean isChampion, Location location, List<String> permissionsNeededToBattle, String npcLocation, int orderNumber, String playerUUID, List<String> commandRewards) {

        super(location, permissionsNeededToBattle, npcLocation, orderNumber, playerUUID, commandRewards);
        this.isChampion = isChampion;
        this.memberName = memberName;
        this.displayID = displayID;
        this.lore = lore;
        this.displayName = displayName;

    }

    public String getMemberName() {

        return this.memberName;

    }

    public String getDisplayID() {

        return this.displayID;

    }

    public List<String> getLore() {

        return this.lore;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public boolean isChampion() {

        return this.isChampion;

    }

}
