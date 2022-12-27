package net.velosia.oitc.events;

import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.managers.PlayerManager;
import net.velosia.oitc.objects.OitcPlayer;
import net.velosia.oitc.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerEvent implements Listener {

    @EventHandler
    public void  onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        OitcManager.createOitcPlayer(player);

        player.sendTitle(Lang.format(Yaml.LANG.getString("message-join-title")), Lang.format(Yaml.LANG.getString("message-join-subtitle")));

        PlayerManager.spawn(player);
    }

    /*
     Statistics
    */


    /*
     Event quand le joueur se  fait tuer par un autre joueur
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMurder(PlayerDeathEvent e) {
        if(OitcManager.getOitcPlayer(e.getEntity()) == null) return;
        e.setDeathMessage("");
        OitcPlayer victim = OitcManager.getOitcPlayer(e.getEntity());
        e.getDrops().clear();

        PlayerManager.handleDeath(victim.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void AutoRespawn(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTask(Oitc.instance, () -> event.getEntity().spigot().respawn());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerManager.spawn(event.getPlayer());
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        if(OitcManager.getOitcPlayer(victim) == null) return;

        //met l'attacker de la victim
        OitcManager.getOitcPlayer(victim).setAttacker(attacker);

        //On ajoute la victim aux personnes attackés
        if(OitcManager.getOitcPlayer(attacker) == null) return;
        OitcManager.getOitcPlayer(attacker).addAttacked(victim);

    }

    // Player life changes jsp comment l'appelé zebi
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof  Player)) return;
        if(e.getCause() == EntityDamageEvent.DamageCause.LAVA ||
           e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

}
