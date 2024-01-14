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
    public static String e4BorderID;
    public static int[] e4BorderSlots;
    public static String e4EmptySlotID;
    public static String e4EmptyDisplayName;
    public static List<String> e4HasBeatenLore;
    public static List<String> e4HasNotBeatenLore;
    public static int e4Rows;
    public static String e4Title;

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
        e4BorderID = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Border", "ID").getString();
        String[] e4Slots = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Border", "Slots").getString().split(", ");
        e4BorderSlots = new int[e4Slots.length];
        for (int i = 0; i < e4Slots.length; i++) {

            e4BorderSlots[i] = Integer.parseInt(e4Slots[i]);

        }
        e4EmptySlotID = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Empty-Slots", "ID").getString();
        e4EmptyDisplayName = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Empty-Slots", "Display-Name").getString();
        e4HasBeatenLore = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Lore", "Has-Beaten").getList(TypeToken.of(String.class));
        e4HasNotBeatenLore = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Lore", "Has-Not-Beaten").getList(TypeToken.of(String.class));
        e4Rows = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Settings", "Rows").getInt();
        e4Title = LeagueManager.configManager.getConfigNode(1, "E4-Menu", "Settings", "Title").getString();

    }

}
