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

package net.gtaun.mk.shoebill.pm.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.gtaun.mk.shoebill.pm.PluginManagerPlugin;
import net.gtaun.mk.shoebill.pm.PluginManagerService;
import net.gtaun.mk.shoebill.pm.dialog.PluginListDialog;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManagerNode;

public class PluginManagerServiceImpl implements PluginManagerService
{
	private final EventManagerNode eventManager;
	
	private final PluginManagerPlugin plugin;

	private boolean isCommandEnabled = true;
	private String commandOperation = "/pm";
	
	
	public PluginManagerServiceImpl(EventManager rootEventManager, PluginManagerPlugin plugin)
	{
		this.eventManager = rootEventManager.createChildNode();
		this.plugin = plugin;
		
		initialize();
	}
	
	private void initialize()
	{
		eventManager.registerHandler(PlayerCommandEvent.class, (e) ->
		{
			if (isCommandEnabled == false) return;
			
			Player player = e.getPlayer();
			
			String command = e.getCommand();
			String[] splits = command.split(" ", 2);
			
			String operation = splits[0].toLowerCase();
			Queue<String> args = new LinkedList<>();
			
			if (splits.length > 1)
			{
				String[] argsArray = splits[1].split(" ");
				args.addAll(Arrays.asList(argsArray));
			}
			
			if (operation.equals(commandOperation))
			{
				if (player.isAdmin() == false)
				{
					player.sendMessage(Color.RED, "You are not authorized to use this command.");
					e.setProcessed();
					return;
				}
				
				showPluginManagerDialog(player);
				e.setProcessed();
			}
		});
	}
	
	public void uninitialize()
	{
		eventManager.cancelAll();
	}

	@Override
	public void setCommandEnabled(boolean enable)
	{
		isCommandEnabled = enable;
	}
	
	@Override
	public void setCommandOperation(String op)
	{
		commandOperation = op;
	}
	
	@Override
	public void showPluginManagerDialog(Player player)
	{
		new PluginListDialog(player, eventManager, null, plugin).show();
	}
}
