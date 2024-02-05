package com.ldtteam.overgrowth.mixin;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.configuration.ServerConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterAnimal.class)
public abstract class WaterAnimalMixin
{
    @Inject(method = "checkSurfaceWaterAnimalSpawnRules", at = @At("RETURN"), cancellable = true)
    private static void checkSurfaceWaterAnimalSpawnRules(
      final EntityType<? extends WaterAnimal> p_218283_,
      final LevelAccessor p_218284_,
      final MobSpawnType p_218285_,
      final BlockPos p_218286_,
      final RandomSource p_218287_,
      final CallbackInfoReturnable<Boolean> cir)
    {
        if (Overgrowth.config.getServer().caveentities.get())
        {
            int i = p_218284_.getSeaLevel();
            int j = i - 70;
            cir.setReturnValue(p_218286_.getY() >= j && p_218286_.getY() <= i && p_218284_.getFluidState(p_218286_.below()).is(FluidTags.WATER) && p_218284_.getBlockState(p_218286_.above()).is(Blocks.WATER));
        }
    }
}
