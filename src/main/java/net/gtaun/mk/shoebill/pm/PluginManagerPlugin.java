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

package net.gtaun.mk.shoebill.pm;

import net.gtaun.mk.common.ConfigurablePlugin;
import net.gtaun.mk.shoebill.pm.impl.PluginManagerServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件管理器主类。
 * 
 * @author MK124
 */
public class PluginManagerPlugin extends ConfigurablePlugin
{
	public static final Logger LOGGER = LoggerFactory.getLogger(PluginManagerPlugin.class);
	
	
	private PluginManagerServiceImpl service;
	
	
	public PluginManagerPlugin()
	{
		
	}
	
	@Override
	protected void onEnable() throws Throwable
	{
		service = new PluginManagerServiceImpl(getShoebill(), getEventManager());
		registerService(PluginManagerService.class, service);
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " Enabled.");
	}
	
	@Override
	protected void onDisable() throws Throwable
	{
		unregisterService(PluginManagerService.class);
		service.uninitialize();
		service = null;
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " Disabled.");
	}
}
