package me.jcobn.fastertp.rtp;

import io.papermc.lib.PaperLib;
import me.jcobn.fastertp.FasteRTP;
import me.jcobn.fastertp.file.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RTPPlayer {

    public final Player player;
    public final CommandSender sender;
    public final RTPOrigin orgin;
    public final String world;
    public final List<String> biomes;
    public final boolean warmup;
    public final boolean cooldown;
    public int attempts;

    public RTPPlayer(Player player, CommandSender sender, RTPOrigin orgin, String world, List<String> biomes, boolean warmup, boolean cooldown) {
        this.player = player;
        this.sender = sender;
        this.orgin = orgin;
        this.world = world;
        this.biomes = biomes;
        this.warmup = warmup;
        this.cooldown = cooldown;
    }

    public void beforeWarmup() {
        player.sendMessage(Messages.BEFORE_WARMUP.getMessage());
    }

    public void rtp() {
        if (attempts >= FasteRTP.getInstance().getRtpConfig().getMaxAttempts()) {
            player.sendMessage(Messages.MAX_ATTEMPTS.getMessage());
            FasteRTP.getInstance().getRtpManager().removePlayerRtping(player);
            FasteRTP.getInstance().getCooldownManager().removeFromCooldown(player);
            return;
        }
        Location location;
        location = FasteRTP.getInstance().getLocationManager().generateLocation(Bukkit.getWorld(world));
        attempts++;
        CompletableFuture<Chunk> chunk = PaperLib.getChunkAtAsync(location);
        chunk.thenAccept(result -> {
            Location loc;
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            //TODO: get at nether
            loc = FasteRTP.getInstance().getLocationManager().getOverworldLocation(location.getBlockX(), location.getBlockZ(), Bukkit.getWorld(world), yaw, pitch);

            //TODO: check claims here
            if (loc != null) {
                //TODO: price
                FasteRTP.getInstance().getRtp().teleportPlayer(player, sender, loc, attempts, this);
            } else
                rtp();
        });
    }


    /**
     * Executes the instant before generating the location
     */
    public void beforeRtp() {
        //TODO: implement
        if(warmup)
            player.sendMessage(Messages.BEFORE_WARMUP.getMessage());
    }

    /**
     * Executes the instant after teleporting the player
     */
    public void afterTeleport() {
        //TODO: implement
        player.sendMessage(Messages.RTP_SELF.getMessage() + " " + attempts + " attempts");
    }
}
