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
 * Makes grass or dirt randomly to mud in the rain
 */
public class MuddyRain extends AbstractTransformationHandler
{
    @Override
    public ModConfigSpec.IntValue getMatchingSetting()
    {
        return Overgrowth.config.getServer().spreadmud;
    }

    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS_BLOCK;
    }

    @Override
    public boolean ready(final long worldTick, final LevelChunk chunk)
    {
        return getCachedSetting() != 0 && chunk.getLevel().isRaining() && worldTick % getCachedSetting() == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockPos worldPos = Utils.getWorldPos(chunk, chunkSection, relativePos);

        if (chunk.getLevel().canSeeSky(worldPos.above()))
        {
            chunk.getLevel().setBlock(worldPos, Blocks.MUD.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
