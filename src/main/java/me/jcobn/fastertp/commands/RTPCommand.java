package me.jcobn.fastertp.commands;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
