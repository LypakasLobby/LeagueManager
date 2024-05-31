package com.lypaka.leaguemanager.Commands;

import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.leaguemanager.GUIs.BadgeCaseMenu;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ViewBadgesCommand {

    public ViewBadgesCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : LeagueManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("viewbadges")
                                            .then(
                                                    Commands.argument("player", EntityArgument.player())
                                                            .then(
                                                                    Commands.argument("region", StringArgumentType.word())
                                                                            .suggests(
                                                                                    (context, builder) -> ISuggestionProvider.suggest(ConfigGetters.regionNames, builder)
                                                                            )
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "leaguemanager.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }

                                                                                    ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                    String region = StringArgumentType.getString(c, "region");
                                                                                    BadgeCaseMenu.openPlayersMenu(player, target, region);

                                                                                }

                                                                                return 1;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
