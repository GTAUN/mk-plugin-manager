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

package net.gtaun.wl.component.dialog;

import java.util.Collection;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.shoebill.resource.ResourceManager;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.common.WlPlugin;
import net.gtaun.wl.common.dialog.AbstractPageListDialog;

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
		
		dialogListItems.clear();
		for (final Plugin plugin : plugins)
		{
			Class<? extends Plugin> clazz = plugin.getClass();
			String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" : Color.RED.toEmbeddingString() + "[D]";
			String pluginName = clazz.getSimpleName() + Color.GRAY + " (" + clazz.getPackage().getName() + ")";
			String item = enableMark + pluginName;
			
			if (plugin instanceof WlPlugin) dialogListItems.add(new DialogListItem(item)
			{
				@Override
				public void onItemSelect()
				{
					new WlPluginDialog((WlPlugin) plugin, player, shoebill, rootEventManager);
					destroy();
				}
			});
		}
		
		super.show();
	}
}
