package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Lava dries up.
 */
public class SandToSandstone extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().anvilsand;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.ANVIL;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return getCachedSetting() != 0 && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockState relativeState = Utils.getBlockState(chunk, relativePos.below(), chunkSection);
        if (relativeState.getBlock() == Blocks.SAND)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos).below();

            chunk.getLevel().setBlock(worldPos, Blocks.SANDSTONE.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
        else if (relativeState.getBlock() == Blocks.RED_SAND)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos).below();

            chunk.getLevel().setBlock(worldPos, Blocks.RED_SANDSTONE.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
