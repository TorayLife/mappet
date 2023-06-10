package mchorse.mappet.network.server.dialogue;

import mchorse.mappet.Mappet;
import mchorse.mappet.api.dialogues.Dialogue;
import mchorse.mappet.api.dialogues.DialogueContext;
import mchorse.mappet.api.dialogues.nodes.ReactionNode;
import mchorse.mappet.api.events.nodes.EventBaseNode;
import mchorse.mappet.capabilities.character.Character;
import mchorse.mappet.capabilities.character.ICharacter;
import mchorse.mappet.network.common.dialogue.PacketPickReply;
import mchorse.mclib.network.ServerMessageHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerHandlerPickReply extends ServerMessageHandler<PacketPickReply>
{
    @Override
    public void run(EntityPlayerMP player, PacketPickReply message)
    {
        ICharacter character = Character.get(player);

        if (character != null && character.getDialogue() != null)
        {
            int i = message.index;

            Dialogue dialogue = character.getDialogue();
            DialogueContext context = character.getDialogueContext();
            EventBaseNode node = context.crafting;

            if (i >= 0 && i < context.replyNodes.size())
            {
                node = context.replyNodes.get(i);
            }
            else if (context.questChain != null)
            {
                node = context.questChain;
            }
            else if (context.quest != null)
            {
                node = context.quest;
            }

            ReactionNode reactionNode = context.reactionNode;

            context.resetAll();
            Mappet.dialogues.recursiveExecute(dialogue, node, context, true);
            Mappet.dialogues.handleContext(player, dialogue, context, reactionNode);
        }
    }
}