package tf.josh.commandalias.cmd;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class Chat
        extends BukkitCommand {
    public Chat() {
        super("c", "chat", "/c", Arrays.asList(new String[]{""}));
    }
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            StringBuilder builder = new StringBuilder();
            byte b;
            int i;
            String[] arrayOfString;
            for (i = arrayOfString = args.length, b = 0; b < i; ) {
                String s = arrayOfString[b];
                builder.append(String.valueOf(s) + " ");
                b++;
            }
            Player p = (Player) sender;
            p.chat(builder.toString());
        }
        return true;
    }
}