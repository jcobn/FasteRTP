package me.jcobn.fastertp.file;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.ChatColor;

import java.text.MessageFormat;


public enum Messages {
    RTP_SELF("messages.rtp-self"),
    NO_PERMISSION("messages.no-permission"),
    IN_COOLDOWN("messages.in-cooldown"),
    IN_WARMUP("messages.in-warmup"),
    IN_RTP("messages.in-rtp"),
    WORLD_DOESNT_EXIST("messages.world-not-exist"),
    CANCELLED_RTP("messages.cancelled-tp"),
    BEFORE_WARMUP("messages.warmup-start"),
    MAX_ATTEMPTS("messages.max-attempts"),
    TIME_HOURS("time.hours"),
    TIME_MINUTES("time.minutes"),
    TIME_SECONDS("time.seconds");

    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getMessage(Object... args) {
        return msg(path, false, args);
    }

    public String getMessageNoPrefix(Object... args) {
        return msg(path, true, args);
    }

    private String msg(String path, boolean withoutPrefix, Object... args) {
        String prefix = FasteRTP.getInstance().getLanguageManager().getPrefix();
        prefix = withoutPrefix ? null : prefix.length() == 0 ? null : prefix;
        String message = FasteRTP.getInstance().getLanguageManager().getTranslatedMessage(path);
        return MessageFormat.format(ChatColor.translateAlternateColorCodes('&', prefix == null ? message : prefix + message), args);
    }
}
