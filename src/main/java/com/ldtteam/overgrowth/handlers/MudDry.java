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
 * Mud dries up
 */
public class MudDry extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().drymud;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.MUD;
    }

    @Override
    public boolean ready(final long worldTick, final LevelChunk chunk)
    {
        return getCachedSetting() != 0 && !chunk.getLevel().isRaining() && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        int waterCount = 0;
        for (final Direction direction : Direction.values())
        {
            final BlockState relativeState = Utils.getBlockState(chunk, relativePos.relative(direction), chunkSection);
            if (relativeState.getBlock() == Blocks.WATER || relativeState.getBlock() == Blocks.MUD)
            {
                waterCount++;
            }
        }

        if (waterCount < 5)
        {
            final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos);

            chunk.getLevel().setBlock(worldPos, Blocks.DIRT.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
