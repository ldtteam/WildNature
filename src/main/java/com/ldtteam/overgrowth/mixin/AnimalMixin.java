package com.ldtteam.overgrowth.mixin;

import com.ldtteam.overgrowth.Overgrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public abstract class AnimalMixin
{
    @Inject(method = "isBrightEnoughToSpawn", at = @At("RETURN"), cancellable = true)
    private static void isBrightEnoughToSpawn(final BlockAndTintGetter p_186210_, final BlockPos p_186211_, final CallbackInfoReturnable<Boolean> cir)
    {
        if (Overgrowth.config.getServer().caveentities.get())
        {
            cir.setReturnValue(true);
        }
    }
}
