package mchorse.mappet.client.gui.nodes.events;

import mchorse.mappet.api.events.nodes.CommandNode;
import mchorse.mappet.client.gui.nodes.GuiEventBaseNodePanel;
import mchorse.mappet.client.gui.utils.GuiMappetUtils;
import mchorse.mclib.client.gui.framework.elements.input.GuiTextElement;
import mchorse.mclib.client.gui.utils.Elements;
import mchorse.mclib.client.gui.utils.keys.IKey;
import net.minecraft.client.Minecraft;

public class GuiCommandNodePanel extends GuiEventBaseNodePanel<CommandNode>
{
    public GuiTextElement command;

    public GuiCommandNodePanel(Minecraft mc)
    {
        super(mc);

        this.command = GuiMappetUtils.fullWindowContext(
                new GuiTextElement(mc, 10000, (text) -> this.node.command = text),
                IKey.lang("mappet.gui.nodes.event.command")
        );

        this.add(Elements.label(IKey.lang("mappet.gui.nodes.event.command")).marginTop(12), this.command, this.binary);
    }

    @Override
    public void set(CommandNode node)
    {
        super.set(node);

        this.command.setText(node.command);
    }
}