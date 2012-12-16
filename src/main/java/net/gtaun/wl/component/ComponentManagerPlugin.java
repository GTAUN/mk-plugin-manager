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

package net.gtaun.wl.component;

import net.gtaun.wl.common.WlPlugin;
import net.gtaun.wl.component.impl.ComponentManagerServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新未来世界组件管理器插件主类。
 * 
 * @author MK124
 */
public class ComponentManagerPlugin extends WlPlugin
{
	public static final Logger LOGGER = LoggerFactory.getLogger(ComponentManagerPlugin.class);
	
	
	private ComponentManagerServiceImpl service;
	
	
	public ComponentManagerPlugin()
	{
		
	}
	
	@Override
	protected void onEnable() throws Throwable
	{
		service = new ComponentManagerServiceImpl(getShoebill(), getEventManager());
		registerService(ComponentManagerService.class, service);
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " Enabled.");
	}
	
	@Override
	protected void onDisable() throws Throwable
	{
		unregisterService(ComponentManagerService.class);
		service.uninitialize();
		service = null;
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " Disabled.");
	}
}
