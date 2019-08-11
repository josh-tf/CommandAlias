package tf.josh.commandalias.cmd;

import java.util.List;
import java.util.Objects;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands extends BukkitCommand {

    private String cmdtype = "";
    private String permission = "";
    private Plugin plugin;

    public Commands(String name, String usageMessage, List<String> aliases, String description,  String cmdtype, String permission, Plugin plugin) {
        super(name, description, usageMessage, aliases);
        this.cmdtype = cmdtype;
        this.permission = permission;
        this.plugin = plugin;
    }

    public String rPlaceholder(Player p, String s){
        s = s.replace("{player}", p.getName()); // replace {player} placeholder with players name
        s = s.replace("{world}", Objects.requireNonNull(p.getLocation().getWorld()).getName());  // replace {world} placeholder with players current world
        return s;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {

        // if trying to run a console only command as a player or visa versa
        if(sender instanceof Player && this.cmdtype.equals("console")){
            // they are a player but running a console only command
            sender.sendMessage(ChatColor.RED + "You can only run this command from the console.");
            return true;
        } else if(!(sender instanceof Player) && this.cmdtype.equals("player")) {
            // the are not a player but running a player only command
            sender.sendMessage(ChatColor.RED + "You can only run this command as a player.");
            return true;
        }

        // sender is a player, permissions are not null and they don't have the permission
        if (sender instanceof Player && !this.permission.equals("") && !sender.hasPermission(this.permission)) {
            sender.sendMessage(ChatColor.RED + "No permission to run this command.");
            return true;
        }

        // player only command, runs player and console commands with replace for the placeholders
        if (this.cmdtype.equals("player")) {
                Player p = (Player) sender;
                FileConfiguration config = this.plugin.getConfig(); // load the config
                for (String s : config.getStringList("cmds." + getName() + ".playercmd")) { // loop through player commands
                    p.performCommand(rPlaceholder(p, s)); // exec
                }
                for (String s : config.getStringList("cmds." + getName() + ".consolecmd")) { // loop through console commands
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rPlaceholder(p, s));
                }
        }

        // this is a console command ran from a console or player sender
        if (this.cmdtype.equals("console") || this.cmdtype.equals("both")) {
            FileConfiguration config = this.plugin.getConfig();
            for (String s : config.getStringList("cmds." + getName() + ".consolecmd")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
            }
        }
        return true;
    }
}