package com.UnchargedJewelleryReminder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class UnchargedJewelleryReminderOverlay extends Overlay
{
    private final ItemManager itemManager;

    private List<Integer> itemIds = Collections.emptyList();

    @Inject
    private UnchargedJewelleryReminderOverlay(ItemManager itemManager)
    {
        this.itemManager = itemManager;
        setPosition(OverlayPosition.TOP_CENTER);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public void setItems(List<Integer> itemIds)
    {
        this.itemIds = itemIds;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (itemIds.isEmpty())
        {
            return null;
        }

        int padding = 6;
        int iconSize = 32;
        int spacing = 4;

        int boxWidth = padding * 2 + (iconSize * itemIds.size()) + (spacing * (itemIds.size() - 1));
        int boxHeight = iconSize + padding * 2;

        graphics.setColor(new Color(220, 20, 60, 150));
        graphics.fillRect(0, 0, boxWidth, boxHeight);

        int x = padding;
        for (int itemId : itemIds)
        {
            BufferedImage image = itemManager.getImage(itemId);
            if (image != null)
            {
                graphics.drawImage(image, x, padding, iconSize, iconSize, null);
            }
            x += iconSize + spacing;
        }

        return new Dimension(boxWidth, boxHeight);
    }
}