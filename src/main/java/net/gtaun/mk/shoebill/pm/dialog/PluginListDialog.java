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

import java.util.Collection;

import net.gtaun.mk.shoebill.pm.PluginManagerPlugin;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.common.dialog.AbstractMsgboxDialog;
import net.gtaun.shoebill.common.dialog.AbstractPageListDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.shoebill.resource.ResourceDescription;
import net.gtaun.shoebill.resource.ResourceManager;
import net.gtaun.util.event.EventManager;

/**
 * 插件列表对话框。
 * 
 * @author MK124
 */
public class PluginListDialog extends AbstractPageListDialog
{
	private final PluginManagerPlugin plugin;
	
	
	public PluginListDialog(Player player, Shoebill shoebill, EventManager eventManager, AbstractDialog parentDialog, PluginManagerPlugin plugin)
	{
		super(player, shoebill, eventManager, parentDialog);
		this.plugin = plugin;
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
		
		caption = "MK's Plugin Manager: Plugin List (Enabled: " + enabledPlugins + ", Total: " + plugins.size() + ")";
		
		dialogListItems.clear();
		for (final Plugin plugin : plugins)
		{
			Class<? extends Plugin> clazz = plugin.getClass();
			String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" + Color.WHITE.toEmbeddingString() : Color.RED.toEmbeddingString() + "[D]" + Color.WHITE.toEmbeddingString();
			String pluginName = clazz.getSimpleName();
			String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")" + Color.WHITE.toEmbeddingString();
			
			String item = "Plugin: " + enableMark + " " + pluginName + " " + packageName;
			
			dialogListItems.add(new DialogListItem(item)
			{
				@Override
				public void onItemSelect()
				{
					new PluginDialog(plugin, player, shoebill, rootEventManager, PluginListDialog.this).show();
				}
			});
		}
		
		dialogListItems.add(new DialogListItem("About MK's PluginManager")
		{
			@Override
			public void onItemSelect()
			{
				ResourceDescription desc = plugin.getDescription();
				
				final String msgboxCaption = "About MK's PluginManager";
				String format =
					"Version: %1$s (Build %2$d)\n" +
					"Build date: %3$s\n" +
					"Developer: mk124\n\n" +
					"Copyright (C) 2012-2013 MK124\n\n" +
					"This program is free software; you can redistribute it and/or\n" +
					"modify it under the terms of the GNU General Public Licenseas\n" +
					"published by the Free Software Foundation; either version 2\n" +
					"of the License, or (at your option) any later version.";
				
				final String message = String.format(format, desc.getVersion(), desc.getBuildNumber(), desc.getBuildDate());
				
				new AbstractMsgboxDialog(player, shoebill, eventManager, PluginListDialog.this)
				{
					@Override
					public void show()
					{
						this.caption = msgboxCaption;
						show(message);
					}
					
					protected void onClickOk()
					{
						showParentDialog();
					}
					
					protected void onClickCancel()
					{
						showParentDialog();
					}
				}.show();
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
