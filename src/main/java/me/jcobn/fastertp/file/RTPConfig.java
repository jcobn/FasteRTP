package me.jcobn.fastertp.file;

import me.jcobn.fastertp.FasteRTP;


public class RTPConfig {

    private final FasteRTP plugin;

    public RTPConfig(FasteRTP plugin) {
        this.plugin = plugin;
    }

    public boolean getCooldownEnabled() {
        return plugin.getConfig().getBoolean("Cooldown.enabled");
    }

    public int getCooldownTime() {
        return plugin.getConfig().getInt("Cooldown.seconds");
    }

    public String getLocale() {
        return plugin.getConfig().getString("language");
    }
}
