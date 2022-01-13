package me.jcobn.fastertp.rtp.tp;

import me.jcobn.fastertp.FasteRTP;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;

public class LocationManager {

    private final FasteRTP plugin;

    public LocationManager(FasteRTP plugin) {
        this.plugin = plugin;
    }

    public Location generateLocation(World world) {
        int quadrant = new Random().nextInt(4);
        int min = 1000;
        int max = 20000;
        int x, z;
        switch (quadrant) {
            case 0:
                x = new Random().nextInt(max) + min;
                z = new Random().nextInt(max) + min;
                break;
            case 1:
                x = -new Random().nextInt(max) - min;
                z = -(new Random().nextInt(max) + min);
                break;
            case 2:
                x = -new Random().nextInt(max) - min;
                z = new Random().nextInt(max) + min;
                break;
            default:
                x = new Random().nextInt(max) + min;
                z = -(new Random().nextInt(max) + min);
                break;
        }
        return new Location(world, x, 0, z);
    }

    public Location getOverworldLocation(int x, int z, World world, float yaw, float pitch) {
        Block b = world.getHighestBlockAt(x, z);
        if (b.getType().toString().endsWith("AIR")) //<=1.15.1
            b = world.getBlockAt(x, b.getY() - 1, z);
        else if (!b.getType().isSolid()) {
            //TODO: implement bad blocks
            b = world.getBlockAt(x, b.getY() - 1, z);
        }
        //TODO: implement bad blocks
        if (b.getY() > 0)
            return new Location(world, (x + 0.5), b.getY() + 1, (z + 0.5), yaw, pitch);
        return null;
    }

}
