package net.velosia.oitc.events;

import net.velosia.oitc.Oitc;
import net.velosia.oitc.managers.OitcManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class ArrowEvent implements Listener {

    Oitc Instance = Oitc.instance;

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            e.getProjectile().setMetadata("shooter", new FixedMetadataValue(Instance ,  player.getUniqueId()));

        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Arrow)) return;
        if(!e.getDamager().hasMetadata("shooter")) return;
        //if(e.getHitEntity() == null) return;
        Arrow arrow = (Arrow) e.getDamager();

        arrow.remove();
        Player shooter = Bukkit.getPlayer((UUID) arrow.getMetadata("shooter").get(0).value());
        Bukkit.getServer().broadcastMessage("Shooter ! " + shooter.getName());

        if(shooter == e.getEntity()) return;
        if(e.getEntity() == null) return;

        Player victim  = (Player) e.getEntity();

        OitcManager.getOitcPlayer(victim).setAttacker(shooter);
        victim.setHealth(0);
        //((Arrow) e.getEntity()).setDamage(victim.getHealthScale());
        //e.getHitEntity().setLastDamageCause((EntityDamageEvent) shooter);



    }

    @EventHandler
    public void onProjectileDamage(EntityDamageByEntityEvent e) {

    }

}

