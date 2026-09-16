package com.UnchargedJewelleryReminder;

import com.google.common.collect.ImmutableList;
import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Uncharged Jewellery Reminder",
        description = "Warns you if you enter the wilderness without a charged glory or ring of wealth",
        tags = {"wilderness", "glory", "ring of wealth", "pvp"}
)
public class UnchargedJewelleryReminderPlugin extends Plugin
{
    private static final List<Integer> CHARGED_GLORY_IDS = ImmutableList.of(
            19707, 1706, 1708, 1710, 1712, 11976, 11978, 10360, 10358, 10356, 10354, 11966, 11964
    );
    private static final List<Integer> UNCHARGED_GLORY_IDS = ImmutableList.of(1704, 10362, 8283);

    // Ring of wealth (1)-(5) and the imbued ring of wealth (i1)-(i5)
    private static final List<Integer> CHARGED_RING_OF_WEALTH_IDS = ImmutableList.of(
            11988, 11986, 11984, 11982, 11980, 20790, 20789, 20788, 20787, 20786
    );
    // Uncharged ring of wealth and uncharged imbued ring of wealth
    private static final List<Integer> UNCHARGED_RING_OF_WEALTH_IDS = ImmutableList.of(2572, 12785);

    private static final List<Integer> CHARGED_COMBAT_BRACELET_IDS = ImmutableList.of(
            11124, 11122, 11120, 11118, 11974, 11972
    );
    private static final List<Integer> UNCHARGED_COMBAT_BRACELET_IDS = ImmutableList.of(11126);

    @Inject private Client client;
    @Inject private OverlayManager overlayManager;
    @Inject private UnchargedJewelleryReminderOverlay overlay;
    @Inject private UnchargedJewelleryReminderConfig config;

    @Provides
    UnchargedJewelleryReminderConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(UnchargedJewelleryReminderConfig.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        boolean inWilderness = client.getVarbitValue(Varbits.IN_WILDERNESS) == 1;

        if (inWilderness)
        {
            checkForItems();
        }
        else
        {
            overlay.setItems(Collections.emptyList());
        }
    }

    private void checkForItems() {
        boolean chargedGlory = config.checkGlory() && hasAny(CHARGED_GLORY_IDS);
        boolean chargedRow = config.checkRingOfWealth() && hasAny(CHARGED_RING_OF_WEALTH_IDS);
        boolean chargedBracelet = config.checkCombatBracelet() && hasAny(CHARGED_COMBAT_BRACELET_IDS);

        boolean unchargedGlory = config.checkGlory() && hasAny(UNCHARGED_GLORY_IDS);
        boolean unchargedRow = config.checkRingOfWealth() && hasAny(UNCHARGED_RING_OF_WEALTH_IDS);
        boolean unchargedBracelet = config.checkCombatBracelet() && hasAny(UNCHARGED_COMBAT_BRACELET_IDS);

        if (chargedGlory || chargedRow || chargedBracelet) {
            overlay.setItems(Collections.emptyList());
            return;
        }

        List<Integer> icons = new ArrayList<>();
        if (unchargedGlory) {
            icons.add(1704);
        }
        if (unchargedRow) {
            icons.add(2572);
        }
        if (unchargedBracelet) {
            icons.add(11126);
        }

        if (icons.isEmpty()) {
            overlay.setItems(Collections.emptyList());
            return;
        }

        overlay.setItems(icons);
    }

    private boolean hasAny(List<Integer> ids)
    {
        return hasItem(InventoryID.INVENTORY, ids) || hasItem(InventoryID.EQUIPMENT, ids);
    }

    private boolean hasItem(InventoryID inventoryId, List<Integer> ids)
    {
        ItemContainer container = client.getItemContainer(inventoryId);
        if (container == null)
        {
            return false;
        }

        for (Item item : container.getItems())
        {
            if (ids.contains(item.getId()))
            {
                return true;
            }
        }
        return false;
    }
}