package com.lypaka.leaguemanager.Accounts;

import com.google.common.reflect.TypeToken;
import com.lypaka.leaguemanager.LeagueManager;
import com.lypaka.leaguemanager.Leagues.E4Member;
import com.lypaka.leaguemanager.Leagues.GymLeader;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.*;

public class AccountHandler {

    public static Map<UUID, Account> accountMap = new HashMap<>();

    public static void createAccount (ServerPlayerEntity player) throws ObjectMappingException {

        System.out.println("creating account object for player " + player.getName().getString() + " / " + player.getUniqueID().toString());
        LeagueManager.playerConfigManager.loadPlayer(player.getUniqueID());
        Map<String, List<String>> beatenE4Members = LeagueManager.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Beaten-E4-Members").getValue(new TypeToken<Map<String, List<String>>>() {});
        System.out.println("e4 map == " + beatenE4Members);
        Map<String, List<String>> beatenGyms = LeagueManager.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Beaten-Gyms").getValue(new TypeToken<Map<String, List<String>>>() {});
        System.out.println("gym map == " + beatenGyms);

        Account account = new Account(player, beatenE4Members, beatenGyms);
        System.out.println("account object created --> " + account);
        accountMap.put(player.getUniqueID(), account);
        System.out.println("put " + player.getUniqueID().toString() + " in the map with account object " + account);

    }

    public static void removeAccount (ServerPlayerEntity player) {

        saveAccount(player);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                accountMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));
                System.out.println("player " + player.getName().getString() + " / " + player.getUniqueID().toString() + " has been removed from the account map");

            }

        }, 1000);

    }

    public static void saveAccount (ServerPlayerEntity player) {

        System.out.println("player == " + player);
        System.out.println("uuid == " + player.getUniqueID());
        Account account = accountMap.get(player.getUniqueID());
        System.out.println("account == " + account);
        LeagueManager.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Beaten-E4-Members").setValue(account.getBeatenE4Members());
        LeagueManager.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Beaten-Gyms").setValue(account.getBeatenGyms());
        LeagueManager.playerConfigManager.savePlayer(player.getUniqueID());

    }

    public static void markGymBeaten (ServerPlayerEntity player, String region, String name) {

        Account account = accountMap.get(player.getUniqueID());
        List<String> gyms = new ArrayList<>();
        //List<String> gyms = account.getBeatenGyms().get(region);
        if (account.getBeatenGyms().containsKey(region)) {

            gyms = account.getBeatenGyms().get(region);

        } else {

            gyms = new ArrayList<>();

        }
        gyms.add(name);
        account.getBeatenGyms().put(region, gyms);
        saveAccount(player);

    }

    public static void markE4MemberBeaten (ServerPlayerEntity player, String region, String name) {

        Account account = accountMap.get(player.getUniqueID());
        List<String> e4s = account.getBeatenE4Members().get(region);
        e4s.add(name);
        account.getBeatenE4Members().put(region, e4s);
        saveAccount(player);

    }

    public static boolean hasBeatenGym (ServerPlayerEntity player, String region, GymLeader leader) {

        Account account = accountMap.get(player.getUniqueID());
        if (!account.getBeatenGyms().containsKey(region)) return false;
        return account.getBeatenGyms().get(region).contains(leader.getGymName());

    }

    public static boolean hasBeatenMember (ServerPlayerEntity player, String region, E4Member member) {

        Account account = accountMap.get(player.getUniqueID());
        return account.getBeatenE4Members().get(region).contains(member.getMemberName());

    }

}
