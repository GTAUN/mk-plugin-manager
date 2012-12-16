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

package net.gtaun.wl.component.impl;

import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.wl.component.ComponentManagerService;

/**
 * 新未来世界组件管理器服务实现类。
 * 
 * @author MK124
 */
public class ComponentManagerServiceImpl implements ComponentManagerService
{
	private final ManagedEventManager eventManager;
	
	
	public ComponentManagerServiceImpl(EventManager rootEventManager)
	{
		eventManager = new ManagedEventManager(rootEventManager);
		
		initialize();
	}
	
	private void initialize()
	{
		
	}
	
	public void uninitialize()
	{
		eventManager.cancelAll();
	}
}
