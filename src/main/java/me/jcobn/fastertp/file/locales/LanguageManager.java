package me.jcobn.fastertp.file.locales;

import lombok.Getter;
import me.jcobn.fastertp.FasteRTP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Path;

public class LanguageManager {

    private final FasteRTP plugin;
    @Getter
    private final Languages currentLanguage;
    private final FileConfiguration localeFile;

    public LanguageManager(FasteRTP plugin, Path languagePath) {
        this.plugin = plugin;
        this.currentLanguage = getCurrentLanguageFromConfig();
        File lFile = new File(languagePath.toString(), currentLanguage.getFileName());
        this.localeFile = YamlConfiguration.loadConfiguration(lFile);
    }

    private Languages getCurrentLanguageFromConfig() {
        String locale = plugin.getRtpConfig().getLocale();
        Languages language = null;
        for (Languages lang : Languages.values()) {
            if (lang.getFileName().equalsIgnoreCase(locale)) {
                language = lang;
                break;
            }
        }
        return language == null ? Languages.ENGLISH : language;
    }

    public String getTranslatedMessage(String path) {
        return localeFile.getString(path);
    }
}
