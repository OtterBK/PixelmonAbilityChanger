package com.bokum.abilitychanger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class CommandManager
{
    private static final Logger LOGGER = LogManager.getLogger();

    CommandDispatcher<CommandSource> dispatcher_;

    public CommandManager(CommandDispatcher<CommandSource> dispatcher)
    {
        this.dispatcher_ = dispatcher;
        initializeCommands();
    }

    private void initializeCommands()
    {
        registerSetAbility();
    }

    private void registerSetAbility()
    {
        String command_name = "setpab";
        dispatcher_.register(Commands.literal(command_name)
        .then(Commands.argument("slot", IntegerArgumentType.integer())
        .then(Commands.argument("ability_name", StringArgumentType.string())
        .executes(context ->
        {
            ServerPlayerEntity player = context.getSource().getPlayerOrException();
            int slot = IntegerArgumentType.getInteger(context, "slot");
            String ability_name = StringArgumentType.getString(context, "ability_name");

            context.getSource().sendSuccess(new StringTextComponent("§7대상 슬롯: §b"+ slot + "§7, 능력: §b" + ability_name), false);

            AbilityChanger.changeAbility(player, slot, ability_name);

            return 1;
        }))));

        LOGGER.info(command_name + " has been registered");
    }
}
