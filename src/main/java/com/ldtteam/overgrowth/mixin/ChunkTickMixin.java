package com.ldtteam.overgrowth.mixin;

import com.ldtteam.overgrowth.Overgrowth;
import com.ldtteam.overgrowth.handlers.ITransformationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ChunkTickMixin
{
    /**
     * Moving random value.
     */
    private int randValue = RandomSource.create().nextInt();
    private static int cachedGlobalSpeed = -1;

    @Inject(method = "tickChunk", at = @At("HEAD"))
    public void overgrowthTickChunk(final LevelChunk chunk, final int pRandomTickSpeed, final CallbackInfo ci)
    {
        int configValue = getCachedGlobalSpeed();
        if (pRandomTickSpeed <= 0 || chunk.getLevel().getGameTime() != 0 && chunk.getLevel().getRandom().nextInt(configValue)+1 != 1)
        {
            return;
        }

        LevelChunkSection[] chunkSections = chunk.getSections();

        for (int sectionId = 0; sectionId < chunkSections.length; sectionId++)
        {
            LevelChunkSection levelchunksection = chunkSections[sectionId];

            for (int times = 0; times < pRandomTickSpeed; times++)
            {
                if (chunk.getLevel().getRandom().nextInt(configValue) + 1 == 1)
                {
                    final BlockPos randomPos = this.overgrowthGetBlockRandomPos();
                    for (ITransformationHandler handler : ITransformationHandler.HANDLERS)
                    {
                        BlockState randomState = levelchunksection.getBlockState(randomPos.getX(), randomPos.getY(), randomPos.getZ());

                        if (handler.transforms(randomState) && handler.ready(chunk.getLevel().getGameTime() + times, chunk))
                        {
                            try
                            {
                                handler.transformBlock(randomPos, chunk, sectionId, randomState);
                            }
                            catch (final Exception ex)
                            {
                                Overgrowth.LOGGER.warn("Oopsy", ex);
                            }
                        }
                    }
                }
            }
        }
    }

    public int getCachedGlobalSpeed()
    {
        if (cachedGlobalSpeed == -1)
        {
            cachedGlobalSpeed = Overgrowth.config.getServer().globalspeed.get();
        }
        return cachedGlobalSpeed;
    }

    /**
     * Calculate a pseudo-random block position based on vanilla.
     *
     * @return the random position.
     */
    private BlockPos overgrowthGetBlockRandomPos()
    {
        this.randValue = this.randValue * 3 + 1013904223;
        int i = this.randValue >> 2;
        return new BlockPos((i & 15), (i >> 16 & 15), (i >> 8 & 15));
    }
}
