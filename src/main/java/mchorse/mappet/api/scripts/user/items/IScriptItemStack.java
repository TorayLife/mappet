package mchorse.mappet.api.scripts.user.items;

import mchorse.mappet.api.scripts.user.nbt.INBTCompound;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * This interface represents an item stack
 */
public interface IScriptItemStack
{
    /**
     * Get Minecraft item stack instance. <b>BEWARE:</b> you need to know the MCP
     * mappings in order to directly call methods on this instance!
     */
    public ItemStack getMinecraftItemStack();

    /**
     * Whether this item is empty.
     */
    public boolean isEmpty();

    /**
     * Get item stack's item.
     */
    public IScriptItem getItem();

    /**
     * Get a copy of item stack.
     */
    public IScriptItemStack copy();

    /**
     * Get item stack's maximum size.
     */
    public int getMaxCount();

    /**
     * Get item stack's count.
     */
    public int getCount();

    /**
     * Set item stack's count.
     */
    public void setCount(int count);

    /**
     * Get item stack's meta.
     */
    public int getMeta();

    /**
     * Set item stack's meta.
     */
    public void setMeta(int meta);

    /**
     * Check whether an item stack has an NBT compound tag.
     */
    public boolean hasData();

    /**
     * Get item stack's NBT compound tag.
     */
    public INBTCompound getData();

    /**
     * Replace item stack's NBT compound tag.
     */
    public void setData(INBTCompound tag);

    /**
     * Serialize item stack to an NBT compound.
     */
    public INBTCompound serialize();

    /**
     * Gets display name of the item stack.
     */
    String getDisplayName();

    /**
     * Sets display name of the item stack.
     */
    void setDisplayName(String name);

    /**
     * Gets lore of the item stack.
     */
    String getLore(int index);

    /**
     * Gets all lore lines of the item stack as a list.
     */
    List<String> getLoreList();

    /**
     * Sets lore of the item stack.
     */
    void setLore(int index, String lore);

    /**
     * Adds a lore line to the item stack.
     */
    void addLore(String lore);

    /**
     * Removes all lore lines from the item stack.
     */
    void clearAllLores();

    /**
     * Removes a lore line from the item stack.
     */
    void clearLore(int index);

    /**
     * Clears all enchantments from the item stack.
     */
    void clearAllEnchantments();

    /**
     * Gets a list of all blocks the item stack can destroy.
     */
    List<String> getCanDestroyBlocks();

    /**
     * Adds a block that the item stack can destroy.
     */
    void addCanDestroyBlock(String block);

    /**
     * Clears all blocks that the item stack can destroy.
     */
    void clearAllCanDestroyBlocks();

    /**
     * Clears a block that the item stack can destroy.
     */
    void clearCanDestroyBlock(String block);

    /**
     * Gets a list of all blocks the item stack can place on.
     */
    List<String> getCanPlaceOnBlocks();

    /**
     * Adds a block that the item stack can place on.
     */
    void addCanPlaceOnBlock(String block);

    /**
     * Clears all blocks that the item stack can place on.
     */
    void clearAllCanPlaceOnBlocks();

    /**
     * Clears a block that the item stack can place on.
     */
    void clearCanPlaceOnBlock(String block);

    /**
     * Gets repair cost of the item stack.
     */
    int getRepairCost();

    /**
     * Sets repair cost of the item stack.
     */
    void setRepairCost(int cost);

    /**
     * Gets if an item stack is unbreakable.
     */
    boolean isUnbreakable();

    /**
     * Sets whether an item stack is unbreakable or not.
     */
    void setUnbreakable(boolean unbreakable);
}