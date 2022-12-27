package net.velosia.oitc.managers;

import net.velosia.oitc.enums.EInventory;
import net.velosia.oitc.enums.Update;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.objects.OitcPlayer;
import net.velosia.oitc.util.Lang;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

    public static void setup(Player player) {

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        EInventory.DEFAULT.Set(player);
        player.teleport(OitcManager.getRandomSpawn());

        player.setHealth(player.getHealthScale());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);

    }

    public static void spawn(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
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
        ScoreboardManager.updateScoreboard(victim.getPlayer(), Update.DEATH);
        ScoreboardManager.updateScoreboard(victim.getPlayer(), Update.KILLSTREAK);
        //remive a nv
        victim.getPlayer().setLevel(0);

        victim.handleAttacked();
        victim.resetAttacked();

        if(victim.getAttacker() == null ) return;

        OitcPlayer attacker = OitcManager.getOitcPlayer(victim.getAttacker());
        victim.resetAttacker();


        //ajout des  stats / scoreboard update
        attacker.addKill(1);
        attacker.addKillstreak(1);
        OitcManager.handleKillStreak(attacker.getPlayer());

        ScoreboardManager.updateScoreboard(attacker.getPlayer(), Update.KILL);
        ScoreboardManager.updateScoreboard(attacker.getPlayer(), Update.KILLSTREAK);

        //remise a niveau du attacker
        attacker.getPlayer().setHealth(attacker.getPlayer().getHealthScale());
        attacker.getPlayer().getInventory().addItem(EInventory.DEFAULT.getSlot(2).getItem());

        //messages
        String killed = Lang.format(Yaml.LANG.getString("message-killed"), attacker.getPlayer());
        killed = Lang.customFormat(killed, "{HEALTH}", "" + Math.round(attacker.getPlayer().getHealth()/2));
        OitcManager.sendActionBarMessage(victim.getPlayer(),  killed);

        String killer = Lang.format(Yaml.LANG.getString("message-kill"), victim.getPlayer());
        OitcManager.sendActionBarMessage(attacker.getPlayer(),  killer);

    }


    public static void handleRespawn(Player player) {

    }





}
