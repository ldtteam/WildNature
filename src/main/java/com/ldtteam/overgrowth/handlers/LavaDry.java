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
 * Lava dries up.
 */
public class LavaDry extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().drylava;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.LAVA;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return getCachedSetting() != 0 && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        int lavaCount = 0;
        for (final Direction direction : Direction.values())
        {
            final BlockState relativeState = Utils.getBlockState(chunk, relativePos.relative(direction), chunkSection);
            if (relativeState.getBlock() == Blocks.LAVA || relativeState.getBlock() == Blocks.NETHERRACK)
            {
                lavaCount++;
            }
        }

        if (lavaCount < 5)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos);

            chunk.getLevel().setBlock(worldPos, Blocks.COBBLESTONE.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
