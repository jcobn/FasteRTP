package me.jcobn.fastertp.file;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public enum Messages {
    RTP_SELF("rtp-self");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getMessage() {
        return msg(path);
    }

    public void send(CommandSender sendi) {
        sendi.sendMessage(msg(path));
    }

    public void send(Player player) {
        player.sendMessage(msg(path));
    }

    private String msg(String path) {
        String message = FasteRTP.getInstance().getLanguageManager().getTranslatedMessage(path);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
