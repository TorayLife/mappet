package mchorse.mappet.commands.states;

import mchorse.mappet.api.states.States;
import mchorse.mappet.commands.CommandMappet;
import mchorse.mappet.commands.MappetCommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public abstract class CommandStateBase extends MappetCommandBase
{
    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return args.length > 0 && !args[0].equals("~") && index == 0;
    }

    @Override
    public int getRequiredArgs()
    {
        return 3;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, CommandMappet.listOfPlayersAndServer(server));
        }

        if (args.length == 2)
        {
            try
            {
                States states = CommandState.getStates(server, sender, args[0]);

                return getListOfStringsMatchingLastWord(args, states.values.keySet());
            }
            catch (Exception e)
            {}
        }

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}