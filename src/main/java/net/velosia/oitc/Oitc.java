package net.velosia.oitc;

import de.slikey.effectlib.EffectManager;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import net.velosia.oitc.commands.oitc.CommandOitc;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.events.*;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for the OITC (One In The Chamber) Minecraft minigame.
 * Handles plugin initialization, event registration, and resource management.
 * 
 * @author ZeyKra
 * @version 1.0
 */
public final class Oitc extends JavaPlugin {

    /** Plugin instance for static access */
    public static Oitc instance;
    
    /** API instance for HolographicDisplays integration */
    public static HolographicDisplaysAPI HoloAPI;
    
    /** Effect manager for particle effects */
    public static EffectManager effectManager;

    /**
     * Called when the plugin is enabled.
     * Initializes all dependencies, configurations, and game components.
     */
    @Override
    public void onEnable() {
        instance = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        HoloAPI = HolographicDisplaysAPI.get(instance);

        Yaml.CONFIG.create(getLogger());
        Yaml.LANG.create(getLogger());
        Yaml.INVENTORY_JOIN.create(getLogger());
        Yaml.INVENTORY_DEFAULT.create(getLogger());

        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        getServer().getPluginManager().registerEvents(new NPCEvent(), this);
        getServer().getPluginManager().registerEvents(new ItemEvent(), this);
        getServer().getPluginManager().registerEvents(new ArrowEvent(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardEvent(), this);
        getServer().getPluginManager().registerEvents(new VoidEvent(), this);

        getCommand("oitc").setExecutor(new CommandOitc());

        effectManager = new EffectManager(this, getLogger());

        OitcManager.generateRandomSpawn();
        OitcManager.regenerateData();
        RegionManager.generateCuboids();




        // Plugin startup logic

    }

    /**
     * Called when the plugin is disabled.
     * Performs cleanup of resources and logs shutdown message.
     */
    @Override
    public void onDisable() {
        // Clean up resources
        if (effectManager != null) {
            effectManager.dispose();
        }
        
        getLogger().info("Oitc plugin has been disabled.");
    }
}
