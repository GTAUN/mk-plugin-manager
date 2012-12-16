package net.gtaun.wl.component.dialog;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.common.WlPlugin;
import net.gtaun.wl.common.dialog.AbstractListDialog;

public class WlPluginDialog extends AbstractListDialog
{
	private WlPlugin plugin;
	
	
	public WlPluginDialog(WlPlugin plugin, Player player, Shoebill shoebill, EventManager eventManager)
	{
		super(player, shoebill, eventManager);
		this.plugin = plugin;
	}
	
	@Override
	public void show()
	{
		Class<? extends Plugin> clazz = plugin.getClass();
		String enableMark = plugin.isEnabled() ? Color.GREEN.toEmbeddingString() + "[E]" : Color.RED.toEmbeddingString() + "[D]";
		final String pluginName = clazz.getSimpleName() + Color.GRAY.toEmbeddingString() + " (" + clazz.getPackage().getName() + ")";
		final String packageName = Color.GRAY.toEmbeddingString()  + "(" + clazz.getPackage().getName() + ")";
		final String item = enableMark + " " + pluginName + " " + packageName;
		setCaption("Plugin - " + item);
		
		dialogListItems.clear();
		
		if (plugin.isEnabled() == false) dialogListItems.add(new DialogListItem("Enable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.enable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginName + " enabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[CM] " + pluginName + " enable failed.");
				}

				destroy();
			}
		});
		
		if (plugin.isEnabled()) dialogListItems.add(new DialogListItem("Disable")
		{
			@Override
			public void onItemSelect()
			{
				try
				{
					plugin.disable();
					player.sendMessage(Color.WHITE, "[CM] " + pluginName + " disabled.");
				}
				catch (Throwable e)
				{
					e.printStackTrace();
					player.sendMessage(Color.WHITE, "[CM] " + pluginName + " disable failed.");
				}
				
				destroy();
			}
		});
		
		dialogListItems.add(new DialogListItem("Configure")
		{
			@Override
			public void onItemSelect()
			{
				player.sendMessage(Color.WHITE, "[CM] " + pluginName + " configuring...");
				plugin.configure(player);
				destroy();
			}
		});
		
		super.show();
	}
}
