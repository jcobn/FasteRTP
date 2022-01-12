package me.jcobn.fastertp;

import lombok.Getter;
import me.jcobn.fastertp.api.FasteRTPAPI;
import me.jcobn.fastertp.commands.FasteRTPCommand;
import me.jcobn.fastertp.commands.RTPCommand;
import me.jcobn.fastertp.file.Messages;
import me.jcobn.fastertp.file.RTPConfig;
import me.jcobn.fastertp.network.Metrics;
import me.jcobn.fastertp.rtp.RTPManager;
import me.jcobn.fastertp.rtp.cooldown.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FasteRTP extends JavaPlugin {

    private static FasteRTP plugin;
    @Getter
    private final Messages messages = new Messages(this);
    @Getter
    private final RTPManager rtpManager = new RTPManager(this);
    @Getter
    private final CooldownManager cooldownManager = new CooldownManager(this);
    @Getter
    private final RTPConfig rtpConfig = new RTPConfig(this);
    @Getter
    private FasteRTPAPI fasteRtpApi;

    @Override
    public void onEnable() {
        plugin = this;
        fasteRtpApi = new FasteRTPAPI(plugin);
        getCommand("rtp").setExecutor(new RTPCommand(this));
        getCommand("fastertp").setExecutor(new FasteRTPCommand(this));
        new Metrics(this, 13935);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public static FasteRTP getInstance() {
        return plugin;
    }

    public FasteRTPAPI getAPI() {
        return fasteRtpApi;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
