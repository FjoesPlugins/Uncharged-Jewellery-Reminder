package com.UnchargedJewelleryReminder;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("glorywealthreminder")
public interface UnchargedJewelleryReminderConfig extends Config
{

    @ConfigItem(
            keyName = "checkGlory",
            name = "Warn for Glory",
            description = "Warn about an uncharged amulet of glory"
    )
    default boolean checkGlory()
    {
        return true;
    }

    @ConfigItem(
            keyName = "checkRingOfWealth",
            name = "Warn for Ring of Wealth",
            description = "Warn about an uncharged ring of wealth"
    )
    default boolean checkRingOfWealth()
    {
        return true;
    }

    @ConfigItem(
            keyName = "checkCombatBracelet",
            name = "Warn for Combat Bracelet",
            description = "Warn about an uncharged combat bracelet"
    )
    default boolean checkCombatBracelet()
    {
        return true;
    }
}