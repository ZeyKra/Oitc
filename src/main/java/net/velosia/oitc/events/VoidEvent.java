package net.velosia.oitc.events;

import net.velosia.oitc.enums.Region;
import net.velosia.oitc.enums.Update;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.managers.PlayerManager;
import net.velosia.oitc.managers.RegionManager;
import net.velosia.oitc.managers.ScoreboardManager;
import net.velosia.oitc.objects.OitcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidEvent implements Listener {

    @EventHandler
    public void onFall(PlayerMoveEvent e) {
        //if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return; //The player hasn't moved
        if(e.getTo().getBlockY() == e.getFrom().getBlockY()) return;
        Player player = e.getPlayer();

        if (RegionManager.checkIn(player.getLocation(), RegionManager.getRegionCuboid(Region.GAME))) {
            Bukkit.broadcastMessage(player + "est tombé de la map");

            PlayerManager.handleDeath(player);
            PlayerManager.spawn(player);

        }
        if (RegionManager.checkIn(player.getLocation(), RegionManager.getRegionCuboid(Region.SPAWN))) {
            Bukkit.broadcastMessage(player + "est tombé du spawn");
            PlayerManager.spawn(player);
        }
    }

}
