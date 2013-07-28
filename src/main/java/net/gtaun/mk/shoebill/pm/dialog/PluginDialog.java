/**
 * Copyright (C) 2012-2013 MK124
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

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.ConfigurablePlugin;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.common.dialog.AbstractListDialog;
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
	
	
	public PluginDialog(Plugin plugin, Player player, Shoebill shoebill, EventManager eventManager, AbstractDialog parentDialog)
	{
		super(player, shoebill, eventManager, parentDialog);
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
		caption = "Plugin: " + item;
		
		dialogListItems.clear();
		
		if (plugin.isEnabled() == false) dialogListItems.add(new DialogListItem("Enable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.enable();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Enabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Enable failed.");
				}
				
				showParentDialog();
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
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Disabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Disable failed.");
				}

				showParentDialog();
			}
		});
		
		if (plugin.isEnabled()) dialogListItems.add(new DialogListItem("Re-enable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.disable();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Disabled.");
					
					plugin.enable();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Re-enabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[MKPM] " + pluginFullName + " Re-enable failed.");
				}

				showParentDialog();
			}
		});
		
		if (plugin instanceof ConfigurablePlugin) dialogListItems.add(new DialogListItem("Configure")
		{
			@Override
			public void onItemSelect()
			{
				ConfigurablePlugin wlPlugin = (ConfigurablePlugin) plugin;
				player.sendMessage(Color.WHITE, "[MKPM] " + item + " Configuring...");
				wlPlugin.configure(player);
			}
		});
		
		super.show();
	}
	
	@Override
	protected void onClickCancel()
	{
		showParentDialog();
	}
}
