package fr.bobinho.sutils.utils.settings;

import fr.bobinho.sutils.sUtilsCore;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class sUtilsSettings {

    /**
     * Fields
     */
    private final String fileName;
    private YamlConfiguration configuration;

    public sUtilsSettings(@Nonnull String fileName) {
        Validate.notNull(fileName, "fileName is null");

        this.fileName = fileName;
        Initialize();
    }

    /**
     * Gets the file name
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Initializes settings file
     */
    public void Initialize() {
        File file = new File(sUtilsCore.getInstance().getDataFolder() + "/" + getFileName() + ".yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (InputStream input = sUtilsSettings.class.getResourceAsStream("/" + getFileName() + ".yml")) {
                if (input != null)
                    Files.copy(input, file.toPath());
                else
                    file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        configuration = YamlConfiguration.loadConfiguration(file);

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Successfully loaded " + getFileName() + " data");
    }

    /**
     * Gets configuration
     *
     * @return the configuration
     */
    @Nonnull
    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Clears configuration
     */
    public void clear() {
        for (String key : getConfiguration().getKeys(false)) {
            getConfiguration().set(key, null);
        }
    }

    /**
     * Saves configuration
     */
    public void save() {
        try {
            getConfiguration().save(sUtilsCore.getInstance().getDataFolder() + "/" + getFileName() + ".yml");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the " + getFileName() + ".yml file");
        }
    }

}
