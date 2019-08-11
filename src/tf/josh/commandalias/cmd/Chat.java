package tf.josh.commandalias.cmd;

import java.util.Arrays;
import java.util.Collections;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;


public class Chat extends BukkitCommand {
    public Chat() {
        super("c", "chat", "/c", Collections.singletonList(""));
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {

            StringBuilder builder = new StringBuilder();
            byte b;
            int i;
            for (i = args.length, b = 0; b < i; ) {
                String s = args[b];
                builder.append(String.valueOf(s)).append(" ");
                b++;
            }

            Player p = (Player) sender;
            p.chat(builder.toString());
        }
        return true;
    }
}