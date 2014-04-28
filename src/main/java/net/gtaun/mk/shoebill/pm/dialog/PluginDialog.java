/**
 * Copyright (C) 2012-2014 MK124
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.gtaun.mk.shoebill.pm.dialog;

import net.gtaun.shoebill.common.ConfigurablePlugin;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.common.dialog.ListDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.util.event.EventManager;

public class PluginDialog
{
	public static ListDialog create(Player player, EventManager eventManager, AbstractDialog parent, Plugin plugin)
	{
		Class<? extends Plugin> clazz = plugin.getClass();
		String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" + Color.WHITE.toEmbeddingString() : Color.RED.toEmbeddingString() + "[D]" + Color.WHITE.toEmbeddingString();
		String pluginName = clazz.getSimpleName();
		String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")" + Color.WHITE.toEmbeddingString();
		String pluginFullName = pluginName + " " + packageName;
		String item = enableMark + " " + pluginFullName;
		
		return ListDialog.create(player, eventManager)
			.parentDialog(parent)
			.caption((d) -> "Plugin: " + item)
			
			.item("Enable", () -> !plugin.isEnabled(), (i) ->
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
				
				i.getCurrentDialog().show();
			})
			
			.item("Disable", () -> plugin.isEnabled(), (i) ->
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
				
				i.getCurrentDialog().show();
			})
			
			.item("Re-enable", () -> plugin.isEnabled(), (i) ->
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
				
				i.getCurrentDialog().show();
			})
			
			.item("Configure", () -> plugin instanceof ConfigurablePlugin, (i) ->
			{
				ConfigurablePlugin wlPlugin = (ConfigurablePlugin) plugin;
				player.sendMessage(Color.WHITE, "[MKPM] " + item + " Configuring...");
				wlPlugin.configure(player);
			})
			
			.onClickCancel((d) -> d.showParentDialog())
			.build();
	}
}
