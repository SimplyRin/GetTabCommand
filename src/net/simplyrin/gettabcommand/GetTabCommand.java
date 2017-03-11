package net.simplyrin.gettabcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class GetTabCommand extends JavaPlugin implements Listener {

	ProtocolManager protocolManager;
	GetTabCommand plugin;

	public void onEnable() {
		plugin = this;

		Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§aAuthor: SimplyRin");

		saveDefaultConfig();


		if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
			manager.addPacketListener(new PacketAdapter(plugin, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
		    	public void onPacketReceiving(PacketEvent event) {
		    		Player player = event.getPlayer();

		    		if(player.hasPermission("gettabcommand.bypass")) {
		    			return;
		    		}

		    		if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
		    			String command = event.getPacket().getStrings().read(0);
		    			if(command.startsWith("/")) {

		    				if(getConfig().getBoolean("Cancel")) {
				    			event.setCancelled(true);
				    		}

		    				Bukkit.getServer().getLogger().info("[GetTabCommand] " + player.getName() + ": " + command);
		    				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
		    					if(p.hasPermission("gettabcommand.show")) {
		    						p.sendMessage(getPrefix() + "§b" + player.getName() + ": " + command);
		    					}
		    				}
		    			}
		    		}
		    	}
		    });
		}
	}

	public static String getPrefix() {
		return "§7[§cGetTabCommand§7] §r";
	}
}
