package com.ldtteam.overgrowth;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
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

        if (event.getEntity().tickCount % 50 == 0 && !(event.getEntity() instanceof Sheep))
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

        //if (event.getEntity().tickCount % 500 == 0 && event.getEntity().getLevel().random.nextInt(100) <= 0 && event.getEntity() instanceof Animal)
        //{
        //    ((Animal) event.getEntity()).setInLove(null);
        //}

        //if (event.getEntity().tickCount % 500 == 0 && event.getEntity().getLevel().random.nextInt(1000) <= 0 && event.getEntity() instanceof Animal)
        //{
        //    event.getEntity().die(DamageSource.STARVE);
        //}
    }
}
