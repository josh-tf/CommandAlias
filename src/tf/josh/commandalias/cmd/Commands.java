package tf.josh.commandalias.cmd;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands
        extends BukkitCommand {
    private boolean playerCommand = false;
    private String permission = "";

    private Plugin plugin;

    public Commands(String name, String usageMessage, List<String> aliases, String description,  boolean playerCommand, String permission, Plugin plugin) {
        super(name, description, usageMessage, aliases);
        this.playerCommand = playerCommand;
        this.permission = permission;
        this.plugin = plugin;
    }


    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission(this.permission) || this.permission.equals("")) {
            if (this.playerCommand) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    FileConfiguration config = this.plugin.getConfig();

                    for (String s : config.getStringList("commands." + getName() + ".playercmd")) {
                        p.performCommand(s);
                    }
                    for (String s : config.getStringList("commands." + getName() + ".consolecmd")) {

                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
                    }
                } else {

                    sender.sendMessage(ChatColor.RED + "You are not a player.");
                }
            } else {

                FileConfiguration config = this.plugin.getConfig();
                if (sender instanceof Player &&
                        config.getStringList("commands." + getName() + ".playercmd") != null &&
                        !config.getStringList("commands." + getName() + ".playercmd").isEmpty()) {

                    Player p = (Player) sender;
                    for (String s : config.getStringList("commands." + getName() + ".playercmd")) {
                        System.out.println(s);
                    }
                    for (String s : config.getStringList("commands." + getName() + ".playercmd")) {


                        p.performCommand(s);
                    }
                }


                for (String s : config.getStringList("commands." + getName() + ".consolecmd")) {

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "No permission.");
        }
        return true;
    }
}