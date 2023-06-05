package mchorse.mappet.api.scripts.user;

import mchorse.mappet.api.scripts.user.data.ScriptVector;
import mchorse.mappet.api.scripts.user.entities.IScriptEntity;
import net.minecraft.util.math.RayTraceResult;

/**
 * This interface represents a ray tracing result.
 *
 * <pre>{@code
 * fun main(c: IScriptEvent) {
 *     val result : IScriptRayTrace = c.getSubject().rayTrace(32.0);
 *
 *     if (result.isBlock()) {
 *         // Ray hit a block, so we'll change the block to diamond block
 *         val pos : ScriptVector = result.getBlock();
 *
 *         c.getWorld().setBlock(mappet.createBlockState("minecraft:diamond_block"), pos.x.toInt(), pos.y.toInt(), pos.z.toInt())
 *     } else if (result.isEntity()) {
 *         // Ray hit an entity, so we'll throw it in the air
 *         result.getEntity().setMotion(0.0, 1.0, 0.0)
 *     } else // if (result.isMissed())
 *     {
 *         c.send("Good luck next time!")
 *     }
 * }
 * }</pre>
 */
public interface IScriptRayTrace
{
    /**
     * Get Minecraft ray trace result. <b>BEWARE:</b> you need to know the MCP
     * mappings in order to directly call methods on this instance!
     */
    public RayTraceResult getMinecraftRayTraceResult();

    /**
     * Check whether this ray trace result didn't capture anything.
     */
    public boolean isMissed();

    /**
     * Check whether this ray trace result hit a block.
     */
    public boolean isBlock();

    /**
     * Check whether this ray trace result hit an entity.
     */
    public boolean isEntity();

    /**
     * Get entity that was captured by this ray trace result (it can be null).
     */
    public IScriptEntity getEntity();

    /**
     * Get block position it hit.
     */
    public ScriptVector getBlock();

    /**
     * Get precise position where it hit.
     */
    public ScriptVector getHitPosition();
}