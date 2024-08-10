package com.ldtteam.overgrowth;

import com.ldtteam.overgrowth.configuration.Configuration;
import com.ldtteam.overgrowth.configuration.ServerConfiguration;
import com.ldtteam.overgrowth.handlers.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("overgrowth")
public class Overgrowth
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "overgrowth";

    /**
     * The config instance.
     */
    public static Configuration config;

    public Overgrowth(final FMLModContainer modContainer, final Dist dist)
    {
        final IEventBus modBus = modContainer.getEventBus();
        final IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.register(EntityHandling.class);

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
        ITransformationHandler.HANDLERS.add(new NyliumSpread());
        ITransformationHandler.HANDLERS.add(new SpawnEntities());

        final Pair<ServerConfiguration, ModConfigSpec> ser = new ModConfigSpec.Builder().configure(ServerConfiguration::new);
        modContainer.registerConfig(ModConfig.Type.SERVER, ser.getRight());
        config = new Configuration(ser);

    }
}
