package me.jcobn.fastertp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions {
    RTP("rtp"),
    RTP_OTHER("rtp.others"),
    BYPASS_COOLDOWN("bypass.cooldown"),
    BYPASS_COUNTDOWN("bypass.countdown"),
    FASTERTP("info");

    private final String permPrefix = "fastertp.";
    private final String permission;

    Permissions(String sub) {
        this.permission = permPrefix + sub;
    }

    public boolean check(CommandSender sendi) {
        if(sendi instanceof Player player) return player.hasPermission(permission);
        return true;
    }
}
