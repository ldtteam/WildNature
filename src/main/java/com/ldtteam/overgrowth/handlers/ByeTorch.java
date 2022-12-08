package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Torch die.
 */
public class ByeTorch implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.TORCH;
    }
    @Override
    public boolean ready(final long worldTick)
    {
        return worldTick % 19 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockPos worldPos = Utils.getWorldPos(chunk, chunk.getSections()[chunkSection], relativePos);
        chunk.getLevel().destroyBlock(worldPos, false);
    }
}
