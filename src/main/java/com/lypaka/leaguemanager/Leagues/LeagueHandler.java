package com.lypaka.leaguemanager.Leagues;

import com.google.common.reflect.TypeToken;
import com.lypaka.leaguemanager.ConfigGetters;
import com.lypaka.leaguemanager.LeagueManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueHandler {

    public static Map<String, ComplexConfigManager> gymMap;
    public static Map<String, ComplexConfigManager> leagueMap;
    public static Map<String, List<GymLeader>> regionGymLeaderMap;
    public static Map<String, List<E4Member>> regionE4MemberMap;

    public static void loadGymsAndLeagues() throws ObjectMappingException {

        gymMap = new HashMap<>();
        leagueMap = new HashMap<>();
        regionGymLeaderMap = new HashMap<>();
        regionE4MemberMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : ConfigGetters.gyms.entrySet()) {

            String regionName = entry.getKey();
            List<String> gyms = entry.getValue();
            Path dir = ConfigUtils.checkDir(Paths.get("./config/leaguemanager/gyms"));
            ComplexConfigManager gcm = new ComplexConfigManager(gyms, regionName, "gymSettings.conf", dir, LeagueManager.class, LeagueManager.MOD_NAME, LeagueManager.MOD_ID, LeagueManager.logger, true);
            gcm.init();

            List<GymLeader> leaders = new ArrayList<>();
            for (int i = 0; i < gyms.size(); i++) {

                String badgeDisplayName = gcm.getConfigNode(i, "Badge", "Display-Name").getString();
                String badgeID = gcm.getConfigNode(i, "Badge", "ID").getString();
                List<String> badgeLore = gcm.getConfigNode(i, "Badge", "Lore").getList(TypeToken.of(String.class));
                GymBadge badge = new GymBadge(badgeDisplayName, badgeID, badgeLore);

                String gymDisplayIcon = gcm.getConfigNode(i, "Gym-Display-Icon").getString();
                List<String> gymLore = gcm.getConfigNode(i, "Gym-Info-Lore").getList(TypeToken.of(String.class));
                String gymName = gcm.getConfigNode(i, "Gym-Name").getString();
                String gymTheme = gcm.getConfigNode(i, "Gym-Theme").getString();

                int maxX = gcm.getConfigNode(i, "Gym-Location", "Max-X").getInt();
                int maxY = gcm.getConfigNode(i, "Gym-Location", "Max-Y").getInt();
                int maxZ = gcm.getConfigNode(i, "Gym-Location", "Max-Z").getInt();
                int minX = gcm.getConfigNode(i, "Gym-Location", "Min-X").getInt();
                int minY = gcm.getConfigNode(i, "Gym-Location", "Min-Y").getInt();
                int minZ = gcm.getConfigNode(i, "Gym-Location", "Min-Z").getInt();
                String worldName = gcm.getConfigNode(i, "Gym-Location", "World").getString();
                Location gymLocation = new Location(maxX, maxY, maxZ, minX, minY, minZ, worldName);

                List<String> permissionsNeededToBattle = gcm.getConfigNode(i, "Permissions").getList(TypeToken.of(String.class));
                String npcLocation = gcm.getConfigNode(i, "NPC-Location").getString();
                String playerUUID = gcm.getConfigNode(i, "Player-UUID").getString();
                List<String> commandRewards = gcm.getConfigNode(i, "Rewards").getList(TypeToken.of(String.class));

                GymLeader gymLeader = new GymLeader(badge, gymDisplayIcon, gymName, gymLore, gymTheme, gymLocation, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
                leaders.add(gymLeader);

            }

            regionGymLeaderMap.put(regionName, leaders);

        }

        for (Map.Entry<String, List<String>> entry : ConfigGetters.leagues.entrySet()) {

            String regionName = entry.getKey();
            List<String> leagues = entry.getValue();
            Path dir = ConfigUtils.checkDir(Paths.get("./config/leaguemanager/leagues"));
            ComplexConfigManager lgm = new ComplexConfigManager(leagues, regionName, "e4Settings.conf", dir, LeagueManager.class, LeagueManager.MOD_NAME, LeagueManager.MOD_ID, LeagueManager.logger, true);
            lgm.init();

            List<E4Member> e4Members = new ArrayList<>();
            for (int i = 0; i < leagues.size(); i++) {

                boolean isChampion = lgm.getConfigNode(i, "Is-Champion").getBoolean();

                int maxX = lgm.getConfigNode(i, "Room-Location", "Max-X").getInt();
                int maxY = lgm.getConfigNode(i, "Room-Location", "Max-Y").getInt();
                int maxZ = lgm.getConfigNode(i, "Room-Location", "Max-Z").getInt();
                int minX = lgm.getConfigNode(i, "Room-Location", "Min-X").getInt();
                int minY = lgm.getConfigNode(i, "Room-Location", "Min-Y").getInt();
                int minZ = lgm.getConfigNode(i, "Room-Location", "Min-Z").getInt();
                String worldName = lgm.getConfigNode(i, "Room-Location", "World").getString();
                Location leagueLocation = new Location(maxX, maxY, maxZ, minX, minY, minZ, worldName);

                List<String> permissionsNeededToBattle = lgm.getConfigNode(i, "Permissions").getList(TypeToken.of(String.class));
                String npcLocation = lgm.getConfigNode(i, "NPC-Location").getString();
                String playerUUID = lgm.getConfigNode(i, "Player-UUID").getString();
                List<String> commandRewards = lgm.getConfigNode(i, "Rewards").getList(TypeToken.of(String.class));

                E4Member e4Member = new E4Member(isChampion, leagueLocation, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
                e4Members.add(e4Member);

            }

            regionE4MemberMap.put(regionName, e4Members);

        }

    }

    public static boolean isEntityALeagueMember (String region, Entity entity) {

        String value;
        if (entity instanceof NPCTrainer) {

            String world = WorldMap.getWorldName(entity.world);
            int x = entity.getPosition().getX();
            int y = entity.getPosition().getY();
            int z = entity.getPosition().getZ();
            value = world + "," + x + "," + y + "," + z;

        } else {

            value = entity.getUniqueID().toString();

        }
        boolean is = false;
        if (regionGymLeaderMap.containsKey(region)) {

            List<GymLeader> gymLeaders = regionGymLeaderMap.get(region);
            for (GymLeader leader : gymLeaders) {

                if (leader.getNPCLocation().equalsIgnoreCase(value) || leader.getPlayerUUID().equalsIgnoreCase(value)) {

                    is = true;
                    break;

                }

            }

        }
        if (!is) {

            if (regionE4MemberMap.containsKey(region)) {

                List<E4Member> e4Members = regionE4MemberMap.get(region);
                for (E4Member member : e4Members) {

                    if (member.getNPCLocation().equalsIgnoreCase(value) || member.getPlayerUUID().equalsIgnoreCase(value)) {

                        is = true;
                        break;

                    }

                }

            }

        }

        return is;

    }

    public static LeagueMember getFromEntity (String region, Entity entity) {

        LeagueMember member = null;
        String value;
        if (entity instanceof NPCTrainer) {

            String world = WorldMap.getWorldName(entity.world);
            int x = entity.getPosition().getX();
            int y = entity.getPosition().getY();
            int z = entity.getPosition().getZ();
            value = world + "," + x + "," + y + "," + z;

        } else {

            value = entity.getUniqueID().toString();

        }
        if (regionGymLeaderMap.containsKey(region)) {

            List<GymLeader> gymLeaders = regionGymLeaderMap.get(region);
            for (GymLeader leader : gymLeaders) {

                if (leader.getNPCLocation().equalsIgnoreCase(value) || leader.getPlayerUUID().equalsIgnoreCase(value)) {

                    member = leader;
                    break;

                }

            }

        }
        if (member == null) {

            if (regionE4MemberMap.containsKey(region)) {

                List<E4Member> e4Members = regionE4MemberMap.get(region);
                for (E4Member e4Member : e4Members) {

                    if (e4Member.getNPCLocation().equalsIgnoreCase(value) || e4Member.getPlayerUUID().equalsIgnoreCase(value)) {

                        member = e4Member;
                        break;

                    }

                }

            }

        }

        return member;

    }

    public static boolean isCurrentlyStandingInGym (LeagueMember member, Entity entityToBeat) {

        int x = entityToBeat.getPosition().getX();
        int y = entityToBeat.getPosition().getY();
        int z = entityToBeat.getPosition().getZ();
        String worldName = WorldMap.getWorldName(entityToBeat.world);

        int maxX = member.getLocation().getMaxX();
        int maxY = member.getLocation().getMaxY();
        int maxZ = member.getLocation().getMaxZ();
        int minX = member.getLocation().getMinX();
        int minY = member.getLocation().getMinY();
        int minZ = member.getLocation().getMinZ();
        String world = member.getLocation().getWorld();

        if (world.equalsIgnoreCase(worldName)) {

            if (x >= minX && x <= maxX) {

                if (y >= minY && y <= maxY) {

                    return z >= minZ && z <= maxZ;

                }

            }

        }

        return false;

    }

    public static LeagueMember getMemberFromEntityLocation (String region, Entity entity) {

        LeagueMember member = null;
        String worldName = WorldMap.getWorldName(entity.world);
        int x = entity.getPosition().getX();
        int y = entity.getPosition().getY();
        int z = entity.getPosition().getZ();

        String entityLocation = worldName + "," + x + "," + y + "," + z;

        List<GymLeader> leaders = regionGymLeaderMap.get(region);
        for (GymLeader leader : leaders) {

            if (leader.getNPCLocation().equalsIgnoreCase(entityLocation)) {

                member = leader;
                break;

            }

        }

        if (member == null) {

            List<E4Member> e4Members = regionE4MemberMap.get(region);
            for (E4Member e4Member : e4Members) {

                if (e4Member.getNPCLocation().equalsIgnoreCase(entityLocation)) {

                    member = e4Member;
                    break;

                }

            }

        }

        return member;

    }

}
