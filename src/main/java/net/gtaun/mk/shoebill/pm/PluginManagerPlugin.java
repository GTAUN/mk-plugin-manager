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

package net.gtaun.mk.shoebill.pm;

import net.gtaun.mk.shoebill.pm.impl.PluginManagerServiceImpl;
import net.gtaun.shoebill.common.ConfigurablePlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		service = new PluginManagerServiceImpl(getEventManager(), this);
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
