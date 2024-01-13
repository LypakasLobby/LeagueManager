package com.lypaka.leaguemanager.Accounts;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;
import java.util.Map;

public class Account {

    private final ServerPlayerEntity player;
    private final Map<String, List<String>> beatenE4Members;
    private final Map<String, List<String>> beatenGyms;

    public Account (ServerPlayerEntity player, Map<String, List<String>> beatenE4Members, Map<String, List<String>> beatenGyms) {

        this.player = player;
        this.beatenE4Members = beatenE4Members;
        this.beatenGyms = beatenGyms;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Map<String, List<String>> getBeatenE4Members() {

        return this.beatenE4Members;

    }

    public Map<String, List<String>> getBeatenGyms() {

        return this.beatenGyms;

    }

}
