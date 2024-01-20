package com.lypaka.leaguemanager.Commands;

import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.leaguemanager.GUIs.BadgeCaseMenu;
import com.lypaka.leaguemanager.Leagues.LeagueHandler;
import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class BadgeCaseCommand {

    public BadgeCaseCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : LeagueManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("badgecase")
                                            .then(
                                                    Commands.argument("region", StringArgumentType.word())
                                                            .suggests(
                                                                    (context, builder) -> ISuggestionProvider.suggest(ConfigGetters.regionNames, builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    String region = StringArgumentType.getString(c, "region");
                                                                    if (LeagueHandler.regionGymLeaderMap.containsKey(region)) {

                                                                        BadgeCaseMenu.openMenu(player, region);

                                                                    } else {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYour current region has no Gym Leaders!"), player.getUniqueID());

                                                                    }

                                                                }

                                                                return 0;

                                                            })
                                            )
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    Region region = RegionHandler.getRegionAtPlayer(player);
                                                    if (region != null) {

                                                        if (LeagueHandler.regionGymLeaderMap.containsKey(region.getName())) {

                                                            BadgeCaseMenu.openMenu(player, region.getName());

                                                        } else {

                                                            player.sendMessage(FancyText.getFormattedText("&cYour current region has no Gym Leaders!"), player.getUniqueID());

                                                        }

                                                    } else {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou're not currently in a region, thus no Gym Leaders exist here!"), player.getUniqueID());

                                                    }

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
