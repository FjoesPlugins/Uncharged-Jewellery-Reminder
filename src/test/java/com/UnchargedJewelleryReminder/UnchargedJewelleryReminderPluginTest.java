package com.UnchargedJewelleryReminder;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class UnchargedJewelleryReminderPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(UnchargedJewelleryReminderPlugin.class);
		RuneLite.main(args);
	}
}