package me.jcobn.fastertp.rtp;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RTPManager {

    private final Set<UUID> rtping = new HashSet<>();

    private final FasteRTP plugin;

    public RTPManager(FasteRTP plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayerRtping(Player player) {
        return rtping.contains(player.getUniqueId());
    }
}
