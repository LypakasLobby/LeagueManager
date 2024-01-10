package com.lypaka.leaguemanager.Listeners;

import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.leaguemanager.API.MemberChallengeEvent;
import com.lypaka.leaguemanager.API.MemberDefeatEvent;
import com.lypaka.leaguemanager.Leagues.*;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleStartedEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.TrainerParticipant;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class BattleListeners {

    @SubscribeEvent
    public void onBattlePreStart (BattleStartedEvent.Pre event) {

        Entity entityRepresenting = null;
        ServerPlayerEntity playerChallenger = null;
        BattleController bc = event.getBattleController();
        Region region = null;

        if (bc.participants.get(0) instanceof PlayerParticipant && bc.participants.get(1) instanceof TrainerParticipant) {

            entityRepresenting = ((TrainerParticipant) bc.participants.get(1)).trainer;
            playerChallenger = ((PlayerParticipant) bc.participants.get(0)).player;
            // Grab the region from the challenger
            region = RegionHandler.getRegionAtPlayer(playerChallenger);

        } else if (bc.participants.get(0) instanceof TrainerParticipant && bc.participants.get(1) instanceof PlayerParticipant) {

            entityRepresenting = ((TrainerParticipant) bc.participants.get(0)).trainer;
            playerChallenger = ((PlayerParticipant) bc.participants.get(1)).player;
            // Grab the region from the challenger
            region = RegionHandler.getRegionAtPlayer(playerChallenger);

        } else if (bc.participants.get(0) instanceof PlayerParticipant && bc.participants.get(1) instanceof PlayerParticipant) {

            // Grab the region at one of the players, as they are sure to be in the same region unless of other out-of-my-hands situations
            entityRepresenting = ((PlayerParticipant) bc.participants.get(0)).player;
            playerChallenger = ((PlayerParticipant) bc.participants.get(1)).player;
            region = RegionHandler.getRegionAtPlayer(playerChallenger);

        } else {

            return;

        }

        if (region == null) return;
        if (!LeagueHandler.regionGymLeaderMap.containsKey(region.getName()) && !LeagueHandler.regionE4MemberMap.containsKey(region.getName())) return;

        LeagueMember pkmnLeagueMember = null;
        Entity leagueLeader = null;
        ServerPlayerEntity challenger = null;
        if (LeagueHandler.regionGymLeaderMap.containsKey(region.getName())) {

            List<GymLeader> leaders = LeagueHandler.regionGymLeaderMap.get(region.getName());
            for (GymLeader leader : leaders) {

                if (!leader.getPlayerUUID().equalsIgnoreCase("")) {

                    if (entityRepresenting.getUniqueID().toString().equalsIgnoreCase(leader.getPlayerUUID())) {

                        leagueLeader = entityRepresenting;
                        challenger = playerChallenger;
                        pkmnLeagueMember = leader;
                        break;

                    } else if (playerChallenger.getUniqueID().toString().equalsIgnoreCase(leader.getPlayerUUID())) {

                        challenger = (ServerPlayerEntity) entityRepresenting;
                        leagueLeader = playerChallenger;
                        pkmnLeagueMember = leader;
                        break;

                    }

                }

            }

        }

        if (leagueLeader == null) {

            if (!LeagueHandler.regionE4MemberMap.containsKey(region.getName())) return;
            List<E4Member> e4Members = LeagueHandler.regionE4MemberMap.get(region.getName());
            for (E4Member e4Member : e4Members) {

                if (!e4Member.getPlayerUUID().equalsIgnoreCase("")) {

                    if (entityRepresenting.getUniqueID().toString().equalsIgnoreCase(e4Member.getPlayerUUID())) {

                        leagueLeader = entityRepresenting;
                        challenger = playerChallenger;
                        pkmnLeagueMember = e4Member;
                        break;

                    } else if (playerChallenger.getUniqueID().toString().equalsIgnoreCase(e4Member.getPlayerUUID())) {

                        challenger = (ServerPlayerEntity) entityRepresenting;
                        leagueLeader = playerChallenger;
                        pkmnLeagueMember = e4Member;
                        break;

                    }

                }

            }

        }

        if (leagueLeader == null) return;

        // We have a League Member, now we need to check if the leader is in his Gym/E4 room area
        if (LeagueHandler.isCurrentlyStandingInGym(pkmnLeagueMember, leagueLeader)) {

            // We check if the player has all the required permissions to challenge the leader, if applicable
            List<String> permissions = pkmnLeagueMember.getPermissionsNeededToBattle();
            if (!permissions.isEmpty()) {

                boolean hasPermission = true;
                for (String p : permissions) {

                    if (!PermissionHandler.hasPermission(challenger, p)) {

                        hasPermission = false;
                        break;

                    }

                }

                if (!hasPermission) {

                    event.setCanceled(true);
                    // player is missing a permission, cancel battle and send message
                    if (leagueLeader instanceof ServerPlayerEntity) {

                        ServerPlayerEntity playerLeader = (ServerPlayerEntity) leagueLeader;
                        playerLeader.sendMessage(FancyText.getFormattedText("&eThe challenger is missing permissions to challenge you!"), playerLeader.getUniqueID());

                    }
                    String mode = pkmnLeagueMember instanceof GymLeader ? "Gym Leader" : "E4 Member";
                    challenger.sendMessage(FancyText.getFormattedText("&eYou are missing one or more required permissions to challenge this " + mode + "!"), challenger.getUniqueID());

                } else {

                    MemberChallengeEvent challengeEvent = new MemberChallengeEvent(challenger, pkmnLeagueMember);
                    MinecraftForge.EVENT_BUS.post(challengeEvent);
                    if (challengeEvent.isCanceled()) {

                        event.setCanceled(true);
                        if (leagueLeader instanceof ServerPlayerEntity) {

                            ServerPlayerEntity playerLeader = (ServerPlayerEntity) leagueLeader;
                            playerLeader.sendMessage(FancyText.getFormattedText("&eThe challenge has been canceled!"), playerLeader.getUniqueID());

                        }
                        challenger.sendMessage(FancyText.getFormattedText("&eThe challenge has been canceled!"), challenger.getUniqueID());

                    }

                }

            }

        }

    }

    @SubscribeEvent
    public void onNPCDefeat (BeatTrainerEvent event) {

        NPCTrainer trainer = event.trainer;
        ServerPlayerEntity player = event.player;
        Region region = RegionHandler.getRegionAtPlayer(player);
        if (region == null) return;
        if (!LeagueHandler.regionE4MemberMap.containsKey(region.getName()) && !LeagueHandler.regionGymLeaderMap.containsKey(region.getName())) return;

        if (LeagueHandler.isEntityALeagueMember(region.getName(), trainer)) {

            LeagueMember leagueMember = LeagueHandler.getFromEntity(region.getName(), trainer);
            MemberDefeatEvent defeatEvent = new MemberDefeatEvent(player, leagueMember);
            MinecraftForge.EVENT_BUS.post(defeatEvent);
            if (leagueMember instanceof GymLeader) {

                GymLeader gymLeader = (GymLeader) leagueMember;
                GymBadge badge = gymLeader.getBadge();
                player.addItemStackToInventory(badge.getBadgeItem());

            }
            List<String> rewardCommands = leagueMember.getCommandRewards();
            for (String c : rewardCommands) {

                player.getServer().getCommandManager().handleCommand(player.getServer().getCommandSource(), c.replace("%player%", player.getName().getString()));

            }

        }

    }

    @SubscribeEvent
    public void onPlayerDefeat (BattleEndEvent event) {

        ServerPlayerEntity p1 = null;
        ServerPlayerEntity p2 = null;
        PlayerParticipant leaderParticipant = null; // used for checking .isDefeated()
        BattleController bc = event.getBattleController();

        if (bc.participants.get(0) instanceof PlayerParticipant && bc.participants.get(1) instanceof PlayerParticipant) {

            p1 = ((PlayerParticipant) bc.participants.get(0)).player;
            p2 = ((PlayerParticipant) bc.participants.get(1)).player;

        }

        if (p1 == null || p2 == null) return;

        Region region = RegionHandler.getRegionAtPlayer(p1);
        if (region == null) return;
        if (!LeagueHandler.regionE4MemberMap.containsKey(region.getName()) && !LeagueHandler.regionGymLeaderMap.containsKey(region.getName())) return;

        LeagueMember member = LeagueHandler.getFromEntity(region.getName(), p1);
        ServerPlayerEntity challenger = null;
        Entity entityToBeat = null;
        if (member != null) {

            challenger = p2;
            entityToBeat = p1;
            for (BattleParticipant bp : bc.participants) {

                if (bp instanceof PlayerParticipant) {

                    PlayerParticipant pp = (PlayerParticipant) bp;
                    if (pp.player.getUniqueID().toString().equalsIgnoreCase(p1.getUniqueID().toString())) {

                        leaderParticipant = pp;
                        break;

                    }

                }

            }

        } else {

            member = LeagueHandler.getFromEntity(region.getName(), p2);
            if (member != null) {

                challenger = p1;
                entityToBeat = p2;
                for (BattleParticipant bp : bc.participants) {

                    if (bp instanceof PlayerParticipant) {

                        PlayerParticipant pp = (PlayerParticipant) bp;
                        if (pp.player.getUniqueID().toString().equalsIgnoreCase(p2.getUniqueID().toString())) {

                            leaderParticipant = pp;
                            break;

                        }

                    }

                }

            }

        }

        if (member == null) return;

        if (LeagueHandler.isCurrentlyStandingInGym(member, entityToBeat)) {

            // player league member is in gym/e4 room
            if (leaderParticipant.isDefeated) { // cries about "could be null" here but there's literally no way that will ever happen in this situation, unless Pixelmon's more broken than I thought

                // challenger won, reward
                MemberDefeatEvent defeatEvent = new MemberDefeatEvent(challenger, member);
                MinecraftForge.EVENT_BUS.post(defeatEvent);
                if (member instanceof GymLeader) {

                    GymLeader gymLeader = (GymLeader) member;
                    GymBadge badge = gymLeader.getBadge();
                    challenger.addItemStackToInventory(badge.getBadgeItem());

                }
                List<String> rewardCommands = member.getCommandRewards();
                for (String c : rewardCommands) {

                    challenger.getServer().getCommandManager().handleCommand(challenger.getServer().getCommandSource(), c.replace("%player%", challenger.getName().getString()));

                }

            }

        }

    }

}
