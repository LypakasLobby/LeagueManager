package com.lypaka.leaguemanager.API;

import com.lypaka.leaguemanager.Leagues.E4Member;
import com.lypaka.leaguemanager.Leagues.GymLeader;
import com.lypaka.leaguemanager.Leagues.LeagueMember;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class MemberDefeatEvent extends Event {

    private final ServerPlayerEntity player;
    private final LeagueMember member;

    public MemberDefeatEvent (ServerPlayerEntity player, LeagueMember member) {

        this.player = player;
        this.member = member;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public LeagueMember getMember() {

        return this.member;

    }

    public boolean isGym() {

        return this.member instanceof GymLeader;

    }

    public boolean isChampion() {

        if (this.member instanceof GymLeader) return false;
        E4Member e4Member = (E4Member) this.member;
        return e4Member.isChampion();

    }

}
