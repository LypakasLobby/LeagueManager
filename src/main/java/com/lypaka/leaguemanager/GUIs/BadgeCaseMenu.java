package com.lypaka.leaguemanager.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.leaguemanager.Accounts.AccountHandler;
import com.lypaka.leaguemanager.ConfigGetters;
import com.lypaka.leaguemanager.Leagues.GymLeader;
import com.lypaka.leaguemanager.Leagues.LeagueHandler;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class BadgeCaseMenu {

    public static void openMenu (ServerPlayerEntity player, String region) {

        List<GymLeader> leaders = LeagueHandler.gymsInOrder.get(region);
        ChestTemplate template = ChestTemplate.builder(ConfigGetters.badgeCaseRows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.badgeCaseDisplayName.replace("%region%", region)))
                .build();

        List<Integer> allGUISlots = new ArrayList<>(ConfigGetters.badgeCaseRows * 9);
        for (int i = 0; i < ConfigGetters.badgeCaseRows; i++) {

            allGUISlots.add(i);

        }

        for (int i : ConfigGetters.badgeCaseBorderSlots) {

            allGUISlots.remove(i);
            page.getTemplate().getSlot(i).setButton(
                    GooeyButton.builder()
                            .display(
                                    ItemStackBuilder.buildFromStringID(ConfigGetters.badgeCaseBorderID).setDisplayName(FancyText.getFormattedText(""))
                            )
                            .build()
            );

        }
        int index = 0;
        for (int i : allGUISlots) {

            GymLeader leader = leaders.get(index);
            index++;
            ItemStack badge = leader.getBadge().getBadgeItem();

            ListNBT lore = new ListNBT();
            for (String s : leader.getGymDisplayLore()) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }
            lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
            List<String> itemLore = AccountHandler.hasBeatenGym(player, region, leader) ? ConfigGetters.badgeCaseHasBeatenLore : ConfigGetters.badgeCaseHasNotBeatenLore;
            for (String s : itemLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }
            badge.getOrCreateChildTag("display").put("Lore", lore);

            page.getTemplate().getSlot(i).setButton(GooeyButton.builder().display(badge).build());

        }

        UIManager.openUIForcefully(player, page);

    }

}
