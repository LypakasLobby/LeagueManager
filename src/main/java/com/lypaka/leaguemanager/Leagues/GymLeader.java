package com.lypaka.leaguemanager.Leagues;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class GymLeader extends LeagueMember {

    private final GymBadge badge;
    private final String gymDisplayIcon;
    private final List<String> gymDisplayLore;
    private final String gymName;
    private final String gymTheme;

    public GymLeader (GymBadge badge, String gymDisplayIcon, String gymName, List<String> gymDisplayLore, String gymTheme,
                      List<String> permissionsNeededToBattle, String npcLocation, String playerUUID, List<String> commandRewards) {

        super(permissionsNeededToBattle, npcLocation, playerUUID, commandRewards);
        this.badge = badge;
        this.gymDisplayIcon = gymDisplayIcon;
        this.gymName = gymName;
        this.gymDisplayLore = gymDisplayLore;
        this.gymTheme = gymTheme;

    }

    public GymBadge getBadge() {

        return this.badge;

    }

    public String getGymDisplayIcon() {

        return this.gymDisplayIcon;

    }

    public List<String> getGymDisplayLore() {

        return this.gymDisplayLore;

    }

    public String getGymTheme() {

        return this.gymTheme;

    }

    public ItemStack getGymDisplayItem() {

        ItemStack item = ItemStackBuilder.buildFromStringID(this.gymDisplayIcon);
        item.setCount(1);
        item.setDisplayName(FancyText.getFormattedText(this.gymName));
        if (!this.gymDisplayLore.isEmpty()) {

            ListNBT lore = new ListNBT();
            for (String s : this.gymDisplayLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }

            item.getOrCreateChildTag("display").put("Lore", lore);

        }

        return item;

    }

}
