package net.velosia.oitc.managers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.objects.OitcPlayer;
import net.velosia.oitc.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OitcManager {

    static int max;

    public static Map<UUID, OitcPlayer> OitcPlayerList = new HashMap<>();
    private static Map<Integer, Location> RandomSpawn = new HashMap<>();

    public static Location getRandomSpawn() {
        int num = (int) (Math.floor(Math.random() * (max- 1 + 1)) + 1);
        return RandomSpawn.get(num);
    }

    public static void generateRandomSpawn() {
        max = Yaml.CONFIG.getInt("pos-number");
        Yaml.CONFIG.setConfigSection("pos");
        for (int i = 1; i < max + 1; i++) {
            //System.out.println(i);

            Location loc = Yaml.CONFIG.getLocWithDirection(i + "");
           //System.out.println(i + " : " + loc);

            RandomSpawn.put(i, loc);
        }
        Yaml.CONFIG.resetConfigSection();
    }

    public static OitcPlayer getOitcPlayer(Player player) {
        return OitcPlayerList.get(player.getUniqueId());
    }

    public static void createOitcPlayer(Player player) {
        OitcPlayer oitcPlayer = new OitcPlayer(player);
        OitcPlayerList.put(player.getUniqueId(), oitcPlayer);
    }

    public static void deleteOitcPlayer(Player player) {
        //oitcPlayer = OitcPlayerList.get(player.getUniqueId());
        OitcPlayerList.remove(player.getUniqueId());
    }

    public static void regenerateData() {
        if(Bukkit.getOnlinePlayers().size() == 0) return;

        for(Player player : Bukkit.getOnlinePlayers()) {
            createOitcPlayer(player);
            ScoreboardManager.createScoreboard(player);
        }

    }

    public static boolean exists(Player player) { return OitcPlayerList.containsKey(player.getUniqueId());}

    public static void handleKillStreak(Player player) {
        if(!exists(player)) return;
        int killstreak = getOitcPlayer(player).getKillstreak();

        player.setLevel(killstreak);
        if(killstreak == 3 ) {
            player.sendMessage(Lang.format(Yaml.LANG.getString("message-killstreak-private"), killstreak));
            player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            return;
        }
        switch (killstreak) {
            case 5:
                player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                break;
            case 10:
                player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                break;
             case 15:
                player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                break;
            case 20:
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                break;
        }
        if(Arrays.asList(5, 10, 15,20).contains(killstreak)) {
            String message = Lang.format(Yaml.LANG.getString("message-killstreak"), killstreak);
            message = Lang.format(message, player);

            Bukkit.broadcastMessage(Lang.format(message, killstreak));
        }


    }

    public static void sendActionBarMessage(Player p, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }



    //Void









}
