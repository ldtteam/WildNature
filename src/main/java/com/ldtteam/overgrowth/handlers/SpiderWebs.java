package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Distributes spiderwebs.
 */
public class SpiderWebs extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().spiderwebs;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.is(BlockTags.PLANKS) || state.is(BlockTags.STONE_BRICKS);
    }
    @Override
    public boolean ready(final long worldTick)
    {
        return getCachedSetting() != 0 && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        for (final Direction direction : Direction.values())
        {
            final BlockState relativeState = Utils.getBlockState(chunk, relativePos.relative(direction), chunkSection);
            if (relativeState.isAir())
            {
                for (final Direction otherDir : Direction.values())
                {
                    if (otherDir != direction.getOpposite() && otherDir != Direction.DOWN)
                    {
                        final BlockState otherRelativeState = Utils.getBlockState(chunk, relativePos.relative(direction).relative(otherDir), chunkSection);
                        final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos.relative(direction).relative(otherDir));
                        if (otherRelativeState.isCollisionShapeFullBlock(chunk.getLevel(), worldPos))
                        {
                            for (int i = 0; i <= 3; i++)
                            {
                                final BlockState belowState = Utils.getBlockState(chunk, relativePos.relative(direction).below(i), chunkSection);
                                if (!belowState.isAir())
                                {
                                    return;
                                }
                            }
                            final BlockPos worldSetPos = Utils.getWorldPos(chunk, chunkSection, relativePos.relative(direction));

                            chunk.getLevel().setBlock(worldSetPos, Blocks.COBWEB.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
                            return;
                        }
                    }
                }
            }
        }
    }
}
