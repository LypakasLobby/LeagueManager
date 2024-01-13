package com.lypaka.leaguemanager;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static Map<String, List<String>> gyms;
    public static Map<String, List<String>> leagues;
    public static String e4Broadcast;
    public static String gymLeaderBroadcast;
    public static Map<String, String> e4PlayerUUIDs;
    public static Map<String, String> gymLeaderPlayerUUIDs;

    public static void load() throws ObjectMappingException {

        gyms = LeagueManager.configManager.getConfigNode(0, "Gyms").getValue(new TypeToken<Map<String, List<String>>>() {});
        leagues = LeagueManager.configManager.getConfigNode(0, "Leagues").getValue(new TypeToken<Map<String, List<String>>>() {});
        e4Broadcast = LeagueManager.configManager.getConfigNode(0, "Messages", "E4").getString();
        gymLeaderBroadcast = LeagueManager.configManager.getConfigNode(0, "Messages", "Gym-Leader").getString();
        e4PlayerUUIDs = LeagueManager.configManager.getConfigNode(0, "Players", "E4-Members").getValue(new TypeToken<Map<String, String>>() {});
        gymLeaderPlayerUUIDs = LeagueManager.configManager.getConfigNode(0, "Players", "Gym-Leaders").getValue(new TypeToken<Map<String, String>>() {});

    }

}
