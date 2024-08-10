package com.ldtteam.overgrowth.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Mod server configuration. Loaded serverside, synced on connection.
 */
public class ServerConfiguration extends AbstractConfiguration
{
    /*  -------------------------------------------------------------------------------- *
     *  ------------------- ######## Main Settings ######## ------------------- *
     *  -------------------------------------------------------------------------------- */

    public final ModConfigSpec.IntValue globalspeed;
    public final ModConfigSpec.IntValue byetorch;
    public final ModConfigSpec.IntValue coastdecline;
    public final ModConfigSpec.IntValue lavadegration;
    public final ModConfigSpec.IntValue farmlanddegration;
    public final ModConfigSpec.IntValue mushrooms;
    public final ModConfigSpec.IntValue growplants;
    public final ModConfigSpec.IntValue underwaterplants;
    public final ModConfigSpec.IntValue drylava;
    public final ModConfigSpec.IntValue mossyblocks;
    public final ModConfigSpec.IntValue drymud;
    public final ModConfigSpec.IntValue spreadmud;
    public final ModConfigSpec.IntValue byecampfire;
    public final ModConfigSpec.IntValue anvilsand;
    public final ModConfigSpec.IntValue spiderwebs;
    public final ModConfigSpec.IntValue byepath;
    public final ModConfigSpec.IntValue createpath;
    public final ModConfigSpec.IntValue entityspawn;
    public final ModConfigSpec.IntValue spreadnylium;
    public final ModConfigSpec.BooleanValue caveentities;

    /**
     * Builds server configuration.
     *
     * @param builder config builder
     */
    public ServerConfiguration(final ModConfigSpec.Builder builder)
    {
        createCategory(builder, "main");

        globalspeed = defineInteger(builder, "globalspeed", "In general how often the individual handlers are ticked. 0 to turn it off.", "Global Update-rate", 80, 0, 1000);
        byetorch = defineInteger(builder, "byetorch", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Torch falling handling", 19, 0, 1000);
        coastdecline = defineInteger(builder, "coastdecline", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Coast decline handling", 18, 0, 1000);
        lavadegration = defineInteger(builder, "lavadegration", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Lava Degradation handling", 17, 0, 1000);
        farmlanddegration = defineInteger(builder, "farmlanddegration", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Farmland Degradation handling", 16, 0, 1000);
        mushrooms = defineInteger(builder, "mushrooms", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Mushroom handling", 23, 0, 1000);
        growplants = defineInteger(builder, "growplants", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Plants growing on Grass handling", 16, 0, 1000);
        underwaterplants = defineInteger(builder, "underwaterplants", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Underwater Plant handling", 15, 0, 1000);
        drylava = defineInteger(builder, "drylava", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Lava Drying handling", 17, 0, 1000);
        mossyblocks = defineInteger(builder, "mossyblocks", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Mossy Blocks handling", 14, 0, 1000);
        drymud = defineInteger(builder, "drymud", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Mud Dry handling", 7, 0, 1000);
        spreadmud = defineInteger(builder, "spreadmud", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Mud during Rain Spread handling", 64, 0, 1000);
        byecampfire = defineInteger(builder, "byecampfire", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Campfire going out handling", 11, 0, 1000);
        anvilsand = defineInteger(builder, "anvilsand", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Anvil on Sand to Sandstone handling", 13, 0, 1000);
        spiderwebs = defineInteger(builder, "spiderwebs", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Spiderweb handling", 15, 0, 1000);
        byepath = defineInteger(builder, "byepath", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Grasspath decay handling", 29, 0, 1000);
        entityspawn = defineInteger(builder, "entityspawn", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Entity Spawning handling", 23, 0, 1000);
        spreadnylium = defineInteger(builder, "spreadnylium", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Nylium Spread handling", 24, 0, 1000);

        createpath = defineInteger(builder, "createpath", "The amount of times at a global rate this is ticked. 0 to turn it off.", "Entities leaving handling", 100, 0, 1000);

        caveentities = defineBoolean(builder, "caveentities", "Allow neutral animals to spawn in caves.", "Allow Cave Entities", false);

        finishCategory(builder);
    }
}
