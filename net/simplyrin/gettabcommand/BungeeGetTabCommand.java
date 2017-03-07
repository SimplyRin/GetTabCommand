package net.simplyrin.gettabcommand;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeeGetTabCommand extends Plugin implements Listener {

	private BungeeGetTabCommand plugin;

	public void onEnable() {
		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCommand(TabCompleteEvent event) {
		String message = event.getCursor();
		ProxiedPlayer player = BungeeCord.getInstance().getPlayer(event.getSender().toString());

		if(player.hasPermission("gettabcommand.bypass")) {
			return;
		}

		BungeeCord.getInstance().getConsole().sendMessage(getPrefix() + "§b" + player.getName() + "@" + player.getServer().getInfo().getName() + ": " + message);

		for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
			if(p.hasPermission("gettabcommand.show")) {
				p.sendMessage(getPrefix() + "§b" + player.getName() + "@" + player.getServer().getInfo().getName() + ": " + message);
			}
		}
	}

	public static String getPrefix() {
		return "§7[§cGetTabCommand§7] §r";
	}
}
