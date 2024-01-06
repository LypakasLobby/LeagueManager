package com.lypaka.leaguemanager.Leagues;

import com.google.common.reflect.TypeToken;
import com.lypaka.leaguemanager.ConfigGetters;
import com.lypaka.leaguemanager.LeagueManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
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

                List<String> permissionsNeededToBattle = gcm.getConfigNode(i, "Permissions").getList(TypeToken.of(String.class));
                String npcLocation = gcm.getConfigNode(i, "NPC-Location").getString();
                String playerUUID = gcm.getConfigNode(i, "Player-UUID").getString();
                List<String> commandRewards = gcm.getConfigNode(i, "Rewards").getList(TypeToken.of(String.class));

                GymLeader gymLeader = new GymLeader(badge, gymDisplayIcon, gymName, gymLore, gymTheme, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
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
                List<String> permissionsNeededToBattle = lgm.getConfigNode(i, "Permissions").getList(TypeToken.of(String.class));
                String npcLocation = lgm.getConfigNode(i, "NPC-Location").getString();
                String playerUUID = lgm.getConfigNode(i, "Player-UUID").getString();
                List<String> commandRewards = lgm.getConfigNode(i, "Rewards").getList(TypeToken.of(String.class));

                E4Member e4Member = new E4Member(isChampion, permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
                e4Members.add(e4Member);

            }

            regionE4MemberMap.put(regionName, e4Members);

        }

    }

}
