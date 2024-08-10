package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Nylium spreads
 */
public class NyliumSpread extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().drymud;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.NETHERRACK;
    }

    @Override
    public boolean ready(final long worldTick, final LevelChunk chunk)
    {
        return getCachedSetting() != 0 && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockState aboveState = Utils.getBlockState(chunk, relativePos.above(), chunkSection);
        if (aboveState.isSolid())
        {
            return;
        }

        BlockState spreadState = null;
        for (final Direction direction : Direction.Plane.HORIZONTAL)
        {
            final BlockState relativeState = Utils.getBlockState(chunk, relativePos.relative(direction), chunkSection);
            if (relativeState.getBlock() == Blocks.CRIMSON_NYLIUM || relativeState.getBlock() == Blocks.WARPED_NYLIUM)
            {
                spreadState = relativeState;
                break;
            }
        }

        if (spreadState != null)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos);

            chunk.getLevel().setBlock(worldPos, spreadState, UPDATE_ALL_IMMEDIATE);
        }
    }
}
