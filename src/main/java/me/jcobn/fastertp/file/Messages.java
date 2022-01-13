package me.jcobn.fastertp.file;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.ChatColor;


public enum Messages {
    RTP_SELF("messages.rtp-self"),
    NO_PERMISSION("messages.no-permission"),
    TIME_HOURS("time.hours"),
    TIME_MINUTES("time.minutes"),
    TIME_SECONDS("time.seconds");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getMessage() {
        return msg(path, false);
    }

    public String getMessage(boolean withoutPrefix) {
        return msg(path, withoutPrefix);
    }

    private String msg(String path, boolean withoutPrefix) {
        String prefix = FasteRTP.getInstance().getLanguageManager().getPrefix();
        prefix = withoutPrefix ? null : prefix.length() == 0 ? null : prefix;
        String message = FasteRTP.getInstance().getLanguageManager().getTranslatedMessage(path);
        return ChatColor.translateAlternateColorCodes('&', prefix == null ? message : prefix + message);
    }
}
