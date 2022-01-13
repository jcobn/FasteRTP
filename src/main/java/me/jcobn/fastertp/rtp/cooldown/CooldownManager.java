package me.jcobn.fastertp.rtp.cooldown;

import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.Permissions;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final FasteRTP plugin;

    public CooldownManager(FasteRTP plugin) {
        this.plugin = plugin;
    }

    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    public void addCooldownToPlayer(Player player) {
        if (!plugin.getRtpConfig().getCooldownEnabled() || Permissions.BYPASS_COOLDOWN.check(player)) return;
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis() + (plugin.getRtpConfig().getCooldownTime() * 1000L));
    }

    public boolean userHasCooldown(Player player) {
        if(exceptionFromCooldown(player)) return false;
        long cooldown = cooldownMap.getOrDefault(player.getUniqueId(), 0L);
        if (cooldown != 0L && System.currentTimeMillis() < cooldown) {
            return true;
        } else cooldownMap.remove(player.getUniqueId());
        return false;
    }

    public int getUserCooldown(Player player) {
        if(exceptionFromCooldown(player)) return 0;
        long cooldown = cooldownMap.getOrDefault(player.getUniqueId(), 0L);
        return cooldown == 0L ? 0 : (int) ((cooldown - System.currentTimeMillis()) / 1000);
    }

    public boolean exceptionFromCooldown(Player player) {
        return !plugin.getConfig().getBoolean("cooldown.enabled") || Permissions.BYPASS_COOLDOWN.check(player);
    }

    public void removeFromCooldown(Player player) {
        cooldownMap.remove(player.getUniqueId());
    }
}
