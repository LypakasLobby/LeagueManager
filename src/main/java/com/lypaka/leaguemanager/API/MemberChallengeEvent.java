package com.lypaka.leaguemanager.API;

import com.lypaka.leaguemanager.Leagues.LeagueMember;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when a player attempts to challenge a Gym Leader or E4 member
 * Canceling the event cancels the battle start
 */
@Cancelable
public class MemberChallengeEvent extends Event {

    private final ServerPlayerEntity challenger;
    private final LeagueMember member;

    public MemberChallengeEvent (ServerPlayerEntity challenger, LeagueMember member) {

        this.challenger = challenger;
        this.member = member;

    }

    public ServerPlayerEntity getChallenger() {

        return this.challenger;

    }

    public LeagueMember getMember() {

        return this.member;

    }

}
