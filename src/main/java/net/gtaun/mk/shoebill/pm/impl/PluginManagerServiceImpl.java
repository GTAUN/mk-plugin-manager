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

package net.gtaun.mk.shoebill.pm.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.gtaun.mk.shoebill.pm.PluginManagerService;
import net.gtaun.mk.shoebill.pm.dialog.PluginListDialog;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;

/**
 * 插件管理器服务实现类。
 * 
 * @author MK124
 */
public class PluginManagerServiceImpl implements PluginManagerService
{
	private final Shoebill shoebill;
	private final ManagedEventManager eventManager;

	private boolean isCommandEnabled = true;
	private String commandOperation = "/pm";
	
	
	public PluginManagerServiceImpl(Shoebill shoebill, EventManager rootEventManager)
	{
		this.shoebill = shoebill;
		eventManager = new ManagedEventManager(rootEventManager);
		
		initialize();
	}
	
	private void initialize()
	{
		eventManager.registerHandler(PlayerCommandEvent.class, playerEventHandler, HandlerPriority.NORMAL);
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
		new PluginListDialog(player, shoebill, eventManager).show();
	}
	
	private PlayerEventHandler playerEventHandler = new PlayerEventHandler()
	{
		protected void onPlayerCommand(PlayerCommandEvent event)
		{
			if (isCommandEnabled == false) return;
			
			Player player = event.getPlayer();
			
			String command = event.getCommand();
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
					event.setProcessed();
					return;
				}
				
				showPluginManagerDialog(player);
				event.setProcessed();
			}
		}
	};
}
