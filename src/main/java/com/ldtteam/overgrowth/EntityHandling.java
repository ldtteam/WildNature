package com.ldtteam.overgrowth;

import com.ldtteam.overgrowth.utils.Utils;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.block.*;
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

    public static int cachedSetting = -1;

    @SubscribeEvent
    public static void onEntityTick(final LivingEvent.LivingTickEvent event)
    {
        if (event.getEntity().level().isClientSide)
        {
            return;
        }

        if (cachedSetting == -1)
        {
            cachedSetting = Overgrowth.config.getServer().createpath.get();
        }

        if (cachedSetting != 0 && event.getEntity().tickCount % cachedSetting == 0 && !(event.getEntity() instanceof Sheep))
        {
            final BlockPos pos = BlockPos.containing(event.getEntity().position().x, event.getEntity().getBoundingBox().minY - 0.5000001D, event.getEntity().position().z);
                final BlockState state = event.getEntity().level().getBlockState(pos);

            if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.PODZOL)
            {
                handlePathForBlock(pos, Blocks.DIRT_PATH.defaultBlockState(), event);
            }
            else if (state.getBlock() == Blocks.SAND)
            {
                handlePathForBlock(pos, Blocks.SANDSTONE.defaultBlockState(), event);
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

    /**
     * Handle this for a given blockstate.
     * @param defaultBlockState
     */
    private static void handlePathForBlock(final BlockPos pos, final BlockState defaultBlockState, final LivingEvent.LivingTickEvent event)
    {
        final int result = positionMapping.addTo(pos, 1);
        boolean adjacentPath = false;
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            if (Utils.getBlockState(event.getEntity().level(), pos.relative(direction)).getBlock() == defaultBlockState.getBlock())
            {
                adjacentPath = true;
            }
        }
        if (result > cachedSetting || (adjacentPath && result > cachedSetting / 2.0))
        {
            final BlockState upState = Utils.getBlockState(event.getEntity().level(), pos.above());
            final Block upBlock = upState.getBlock();
            if (upBlock instanceof SnowLayerBlock || upBlock == Blocks.GRASS || upBlock == Blocks.TALL_GRASS)
            {
                event.getEntity().level().setBlock(pos.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            }
            else if (upState.isAir())
            {
                event.getEntity().level().setBlock(pos, defaultBlockState, Block.UPDATE_ALL_IMMEDIATE);
            }

            positionMapping.remove(pos);
        }
    }
}
