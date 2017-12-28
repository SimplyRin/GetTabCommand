package net.simplyrin.gettabcommand;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.simplyrin.config.Config;

public class BungeeGetTabCommand extends Plugin implements Listener {

	private static BungeeGetTabCommand plugin;
	private static Configuration config;

	@Override
	public void onEnable() {
		plugin = this;
		plugin.getProxy().getPluginManager().registerListener(this, this);

		File folder = plugin.getDataFolder();
		if(!folder.exists()) {
			folder.mkdir();
		}

		File file = new File(folder, "config.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			config = Config.getConfig(file);

			config.set("Cancel", false);
			config.set("Bypass-List", "b0bb65a2-832f-4a5d-854e-873b7c4522ed");

			Config.saveConfig(config, file);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCommand(TabCompleteEvent event) {
		String message = event.getCursor();
		ProxiedPlayer player = plugin.getProxy().getPlayer(event.getSender().toString());

		if(message.startsWith("/")) {
			if(player.hasPermission("gettabcommand.bypass")) {
				return;
			}

			if(config.getBoolean("Cancel")) {
				event.setCancelled(true);
			}

			plugin.getProxy().getConsole().sendMessage(getPrefix() + "§b" + player.getName() + "@" + player.getServer().getInfo().getName() + ": " + message);

			for(ProxiedPlayer p : plugin.getProxy().getPlayers()) {
				if(p.hasPermission("gettabcommand.show")) {
					p.sendMessage(getPrefix() + "§b" + player.getName() + "@" + player.getServer().getInfo().getName() + ": " + message);
				}
			}
		}
	}

	public static String getPrefix() {
		return "§7[§cGetTabCommand§7] §r";
	}

}
