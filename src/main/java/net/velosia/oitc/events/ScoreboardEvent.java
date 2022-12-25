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

public class ScoreboardEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        ScoreboardManager.createScoreboard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        FastBoard board = ScoreboardManager.removeScoreboard(player);
        board.delete();

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(OitcManager.getOitcPlayer(e.getEntity()) == null) return;
        OitcPlayer oitcVictim = OitcManager.getOitcPlayer(e.getEntity());

        if(oitcVictim.getAttacker() != null) return;

        ScoreboardManager.updateScoreboard(oitcVictim.getPlayer(), Update.DEATH);

    }


    @EventHandler
    public void onPlayerMurder(PlayerDeathEvent e) {
        if(OitcManager.getOitcPlayer(e.getEntity()) == null) return;
        OitcPlayer oitcVictim = OitcManager.getOitcPlayer(e.getEntity());

        if(oitcVictim.getAttacker() == null) return;
        OitcPlayer oitcAttacker = OitcManager.getOitcPlayer(oitcVictim.getAttacker());

        ScoreboardManager.updateScoreboard(oitcVictim.getPlayer(), Update.DEATH);
        ScoreboardManager.updateScoreboard(oitcVictim.getPlayer(), Update.KILLSTREAK);

        ScoreboardManager.updateScoreboard(oitcAttacker.getPlayer(), Update.KILL);
        ScoreboardManager.updateScoreboard(oitcAttacker.getPlayer(), Update.KILLSTREAK);
        oitcVictim.resetAttacker();

    }

}
