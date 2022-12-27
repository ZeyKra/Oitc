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

public final class Oitc extends JavaPlugin {

    public static Oitc instance;
    public static Yaml Config;
    public static HolographicDisplaysAPI HoloAPI;

    public static EffectManager effectManager;

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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
