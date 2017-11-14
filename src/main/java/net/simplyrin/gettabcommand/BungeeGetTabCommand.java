package net.simplyrin.gettabcommand;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeeGetTabCommand extends Plugin implements Listener {

	private static BungeeGetTabCommand plugin;

	@Override
	public void onEnable() {
		plugin = this;
		plugin.getProxy().getPluginManager().registerListener(this, this);
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
