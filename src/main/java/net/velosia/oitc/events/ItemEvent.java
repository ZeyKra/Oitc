package net.velosia.oitc.events;

import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.managers.PlayerManager;
import net.velosia.oitc.objects.YamlInventoryObject;
import net.velosia.oitc.util.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemEvent implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(event.getItem() == null || !event.getItem().hasItemMeta()) return;
        if(!event.getItem().getItemMeta().hasDisplayName()) return;

        String itemName = event.getItem().getItemMeta().getDisplayName();
        Player player = event.getPlayer();

        if(itemName.contains("Jouer")) {
            PlayerManager.setup(player);
            event.setCancelled(true);
        }
        else if (itemName.contains("Retour au Lobby")) {
            player.performCommand("lobby");
            event.setCancelled(true);
        }



        //event.setUseItemInHand(Event.Result.DENY);

    }


}
