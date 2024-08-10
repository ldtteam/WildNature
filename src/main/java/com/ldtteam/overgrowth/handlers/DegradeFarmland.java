package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Degrades farmland.
 */
public class DegradeFarmland extends AbstractTransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.FARMLAND;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return getCachedSetting() != 0 && worldTick % getCachedSetting() == 0;
    }

    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().farmlanddegration;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockState upState = Utils.getBlockState(chunk, relativePos.above(), chunkSection);
        if (upState.isAir() || chunk.getLevel().getRandom().nextInt(100) < 1)
        {
            final BlockState hereState = Utils.getBlockState(chunk, relativePos, chunkSection);

            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos);
            FarmBlock.turnToDirt(null, hereState, chunk.getLevel(), worldPos);
        }
        else if (upState.getBlock() instanceof CropBlock)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos.above());

            if (chunk.getLevel().getRandom().nextInt(100) < 10)
            {
                chunk.getLevel().destroyBlock(worldPos, true);
            }
            else
            {
                chunk.getLevel().setBlock(worldPos, upState.getBlock().defaultBlockState(), 3);
            }
        }
    }
}
