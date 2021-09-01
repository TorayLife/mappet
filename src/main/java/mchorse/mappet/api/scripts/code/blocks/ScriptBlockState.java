package mchorse.mappet.api.scripts.code.blocks;

import mchorse.mappet.api.scripts.user.blocks.IScriptBlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class ScriptBlockState implements IScriptBlockState
{
    public static ScriptBlockState AIR = new ScriptBlockState(Blocks.AIR.getDefaultState());

    private IBlockState state;

    public static IScriptBlockState create(IBlockState state)
    {
        if (state == Blocks.AIR.getDefaultState() || state == null)
        {
            return AIR;
        }

        return new ScriptBlockState(state);
    }

    private ScriptBlockState(IBlockState state)
    {
        this.state = state;
    }

    @Override
    public IBlockState getMinecraftBlockState()
    {
        return this.state;
    }

    @Override
    public int getMeta()
    {
        return this.state.getBlock().getMetaFromState(this.state);
    }

    @Override
    public String getBlockId()
    {
        ResourceLocation rl = this.state.getBlock().getRegistryName();

        return rl == null ? "" : rl.toString();
    }

    @Override
    public boolean isSame(IScriptBlockState state)
    {
        return this.state == ((ScriptBlockState) state).state;
    }

    @Override
    public boolean isSameBlock(IScriptBlockState state)
    {
        return this.state.getBlock() == ((ScriptBlockState) state).state.getBlock();
    }

    @Override
    public boolean isAir()
    {
        return this.state.getBlock() == Blocks.AIR;
    }
}