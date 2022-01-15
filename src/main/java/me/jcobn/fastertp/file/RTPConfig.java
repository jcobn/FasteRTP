package me.jcobn.fastertp.file;

import lombok.Getter;
import me.jcobn.fastertp.FasteRTP;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;


public class RTPConfig {

    private final FasteRTP plugin;
    @Getter
    private final List<Material> badBlocks = new ArrayList<>();

    public RTPConfig(FasteRTP plugin) {
        this.plugin = plugin;
        setBadBlocks();
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

    public boolean getWarmupEnabled() {
        return bool("warmup.enabled");
    }

    public int getWarmupTime() {
        return num("warmup.seconds");
    }

    public int getMaxAttempts() {
        return num("rtp-settings.max-attempts");
    }

    private void setBadBlocks() {
        List<String> list = plugin.getConfig().getStringList("rtp-settings.bad-blocks");
        for (String s : list) {
            try {
                Material mat = Material.matchMaterial(s);
                badBlocks.add(mat);
            } catch (Exception e) {
                plugin.getLogger().severe("Wrong bad block name was used in the config");
            }
        }
    }

    public boolean getBeforeGenerationMsgEnabled() {
        return bool("rtp-settings.before-generation.message");
    }

    public boolean getBeforeTeleportTitleEnabled() {
        return bool("rtp-settings.before-teleport.title");
    }

    public boolean getAfterTeleportMsgEnabled() {
        return bool("rtp-settings.after-teleport.message");
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
