package me.jcobn.fastertp.rtp.warmup;

import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.Permissions;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarmupManager {

    private final Map<UUID, Integer> playersInWarmup = new HashMap<>();
    private final FasteRTP plugin;

    public WarmupManager(FasteRTP plugin) {
        this.plugin = plugin;
    }

    public boolean shouldWarmup(Player player) {
        return !Permissions.BYPASS_WARMUP.check(player) && plugin.getRtpConfig().getWarmupEnabled();
    }

    public boolean isInWarmup(Player player) {
        return playersInWarmup.containsKey(player.getUniqueId());
    }

    public int getTaskId(Player player) {
        return playersInWarmup.getOrDefault(player.getUniqueId(), null);
    }
}
