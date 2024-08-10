package com.ldtteam.overgrowth.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Mod root configuration.
 */
public class Configuration
{
    /**
     * Loaded serverside, synced on connection
     */
    private final ServerConfiguration serverConfig;

    /**
     * Builds configuration tree.
     */
    public Configuration(final Pair<ServerConfiguration, ModConfigSpec> ser)
    {
        serverConfig = ser.getLeft();
    }

    public ServerConfiguration getServer()
    {
        return serverConfig;
    }
}
