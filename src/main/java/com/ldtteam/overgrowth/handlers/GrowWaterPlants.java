package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

/**
 * Grows different type of plants on grass.
 */
public class GrowWaterPlants implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.SEAGRASS;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return worldTick % 200 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection)
    {
        final BlockState state = Utils.getBlockState(chunk, relativePos, chunkSection);
        final LevelChunkSection section = chunk.getSections()[chunkSection];
        final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos);

        if (state.getBlock() == Blocks.SEAGRASS)
        {
            ((SeagrassBlock) state.getBlock()).performBonemeal((ServerLevel) chunk.getLevel(), chunk.getLevel().getRandom(), worldPos, state);
        }
        else
        {
            final BlockState belowState = Utils.getBlockState(chunk, relativePos.below(), chunkSection);

            BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

            if (!belowState.isCollisionShapeFullBlock(chunk.getLevel(), worldPos.below()))
            {
                return;
            }

            Holder<Biome> holder = chunk.getLevel().getBiome(worldPos);
            if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL))
            {
                //todo here we can do wall corals!
                if (i == 0 && p_40635_ != null && p_40635_.getAxis().isHorizontal())
                {
                    blockstate = Registry.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap((p_204098_) -> {
                        return p_204098_.getRandomElement(chunk.getLevel().random);
                    }).map((p_204100_) -> {
                        return p_204100_.value().defaultBlockState();
                    }).orElse(blockstate);
                    if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING))
                    {
                        blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, p_40635_);
                    }
                }
                else if (randomsource.nextInt(4) == 0)
                {
                    blockstate = Registry.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap((p_204091_) -> {
                        return p_204091_.getRandomElement(chunk.getLevel().random);
                    }).map((p_204095_) -> {
                        return p_204095_.value().defaultBlockState();
                    }).orElse(blockstate);
                }
            }

            if (blockstate.is(BlockTags.WALL_CORALS, (p_204093_) -> p_204093_.hasProperty(BaseCoralWallFanBlock.FACING)))
            {
                for (int k = 0; !blockstate.canSurvive(chunk.getLevel(), worldPos.below()) && k < 4; ++k)
                {
                    blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomsource));
                }
            }

            if (blockstate.canSurvive(chunk.getLevel(), worldPos.below()))
            {
                BlockState blockstate1 = chunk.getLevel().getBlockState(worldPos.below());
                if (blockstate1.is(Blocks.WATER) && chunk.getLevel().getFluidState(worldPos.below()).getAmount() == 8)
                {
                    chunk.getLevel().setBlock(worldPos.below(), blockstate, 3);
                }
            }
        }
    }
}
