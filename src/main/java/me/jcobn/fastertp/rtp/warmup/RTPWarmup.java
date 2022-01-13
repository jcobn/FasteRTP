package me.jcobn.fastertp.rtp.warmup;

import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.file.Messages;
import me.jcobn.fastertp.rtp.RTPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class RTPWarmup implements Listener {

    private final RTPPlayer rtpPlayer;
    private int taskId;

    public RTPWarmup(RTPPlayer rtpPlayer) {
        this.rtpPlayer = rtpPlayer;

        rtpPlayer.beforeWarmup();
        startDelay();
    }

    private void startDelay() {
        FasteRTP.getInstance().getServer().getPluginManager().registerEvents(this, FasteRTP.getInstance());
        taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(FasteRTP.getInstance(), getRun(this), FasteRTP.getInstance().getRtpConfig().getWarmupTime() * 20L);
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getUniqueId() == rtpPlayer.player.getUniqueId()) {
            if (event.getTo() != null && (
                    event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                            event.getFrom().getBlockY() != event.getTo().getBlockY() ||
                            event.getFrom().getBlockZ() != event.getTo().getBlockZ()
            )) {
                cancel();
            }
        }
    }

    @EventHandler
    private void onHit(EntityDamageEvent event) {
        if (event.getEntity().getUniqueId() == rtpPlayer.player.getUniqueId())
            cancel();
    }

    private Runnable getRun(RTPWarmup clazz) {
        return () -> {
            HandlerList.unregisterAll(clazz);
            if (FasteRTP.getInstance().getRtpManager().isPlayerRtping(rtpPlayer.player))
                rtpPlayer.rtp();
        };
    }

    private void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
        HandlerList.unregisterAll(this);
        rtpPlayer.player.sendMessage(Messages.CANCELLED_RTP.getMessage());
        FasteRTP.getInstance().getCooldownManager().removeFromCooldown(rtpPlayer.player);
        FasteRTP.getInstance().getRtpManager().removePlayerRtping(rtpPlayer.player);
    }
}
