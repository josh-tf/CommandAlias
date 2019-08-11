package tf.josh.commandalias.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

import tf.josh.commandalias.cmd.Chat;
import tf.josh.commandalias.cmd.Commands;
import org.bukkit.command.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

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
    	
    	for (String a : Objects.requireNonNull(getConfig().getConfigurationSection("cmds")).getKeys(false)) {

    		ArrayList<String> aliascmds = (ArrayList)getConfig().getStringList("cmds." + a + ".aliases");

    		registerCmd(a,
    				new Commands(a,
    						getConfig().getString("commands." + a + ".usage"),
							aliascmds, // array of available aliases
    						getConfig().getString("commands." + a + ".description"), 
    						getConfig().getBoolean("commands." + a + ".ingame"), 
    						getConfig().getString("commands." + a + ".permission"), this)
    				);
    	}
		registerCmd("c", new Chat());
    }
    	
    	private void registerCmd(String name, Command cmd) {
    		Field bukkitClassMgr;
    		
    		try {

    			bukkitClassMgr = Bukkit.getServer().getClass().getDeclaredField("commandMap");
    			bukkitClassMgr.setAccessible(true);
    		} catch (NoSuchFieldException e) {
    			bukkitClassMgr = null;
    			e.printStackTrace();
    		}
    		try {
				assert bukkitClassMgr != null;
				CommandMap cmdMap = (CommandMap)bukkitClassMgr.get(getServer());
    			cmdMap.register(name,  cmd);
    		} catch (IllegalAccessException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
