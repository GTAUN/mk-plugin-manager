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

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.service.Service;

/**
 * 插件管理器服务接口。
 * 
 * @author MK124
 */
public interface PluginManagerService extends Service
{
	void setCommandEnabled(boolean enable);
	void setCommandOperation(String op);
	
	void showPluginManagerDialog(Player player);
}
