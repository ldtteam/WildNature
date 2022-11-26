package com.ldtteam.wildnature;

import com.ldtteam.wildnature.handlers.GrowPlantsOnGrassHandler;
import com.ldtteam.wildnature.handlers.ITransformationHandler;
import com.ldtteam.wildnature.handlers.MossyBlocksDegradation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("wildnature")
public class WildNature
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "wildnature";

    public WildNature()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(this.getClass());
        ITransformationHandler.HANDLERS.add(new GrowPlantsOnGrassHandler());
        ITransformationHandler.HANDLERS.add(new MossyBlocksDegradation());
    }
}
