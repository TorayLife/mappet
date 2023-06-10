package mchorse.mappet.client.gui;

import mchorse.mappet.api.crafting.CraftingTable;
import mchorse.mappet.client.gui.crafting.GuiCrafting;
import mchorse.mappet.client.gui.crafting.ICraftingScreen;
import mchorse.mappet.network.Dispatcher;
import mchorse.mappet.network.common.crafting.PacketCraftingTable;
import mchorse.mclib.client.gui.framework.GuiBase;
import net.minecraft.client.Minecraft;

public class GuiCraftingTableScreen extends GuiBase implements ICraftingScreen
{
    public GuiCrafting crafting;

    public GuiCraftingTableScreen(CraftingTable table)
    {
        super();

        Minecraft mc = Minecraft.getMinecraft();

        this.crafting = new GuiCrafting(mc);
        this.crafting.set(table);
        this.crafting.flex().relative(this.viewport).y(20).w(1F).h(1F, -20);

        this.root.add(this.crafting);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void refresh()
    {
        this.crafting.refresh();
    }

    @Override
    protected void closeScreen()
    {
        super.closeScreen();

        Dispatcher.sendToServer(new PacketCraftingTable(null));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawCenteredString(this.fontRenderer, this.crafting.get().title, this.viewport.mx(), 11, 0xffffff);
    }
}