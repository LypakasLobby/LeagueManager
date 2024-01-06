package com.lypaka.leaguemanager.Leagues;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class GymBadge {

    private final String badgeDisplayName;
    private final String badgeID;
    private final List<String> badgeLore;

    public GymBadge (String badgeDisplayName, String badgeID, List<String> badgeLore) {

        this.badgeDisplayName = badgeDisplayName;
        this.badgeID = badgeID;
        this.badgeLore = badgeLore;

    }

    public String getBadgeDisplayName() {

        return this.badgeDisplayName;

    }

    public String getBadgeID() {

        return this.badgeID;

    }

    public List<String> getBadgeLore() {

        return this.badgeLore;

    }

    public ItemStack getBadgeItem() {

        ItemStack badge = ItemStackBuilder.buildFromStringID(this.badgeID);
        badge.setCount(1); // I know this is a default thing ItemStacks do, but I like being thorough
        badge.setDisplayName(FancyText.getFormattedText(this.badgeDisplayName));
        if (!this.badgeLore.isEmpty()) {

            ListNBT lore = new ListNBT();
            for (String s : this.badgeLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }

            badge.getOrCreateChildTag("display").put("Lore", lore);

        }

        return badge;

    }

}
