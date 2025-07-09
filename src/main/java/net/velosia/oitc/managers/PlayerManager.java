package net.velosia.oitc.managers;

import net.velosia.oitc.enums.EInventory;
import net.velosia.oitc.enums.Update;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.objects.OitcPlayer;
import net.velosia.oitc.util.Lang;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Manages player states and actions in the OITC game.
 * Handles player setup, spawning, death, and respawn mechanics.
 * 
 * @author ZeyKra
 */
public class PlayerManager {

    /**
     * Sets up a player for the OITC game with default inventory and state.
     * 
     * @param player The player to setup, must not be null
     */
    public static void setup(Player player) {
        if (player == null) {
            return;
        }
        
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        EInventory.DEFAULT.Set(player);
        player.teleport(OitcManager.getRandomSpawn());

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
    }

    /**
     * Spawns a player to the lobby/join area with appropriate inventory and settings.
     * 
     * @param player The player to spawn, must not be null
     */
    public static void spawn(Player player) {
        if (player == null) {
            return;
        }
        
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        EInventory.JOIN.Set(player);
        player.teleport(Yaml.CONFIG.getLocWithDirection("spawn"));

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
    }

    /**
     * Handles player death mechanics including stats updates and attacker rewards.
     * Updates scoreboards, manages kill streaks, and sends appropriate messages.
     * 
     * @param player The player who died, must not be null
     */
    public static void handleDeath(Player player) {
        if(!OitcManager.exists(player)) return;
        OitcPlayer victim = OitcManager.getOitcPlayer(player);

        victim.addDeath(1);
        victim.setKillstreak(0);
        ScoreboardManager.updateScoreboard(victim.getPlayer(), Update.DEATH);
        ScoreboardManager.updateScoreboard(victim.getPlayer(), Update.KILLSTREAK);
        // Remove arrow level from player
        victim.getPlayer().setLevel(0);

        victim.handleAttacked();
        victim.resetAttacked();

        if(victim.getAttacker() == null ) return;

        OitcPlayer attacker = OitcManager.getOitcPlayer(victim.getAttacker());
        victim.resetAttacker();

        // Add stats and update scoreboard for attacker
        attacker.addKill(1);
        attacker.addKillstreak(1);
        OitcManager.handleKillStreak(attacker.getPlayer());

        ScoreboardManager.updateScoreboard(attacker.getPlayer(), Update.KILL);
        ScoreboardManager.updateScoreboard(attacker.getPlayer(), Update.KILLSTREAK);

        // Restore attacker's health
        attacker.getPlayer().setHealth(attacker.getPlayer().getMaxHealth());
        attacker.getPlayer().getInventory().addItem(EInventory.DEFAULT.getSlot(2).getItem());

        // Send messages to players
        String killed = Lang.format(Yaml.LANG.getString("message-killed"), attacker.getPlayer());
        killed = Lang.customFormat(killed, "{HEALTH}", "" + Math.round(attacker.getPlayer().getHealth()/2));
        OitcManager.sendActionBarMessage(victim.getPlayer(),  killed);

        String killer = Lang.format(Yaml.LANG.getString("message-kill"), victim.getPlayer());
        OitcManager.sendActionBarMessage(attacker.getPlayer(),  killer);
    }


    /**
     * Handles player respawn by resetting their state and teleporting to spawn.
     * 
     * @param player The player to respawn, must not be null
     */
    public static void handleRespawn(Player player) {
        if (player == null) {
            return;
        }
        // Teleport player to spawn and reset their state
        setup(player);
    }





}
