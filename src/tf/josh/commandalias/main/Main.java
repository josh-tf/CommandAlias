package tf.josh.commandalias.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Override
    public void onEnable() {
    	console.sendMessage(ChatColor.AQUA+"[CommandAlias] Version something has loaded");

    	getConfig().options().copyDefaults(true);
    	saveDefaultConfig();
    	registerCommands();
    	
    }
    
    @Override
    public void onDisable() {
    }

    private void registerCommands() {
    	
    	for (String c : getConfig().getConfigurationSection("cmds").getKeys(false)) {
    		
    	}
    	
    }
    
}
