package me.jcobn.fastertp.commands;

import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.Permissions;
import me.jcobn.fastertp.file.Messages;
import me.jcobn.fastertp.rtp.RTPOrigin;
import me.jcobn.fastertp.rtp.RTPPlayer;
import me.jcobn.fastertp.util.Time;
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
                    if(plugin.getRtpManager().isPlayerRtping(sender)) {
                        sender.sendMessage(Messages.IN_RTP.getMessage());
                        return true;
                    }
                    if (!Permissions.RTP.check(sender)) {
                        sender.sendMessage(Messages.NO_PERMISSION.getMessage());
                        return true;
                    }
                    if (plugin.getWarmupManager().isInWarmup(sender)) {
                        sender.sendMessage(Messages.IN_WARMUP.getMessage());
                        return true;
                    }
                    if (!plugin.getCooldownManager().userHasCooldown(sender)) {
                        plugin.getRtp().tp(sender, sender, RTPOrigin.COMMAND_SELF, sender.getWorld().getName(), null, plugin.getWarmupManager().shouldWarmup(sender), !plugin.getCooldownManager().exceptionFromCooldown(sender));
                    } else {
                        sender.sendMessage(Messages.IN_COOLDOWN.getMessage().replace("%time%", Time.secondsToHumanReadableTime(plugin.getCooldownManager().getUserCooldown(sender))));

                    }
                    return true;
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
