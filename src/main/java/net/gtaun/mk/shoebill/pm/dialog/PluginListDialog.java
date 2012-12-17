/**
 * Copyright (C) 2012 MK124
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package net.gtaun.mk.shoebill.pm.dialog;

import java.util.Collection;

import net.gtaun.mk.common.ConfigurablePlugin;
import net.gtaun.mk.common.dialog.AbstractPageListDialog;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.shoebill.resource.ResourceManager;
import net.gtaun.util.event.EventManager;

/**
 * 插件列表对话框。
 * 
 * @author MK124
 */
public class PluginListDialog extends AbstractPageListDialog
{
	public PluginListDialog(Player player, Shoebill shoebill, EventManager eventManager)
	{
		super(player, shoebill, eventManager);
	}
	
	@Override
	public void show()
	{
		ResourceManager resourceManager = shoebill.getResourceManager();
		Collection<Plugin> plugins = resourceManager.getPlugins();
		
		int enabledPlugins = 0;
		for (Plugin plugin : plugins)
		{
			if (plugin.isEnabled()) enabledPlugins++;
		}
		
		setCaption("WL Plugin List - Enabled: " + enabledPlugins + ", Total: " + plugins.size());
		
		dialogListItems.clear();
		for (final Plugin plugin : plugins)
		{
			Class<? extends Plugin> clazz = plugin.getClass();
			String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" + Color.WHITE.toEmbeddingString() : Color.RED.toEmbeddingString() + "[D]" + Color.WHITE.toEmbeddingString();
			String wlPluginMark = plugin instanceof ConfigurablePlugin ? "[WL]" : "";
			String pluginName = clazz.getSimpleName();
			String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")" + Color.WHITE.toEmbeddingString();
			
			String item = enableMark + " " + wlPluginMark + pluginName + " " + packageName;
			
			dialogListItems.add(new DialogListItem(item)
			{
				@Override
				public void onItemSelect()
				{
					new PluginDialog(PluginListDialog.this, plugin, player, shoebill, rootEventManager).show();
					destroy();
				}
			});
		}
		
		super.show();
	}
}
