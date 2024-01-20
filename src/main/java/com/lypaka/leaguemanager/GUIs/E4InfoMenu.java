package com.lypaka.leaguemanager.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.leaguemanager.Accounts.AccountHandler;
import com.lypaka.leaguemanager.ConfigGetters;
import com.lypaka.leaguemanager.Leagues.E4Member;
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

public class E4InfoMenu {

    public static void openMenu (ServerPlayerEntity player, String region) {

        List<E4Member> members = LeagueHandler.e4MembersInOrder.get(region);
        ChestTemplate template = ChestTemplate.builder(ConfigGetters.e4Rows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.e4Title.replace("%region%", region)))
                .build();

        List<Integer> allGUISlots = new ArrayList<>(ConfigGetters.e4Rows * 9);
        for (int i = 0; i < ConfigGetters.e4Rows; i++) {

            allGUISlots.add(i);

        }

        for (int i : ConfigGetters.e4BorderSlots) {

            allGUISlots.remove(i);
            page.getTemplate().getSlot(i).setButton(
                    GooeyButton.builder()
                            .display(
                                    ItemStackBuilder.buildFromStringID(ConfigGetters.e4BorderID).setDisplayName(FancyText.getFormattedText(""))
                            )
                            .build()
            );

        }

        int index = 0;
        for (int i : allGUISlots) {

            E4Member e4Member = members.get(index);
            index++;
            ItemStack item = ItemStackBuilder.buildFromStringID(e4Member.getDisplayID()).setDisplayName(FancyText.getFormattedText(e4Member.getDisplayName()));

            List<String> itemLore = AccountHandler.hasBeatenMember(player, region, e4Member) ? ConfigGetters.e4HasBeatenLore : ConfigGetters.e4HasNotBeatenLore;
            ListNBT lore = new ListNBT();
            for (String s : e4Member.getLore()) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }
            lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
            for (String s : itemLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }
            item.getOrCreateChildTag("display").put("Lore", lore);

            page.getTemplate().getSlot(i).setButton(GooeyButton.builder().display(item).build());

        }

        UIManager.openUIForcefully(player, page);

    }

}
