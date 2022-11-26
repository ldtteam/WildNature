package com.ldtteam.overgrowth;

import com.ldtteam.overgrowth.handlers.GrowPlantsOnGrassHandler;
import com.ldtteam.overgrowth.handlers.ITransformationHandler;
import com.ldtteam.overgrowth.handlers.MossyBlocksDegradation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("overgrowth")
public class WildNature
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "overgrowth";

    public WildNature()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(this.getClass());
        ITransformationHandler.HANDLERS.add(new GrowPlantsOnGrassHandler());
        ITransformationHandler.HANDLERS.add(new MossyBlocksDegradation());
    }
}
