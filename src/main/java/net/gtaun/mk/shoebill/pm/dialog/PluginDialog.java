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

import net.gtaun.mk.common.ConfigurablePlugin;
import net.gtaun.mk.common.dialog.AbstractListDialog;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.util.event.EventManager;

/**
 * 插件列表对话框。
 * 
 * @author MK124
 */
public class PluginDialog extends AbstractListDialog
{
	private Plugin plugin;
	
	
	public PluginDialog(PluginListDialog pluginListDialog, Plugin plugin, Player player, Shoebill shoebill, EventManager eventManager)
	{
		super(player, shoebill, eventManager);
		this.plugin = plugin;
	}
	
	@Override
	public void show()
	{
		Class<? extends Plugin> clazz = plugin.getClass();
		String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" + Color.WHITE.toEmbeddingString() : Color.RED.toEmbeddingString() + "[D]" + Color.WHITE.toEmbeddingString();
		final String pluginName = clazz.getSimpleName();
		final String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")" + Color.WHITE.toEmbeddingString();
		final String pluginFullName = pluginName + " " + packageName;
		final String item = enableMark + " " + pluginFullName;
		setCaption("Plugin - " + item);
		
		dialogListItems.clear();
		
		if (plugin.isEnabled() == false) dialogListItems.add(new DialogListItem("Enable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.enable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " enabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " enable failed.");
				}
				
				new PluginListDialog(player, shoebill, rootEventManager).show(); 
				destroy();
			}
		});
		
		if (plugin.isEnabled()) dialogListItems.add(new DialogListItem("Disable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.disable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " disabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " disable failed.");
				}

				new PluginListDialog(player, shoebill, rootEventManager).show(); 
				destroy();
			}
		});
		
		if (plugin.isEnabled()) dialogListItems.add(new DialogListItem("Restart")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.disable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " disabled.");
					
					plugin.enable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " enabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[CM] " + pluginFullName + " restart failed.");
				}

				new PluginListDialog(player, shoebill, rootEventManager).show(); 
				destroy();
			}
		});
		
		if (plugin instanceof ConfigurablePlugin) dialogListItems.add(new DialogListItem("Configure")
		{
			@Override
			public void onItemSelect()
			{
				ConfigurablePlugin wlPlugin = (ConfigurablePlugin) plugin;
				player.sendMessage(Color.WHITE, "[CM] " + item + " configuring...");
				wlPlugin.configure(player);
				destroy();
			}
		});
		
		super.show();
	}
}
