package net.velosia.oitc.events;

import fr.mrmicky.fastboard.FastBoard;
import net.velosia.oitc.enums.Update;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.managers.ScoreboardManager;
import net.velosia.oitc.objects.OitcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Handles scoreboard-related events for players in the OITC game.
 * Manages scoreboard creation, deletion, and updates.
 * 
 * @author ZeyKra
 */
public class ScoreboardEvent implements Listener {

    /**
     * Creates a scoreboard for a player when they join.
     * 
     * @param e The player join event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ScoreboardManager.createScoreboard(player);
    }

    /**
     * Removes and cleans up a player's scoreboard when they quit.
     * 
     * @param e The player quit event
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = ScoreboardManager.removeScoreboard(player);
        if (board != null) {
            board.delete();
        }
    }
}
