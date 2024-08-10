package com.ldtteam.overgrowth.handlers;

/**
 * Abstract implementation.
 */
public abstract class AbstractTransformationHandler implements ITransformationHandler
{
    /**
     * Setting cache value.
     */
    public int cachedSetting = -1;

    /**
     * Get the cached setting value.
     * @return the cached setting.
     */
    public int getCachedSetting()
    {
        if (cachedSetting == -1)
        {
            cachedSetting = getMatchingSetting().get();
        }
        return cachedSetting;
    }
}
