package com.ldtteam.overgrowth;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityHandling
{
    /**
     * Stores position to count.
     */
    private static Object2IntLinkedOpenHashMap<BlockPos> positionMapping = new Object2IntLinkedOpenHashMap<>();

    @SubscribeEvent
    public static void onEntityTick(final LivingEvent.LivingTickEvent event)
    {
        if (event.getEntity().getLevel().isClientSide)
        {
            return;
        }

        if (event.getEntity().tickCount % 50 == 0)
        {
            final BlockPos pos = new BlockPos(event.getEntity().position().x, event.getEntity().getBoundingBox().minY - 0.5000001D, event.getEntity().position().z);
            final BlockState state = event.getEntity().getLevel().getBlockState(pos);
            if (state.getBlock() == Blocks.GRASS_BLOCK)
            {
                final int result = positionMapping.addTo(pos, 1);
                if (result > 25)
                {
                    event.getEntity().getLevel().setBlock(pos, Blocks.DIRT_PATH.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                }
            }
            else if (state.getBlock() == Blocks.SAND)
            {
                final int result = positionMapping.addTo(pos, 1);
                if (result > 25)
                {
                    event.getEntity().getLevel().setBlock(pos, Blocks.SANDSTONE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                }
            }
        }
    }
}
