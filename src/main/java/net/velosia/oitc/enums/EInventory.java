package net.velosia.oitc.enums;

import net.velosia.oitc.objects.YamlInventoryObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public enum EInventory {
    JOIN(Yaml.INVENTORY_JOIN),
    DEFAULT(Yaml.INVENTORY_DEFAULT);
    Yaml File;
    Map<Integer, YamlInventoryObject> InventoryMap;

    EInventory(Yaml File) {
        InventoryMap = File.readInventory();
    }

    public void Set(Player player) {
        player.closeInventory();
        Inventory inv = player.getInventory();

        for (Map.Entry<Integer, YamlInventoryObject> set : InventoryMap.entrySet()) {
            inv.setItem(set.getKey(), set.getValue().generateItem(player));
        }

        player.updateInventory();
    }

    public YamlInventoryObject getSlot(int slot) {
        return InventoryMap.get(slot);
    }

}

