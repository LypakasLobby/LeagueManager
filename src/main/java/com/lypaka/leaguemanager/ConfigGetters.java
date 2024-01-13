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

    public static String badgeCaseBorderID;
    public static int[] badgeCaseBorderSlots;
    public static String badgeCaseEmptySlotID;
    public static String badgeCaseEmptySlotDisplayName;
    public static List<String> badgeCaseHasBeatenLore;
    public static List<String> badgeCaseHasNotBeatenLore;
    public static int badgeCaseRows;
    public static String badgeCaseDisplayName;

    public static void load() throws ObjectMappingException {

        gyms = LeagueManager.configManager.getConfigNode(0, "Gyms").getValue(new TypeToken<Map<String, List<String>>>() {});
        leagues = LeagueManager.configManager.getConfigNode(0, "Leagues").getValue(new TypeToken<Map<String, List<String>>>() {});
        e4Broadcast = LeagueManager.configManager.getConfigNode(0, "Messages", "E4").getString();
        gymLeaderBroadcast = LeagueManager.configManager.getConfigNode(0, "Messages", "Gym-Leader").getString();
        e4PlayerUUIDs = LeagueManager.configManager.getConfigNode(0, "Players", "E4-Members").getValue(new TypeToken<Map<String, String>>() {});
        gymLeaderPlayerUUIDs = LeagueManager.configManager.getConfigNode(0, "Players", "Gym-Leaders").getValue(new TypeToken<Map<String, String>>() {});

        badgeCaseBorderID = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Border", "ID").getString();
        String[] slots = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Border", "Slots").getString().split(", ");
        badgeCaseBorderSlots = new int[slots.length];
        for (int i = 0; i < slots.length; i++) {

            badgeCaseBorderSlots[i] = Integer.parseInt(slots[i]);

        }
        badgeCaseEmptySlotID = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Empty-Slots", "ID").getString();
        badgeCaseEmptySlotDisplayName = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Empty-Slots", "Display-Name").getString();
        badgeCaseHasBeatenLore = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Lore", "Has-Beaten-Gym").getList(TypeToken.of(String.class));
        badgeCaseHasNotBeatenLore = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Lore", "Has-Not-Beaten-Gym").getList(TypeToken.of(String.class));
        badgeCaseRows = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Settings", "Rows").getInt();
        badgeCaseDisplayName = LeagueManager.configManager.getConfigNode(1, "Badge-Case-Menu", "Settings", "Title").getString();

    }

}
