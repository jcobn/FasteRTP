package me.jcobn.fastertp.file;

import me.jcobn.fastertp.FasteRTP;


public class RTPConfig {

    private final FasteRTP plugin;

    public RTPConfig(FasteRTP plugin) {
        this.plugin = plugin;
    }

    public boolean getCooldownEnabled() {
        return bool("cooldown.enabled");
    }

    public int getCooldownTime() {
        return num("cooldown.seconds");
    }

    public String getLocale() {
        return str("language");
    }

    private String str(String path) {
        return plugin.getConfig().getString(path);
    }

    private boolean bool(String path) {
        return plugin.getConfig().getBoolean(path);
    }

    private int num(String path) {
        return plugin.getConfig().getInt(path);
    }
}
