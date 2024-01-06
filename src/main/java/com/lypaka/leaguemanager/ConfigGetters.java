package com.lypaka.leaguemanager;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static Map<String, List<String>> gyms;
    public static Map<String, List<String>> leagues;

    public static void load() throws ObjectMappingException {

        gyms = LeagueManager.configManager.getConfigNode(0, "Gyms").getValue(new TypeToken<Map<String, List<String>>>() {});
        leagues = LeagueManager.configManager.getConfigNode(0, "Leagues").getValue(new TypeToken<Map<String, List<String>>>() {});

    }

}
