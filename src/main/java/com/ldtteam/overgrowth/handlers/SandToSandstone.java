package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Lava dries up.
 */
public class SandToSandstone implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.ANVIL;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return worldTick % 103 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection)
    {
        final BlockState relativeState = Utils.getBlockState(chunk, relativePos.below(), chunkSection);
        if (relativeState.getBlock() == Blocks.SAND)
        {
            final LevelChunkSection section = chunk.getSections()[chunkSection];
            final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos).below();

            chunk.getLevel().setBlock(worldPos, Blocks.SANDSTONE.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
        else if (relativeState.getBlock() == Blocks.RED_SAND)
        {
            final LevelChunkSection section = chunk.getSections()[chunkSection];
            final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos).below();

            chunk.getLevel().setBlock(worldPos, Blocks.RED_SANDSTONE.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
