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

import java.util.Collection;

import net.gtaun.mk.shoebill.pm.PluginManagerPlugin;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.common.dialog.MsgboxDialog;
import net.gtaun.shoebill.common.dialog.PageListDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.shoebill.resource.ResourceDescription;
import net.gtaun.util.event.EventManager;

public class PluginListDialog extends PageListDialog
{
	private final PluginManagerPlugin plugin;
	
	
	public PluginListDialog(Player player, EventManager eventManager, AbstractDialog parentDialog, PluginManagerPlugin plugin)
	{
		super(player, eventManager);
		setParentDialog(parentDialog);
		
		this.plugin = plugin;
		
		setClickCancelHandler((d) -> showParentDialog());
	}
	
	@Override
	public void show()
	{
		Collection<Plugin> plugins = Plugin.get();
		int enabledPlugins = (int) plugins.stream().filter((p) -> p.isEnabled()).count();
		
		setCaption("MK's Plugin Manager: Plugin List (Enabled: " + enabledPlugins + ", Total: " + plugins.size() + ")");
		
		items.clear();
		for (Plugin plugin : plugins)
		{
			Class<? extends Plugin> clazz = plugin.getClass();
			String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" + Color.WHITE.toEmbeddingString() : Color.RED.toEmbeddingString() + "[D]" + Color.WHITE.toEmbeddingString();
			String pluginName = clazz.getSimpleName();
			String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")" + Color.WHITE.toEmbeddingString();
			
			String item = "Plugin: " + enableMark + " " + pluginName + " " + packageName;
			addItem(item, (i) -> PluginDialog.create(player, eventManagerNode, this, plugin).show());
		}
		
		addItem("About MK's PluginManager", (i) ->
		{
			ResourceDescription desc = plugin.getDescription();
			String format =
				"Version: %1$s (Build %2$d)\n" +
				"Build date: %3$s\n" +
				"Developer: mk124\n\n" +
				"Copyright (C) 2012-2013 MK124\n\n" +
				"This program is free software; you can redistribute it and/or\n" +
				"modify it under the terms of the GNU General Public Licenseas\n" +
				"published by the Free Software Foundation; either version 2\n" +
				"of the License, or (at your option) any later version.";
			
			String message = String.format(format, desc.getVersion(), desc.getBuildNumber(), desc.getBuildDate());
			
			MsgboxDialog.create(player, eventManagerNode.getParent())
				.parentDialog(this)
				.caption("About MK's PluginManager")
				.message(message)
				.onClickOk((d) -> show())
				.onClickCancel((d) -> show())
				.build().show();
		});
		
		super.show();
	}
}
