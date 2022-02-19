package fr.bobinho.sutils;

import co.aikar.commands.PaperCommandManager;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import fr.bobinho.sutils.commands.EchestCommand;
import fr.bobinho.sutils.commands.RulesCommand;
import fr.bobinho.sutils.commands.home.DelhomeCommand;
import fr.bobinho.sutils.commands.home.HomeCommand;
import fr.bobinho.sutils.commands.home.SethomeCommand;
import fr.bobinho.sutils.commands.spawn.SetspawnCommand;
import fr.bobinho.sutils.commands.spawn.SpawnCommand;
import fr.bobinho.sutils.commands.teleportation.TpaCommand;
import fr.bobinho.sutils.commands.teleportation.TpyesCommand;
import fr.bobinho.sutils.listeners.*;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import fr.bobinho.sutils.utils.settings.sUtilsSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class sUtilsCore extends JavaPlugin {

    /**
     * Fields
     */
    private static sUtilsCore instance;
    private static sUtilsSettings mainSettings;
    private static sUtilsSettings safezonesSettings;
    private static sUtilsSettings homesSettings;
    private static WorldBorderApi worldBorderApi = Bukkit.getServer().getServicesManager().getRegistration(WorldBorderApi.class).getProvider();

    /**
     * Gets the sutils core instance
     *
     * @return the sutils core instance
     */
    @Nonnull
    public static sUtilsCore getInstance() {
        return instance;
    }

    /**
     * Gets the main settings
     *
     * @return the main settings
     */
    @Nonnull
    public static sUtilsSettings getMainSettings() {
        return mainSettings;
    }

    /**
     * Gets the safezones settings
     *
     * @return the safezones settings
     */
    @Nonnull
    public static sUtilsSettings getSafezonesSettings() {
        return safezonesSettings;
    }

    /**
     * Gets the homes settings
     *
     * @return the homes settings
     */
    @Nonnull
    public static sUtilsSettings getHomesSettings() {
        return homesSettings;
    }

    /**
     * Gets the world border api
     *
     * @return the world border api
     */
    @Nonnull
    public static WorldBorderApi getWorldBorderApi() {
        return worldBorderApi;
    }

    /**
     * Enable and initialize the plugin
     */
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[LuxePractice] Loading the plugin...");

        //Registers commands and listeners
        registerCommands();
        registerListeners();

        //Registers files settings
        mainSettings = new sUtilsSettings("settings");
        safezonesSettings = new sUtilsSettings("safezones");
        homesSettings = new sUtilsSettings("homes");

        //Loads safezones
        sUtilsSafezoneManager.loadsUtilsSafezone();
    }

    /**
     * Disable the plugin and save data
     */
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[LuxePractice] Unloading the plugin...");

        //Saves safezones
        sUtilsSafezoneManager.savesUtilsSafezone();
    }

    /**
     * Register listeners
     */
    private void registerListeners() {

        //Registers test listener
        Bukkit.getServer().getPluginManager().registerEvents(new DisablingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PotionListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new NetheriteListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CombatListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PotionListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SafezoneListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Successfully loaded listeners");
    }

    /**
     * Register commands
     */
    private void registerCommands() {
        final PaperCommandManager commandManager = new PaperCommandManager(this);

        //Registers homes command
        commandManager.registerCommand(new DelhomeCommand());
        commandManager.registerCommand(new HomeCommand());
        commandManager.registerCommand(new SethomeCommand());

        //Registers spawns command
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new SetspawnCommand());

        //Registers teleportations command
        commandManager.registerCommand(new TpaCommand());
        commandManager.registerCommand(new TpyesCommand());

        //Registers rules command
        commandManager.registerCommand(new RulesCommand());

        //Registers echest command
        commandManager.registerCommand(new EchestCommand());

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Successfully loaded commands");
    }

}