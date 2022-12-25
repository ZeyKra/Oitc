package net.velosia.oitc.events;

import net.velosia.oitc.Oitc;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.objects.OitcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class ArrowEvent implements Listener {

    Oitc Instance = Oitc.Instance;

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            e.getProjectile().setMetadata("shooter", new FixedMetadataValue(Instance ,  player.getUniqueId()));

        }
    }

    @EventHandler
    public void onProjectileDamage(ProjectileHitEvent e) {
        if(!(e.getEntity() instanceof Arrow)) return;
        if(!e.getEntity().hasMetadata("shooter")) return;
        //if(e.getHitEntity() == null) return;
        e.getEntity().remove();

        if(e.getHitEntity() == e.getEntity().getShooter()) return;

        Arrow arrow = (Arrow) e.getEntity();

        Player shooter = Bukkit.getPlayer((UUID) arrow.getMetadata("shooter").get(0).value());
        Bukkit.getServer().broadcastMessage("Shooter ! " + shooter.getName());

        if(e.getHitEntity() == null) return;
        Player victim  = (Player) e.getHitEntity();

        OitcManager.getOitcPlayer(victim).setAttacker(shooter);
        ((Arrow) e.getEntity()).setDamage(victim.getHealthScale());
        //e.getHitEntity().setLastDamageCause((EntityDamageEvent) shooter);



    }

    @EventHandler
    public void onProjectileDamage(EntityDamageByEntityEvent e) {

    }

}

