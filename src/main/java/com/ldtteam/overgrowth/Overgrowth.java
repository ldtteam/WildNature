package com.ldtteam.overgrowth;

import com.ldtteam.overgrowth.handlers.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("overgrowth")
public class Overgrowth
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "overgrowth";

    public Overgrowth()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(this.getClass());
        ITransformationHandler.HANDLERS.add(new GrowPlantsOnGrass());
        ITransformationHandler.HANDLERS.add(new MossyBlocksDegradation());
        ITransformationHandler.HANDLERS.add(new GrowWaterPlants());
        ITransformationHandler.HANDLERS.add(new GrowMushrooms());
        ITransformationHandler.HANDLERS.add(new SpiderWebs());
        ITransformationHandler.HANDLERS.add(new ByeTorch());
        ITransformationHandler.HANDLERS.add(new DegradeFarmland());
        ITransformationHandler.HANDLERS.add(new CrackedBlocksDegradation());
        ITransformationHandler.HANDLERS.add(new CoastDecline());
        ITransformationHandler.HANDLERS.add(new LavaDry());
        ITransformationHandler.HANDLERS.add(new NightCampfire());
        ITransformationHandler.HANDLERS.add(new SandToSandstone());
        ITransformationHandler.HANDLERS.add(new UndoPath());
        ITransformationHandler.HANDLERS.add(new MuddyRain());
        ITransformationHandler.HANDLERS.add(new MudDry());

        ITransformationHandler.HANDLERS.add(new SpawnEntities());

        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(EntityHandling.class);
    }
}
