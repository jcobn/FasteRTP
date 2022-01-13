package me.jcobn.fastertp;

import lombok.Getter;
import me.jcobn.fastertp.api.FasteRTPAPI;
import me.jcobn.fastertp.commands.FasteRTPCommand;
import me.jcobn.fastertp.commands.RTPCommand;
import me.jcobn.fastertp.file.Messages;
import me.jcobn.fastertp.file.RTPConfig;
import me.jcobn.fastertp.file.locales.LanguageManager;
import me.jcobn.fastertp.file.locales.Languages;
import me.jcobn.fastertp.network.Metrics;
import me.jcobn.fastertp.rtp.RTP;
import me.jcobn.fastertp.rtp.RTPManager;
import me.jcobn.fastertp.rtp.cooldown.CooldownManager;
import me.jcobn.fastertp.rtp.tp.LocationManager;
import me.jcobn.fastertp.rtp.warmup.WarmupManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FasteRTP extends JavaPlugin {

    private static FasteRTP plugin;
    @Getter
    private final RTPManager rtpManager = new RTPManager(this);
    @Getter
    private final CooldownManager cooldownManager = new CooldownManager(this);
    @Getter
    private final WarmupManager warmupManager = new WarmupManager(this);
    @Getter
    private final RTP rtp = new RTP(this);
    @Getter
    private final RTPConfig rtpConfig = new RTPConfig(this);
    @Getter
    private final LocationManager locationManager = new LocationManager(this);
    @Getter
    private LanguageManager languageManager;
    @Getter
    private FasteRTPAPI fasteRtpApi;

    @Override
    public void onEnable() {
        plugin = this;
        fasteRtpApi = new FasteRTPAPI(plugin);
        getCommand("rtp").setExecutor(new RTPCommand(this));
        getCommand("fastertp").setExecutor(new FasteRTPCommand(this));
        new Metrics(this, 13935);

        loadFiles();
    }

    private void loadFiles() {
        //Initialize the default config.yml file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        try {
            //Locale files
            Path languageDir = Paths.get(getDataFolder().getPath(), "locales");
            if (!Files.exists(languageDir)) {
                Files.createDirectory(languageDir);
            }
            for (Languages lang : Languages.values()) {
                Path langFile = Paths.get(languageDir.toString(), lang.getFileName());
                if (Files.exists(langFile)) continue;
                Files.copy(
                        getClassLoader().getResourceAsStream("locales/" + lang.getFileName()),
                        Paths.get(
                                languageDir.toString(), lang.getFileName()
                        )
                );

            }
            languageManager = new LanguageManager(this, languageDir);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Couldn't load config files");
        }
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
