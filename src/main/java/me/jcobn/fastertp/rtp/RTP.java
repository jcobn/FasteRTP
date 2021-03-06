package me.jcobn.fastertp.rtp;

import io.papermc.lib.PaperLib;
import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.file.Messages;
import me.jcobn.fastertp.rtp.warmup.RTPWarmup;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RTP {

    private final FasteRTP plugin;
    private final RTPManager rtpManager;

    public RTP(FasteRTP plugin) {
        this.plugin = plugin;
        this.rtpManager = plugin.getRtpManager();
    }

    public void tp(CommandSender sender, Player player, RTPOrigin origin, String world, List<String> biomes, boolean shouldWarmup, boolean cooldown) {
        RTPPlayer rtpPlayer = new RTPPlayer(player, sender, origin, world, biomes, shouldWarmup, cooldown);
        start(rtpPlayer);
    }

    private void start(RTPPlayer rtpPlayer) {
        String wName = rtpPlayer.world;
        Player player = rtpPlayer.player;

        if (wName == null) {
            wName = player.getWorld().getName();
        }
        World world = Bukkit.getWorld(wName);
        if (world == null) {
            rtpPlayer.sender.sendMessage(Messages.WORLD_DOESNT_EXIST.getMessage());
            return;
        }
        rtp(rtpPlayer);
    }

    private void rtp(RTPPlayer rtpPlayer) {
        if (rtpPlayer.cooldown)
            plugin.getCooldownManager().addCooldownToPlayer(rtpPlayer.player);
        plugin.getRtpManager().addPlayerRtping(rtpPlayer.player);
        rtpPlayer.beforeGeneration();
        if (rtpPlayer.warmup)
            new RTPWarmup(rtpPlayer);
        else
            rtpPlayer.rtp();
    }

    public void teleportPlayer(Player player, CommandSender sendi, Location location, int attempts, RTPPlayer rtpPlayer) {
        rtpPlayer.beforeTeleport();
        List<CompletableFuture<Chunk>> asChunks = getChunks(location);
        CompletableFuture.allOf(asChunks.toArray(new CompletableFuture[]{})).thenRun(() -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        PaperLib.teleportAsync(player, location).thenRun(new BukkitRunnable() {
                            @Override
                            public void run() {
                                rtpPlayer.afterTeleport(location);
                                if (player != sendi) {
                                    //TODO: rtp other message?
                                }
                                plugin.getRtpManager().removePlayerRtping(player);
                                //TODO: on first join handle spawnpoint
                            }
                        });
                    } catch (Exception e) {
                        plugin.getRtpManager().removePlayerRtping(player);
                        e.printStackTrace();
                    }
                }
            }.runTask(plugin);
        });
    }

    private List<CompletableFuture<Chunk>> getChunks(Location loc) {
        List<CompletableFuture<Chunk>> asyncChunks = new ArrayList<>();
        int range = Math.round(Math.max(0, Math.min(16, FasteRTP.getInstance().getRtpConfig().getChunkPregenRadius())));
        for (int x = -range; x <= range; x++)
            for (int z = -range; z <= range; z++) {
                Location locLoad = new Location(loc.getWorld(), loc.getX() + (x * 16), loc.getY(), loc.getZ() + (z * 16));
                CompletableFuture<Chunk> chunk = PaperLib.getChunkAtAsync(locLoad, true);
                asyncChunks.add(chunk);
            }
        return asyncChunks;
    }
}
