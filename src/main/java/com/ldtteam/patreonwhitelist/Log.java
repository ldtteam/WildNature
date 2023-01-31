package com.ldtteam.patreonwhitelist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log
{
    /**
     * Mod logger.
     */
    private static Logger logger = LogManager.getLogger(PatreonWhitelist.MOD_ID);

    /**
     * Private constructor to hide the public one.
     */
    private Log()
    {
        /*
         * Intentionally left empty.
         */
    }

    /**
     * Getter for the minecolonies Logger.
     *
     * @return the logger.
     */
    public static Logger getLogger()
    {
        return logger;
    }
}
