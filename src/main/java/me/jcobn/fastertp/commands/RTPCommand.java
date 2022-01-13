package me.jcobn.fastertp.commands;

import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.Permissions;
import me.jcobn.fastertp.file.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class RTPCommand implements TabExecutor {
    /*
     * /rtp [world] [biomes...]
     * /rtp (p | player) <player> [world] [biomes...]
     * */

    private final FasteRTP plugin;

    public RTPCommand(FasteRTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cSender, Command command, String label, String[] args) {
        if (cSender instanceof Player sender) {
            switch (args.length) {
                case 0:
                    if (!Permissions.RTP.check(sender)) {
                        sender.sendMessage(Messages.NO_PERMISSION.getMessage());
                        return true;
                    }
                    //TODO: check if in warmup

                    break;
            }
        } else {

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
