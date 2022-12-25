package net.velosia.oitc.managers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.EInventory;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.objects.OitcPlayer;
import net.velosia.oitc.util.Lang;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerManager {

    public static void setup(Player player) {

        player.getInventory().clear();
        EInventory.DEFAULT.Set(player);
        player.teleport(OitcManager.getRandomSpawn());

        player.setHealth(player.getHealthScale());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);

    }

    public static void spawn(Player player) {
        player.getInventory().clear();
        EInventory.JOIN.Set(player);
        player.teleport(Yaml.CONFIG.getLocWithDirection("spawn"));

        player.setHealth(player.getHealthScale());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
    }

    public static void handleDeath(Player player) {
        if(!OitcManager.exists(player)) return;
        OitcPlayer victim = OitcManager.getOitcPlayer(player);

        victim.addDeath(1);
        victim.setKillstreak(0);

        if(victim.getAttacker() == null ) return;
        OitcManager.handleKillStreak(player);

        OitcPlayer attacker = OitcManager.getOitcPlayer(victim.getAttacker());
        victim.resetAttacker();
        //victim.resetAttacked();

        //ajout des  stats
        attacker.addKill(1);
        attacker.addKillstreak(1);
        //remise a niveau du attacker
        attacker.getPlayer().setHealth(attacker.getPlayer().getHealthScale());
        attacker.getPlayer().getInventory().addItem(EInventory.DEFAULT.getSlot(2).getItem());

        //messages
        String killed = Lang.format(Yaml.LANG.getString("message-killed"), attacker.getPlayer());
        killed = Lang.customFormat(killed, "{HEALTH}", "" + Math.round(attacker.getPlayer().getHealth()));
        victim.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(killed));

        String killer = Lang.format(Yaml.LANG.getString("message-kill"), victim.getPlayer());
        attacker.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(killer));

    }

    public static void handleRespawn(Player player) {

    }





}
