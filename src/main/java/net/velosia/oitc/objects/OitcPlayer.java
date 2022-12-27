package net.velosia.oitc.objects;

import net.velosia.oitc.managers.OitcManager;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class OitcPlayer {


    Player player, attacker;
    int kill, death, killstreak;

    Set<Player> attacked = new HashSet<>(); // Represente les joueurs qui ont était ataqué par  oitcplayer actuelement

    public OitcPlayer(Player player) {
        //System.out.println("tes");
        this.kill = 0;
        this.death = 0;
        this.killstreak = 0;
        this.player = player;

    }

    //getter
    public int getDeath() { return death;}
    public int getKill() { return kill; }
    public int getKillstreak() { return killstreak; }
    public Player getPlayer() { return player; }
    public Player getAttacker() { return attacker; }
    public Set<Player> getAttacked() { return attacked; }

    //setter
    public void setKill(int kill) { this.kill = kill; }
    public void setDeath(int value) { this.death = value; }
    public void setKillstreak(int killstreak) { this.killstreak = killstreak; }
    public void setAttacker(Player attacker) { this.attacker = attacker; }

    //adder
    public void addKill(int value) { this.kill+=value; }
    public void addDeath(int value) { this.death+=value; }
    public void addKillstreak(int value) { this.killstreak+=value; }
    public void addAttacked(Player player) { this.attacked.add(player); }

    //misc
    public void resetAttacker() { this.attacker = null; }
    public void resetAttacked() { this.attacked.clear(); }

    public void removeAttacked(Player player) { this.attacked.remove(player); }


    //Fonction qui  permet de reset l'attacker des  gens attaqué par player si leurs attacker == player
    //Usage mort dans le  vide tout seul  après pvp
    public void handleAttacked() {
        if(attacked.size() == 0) return;
        for(Player attackedPlayer : attacked) {
            if(!OitcManager.exists(attackedPlayer)) continue;
            OitcPlayer oitcP = OitcManager.getOitcPlayer(attackedPlayer);

            if(oitcP.getAttacker() != player) return;
            oitcP.resetAttacker();

        }

    }

    public void debugMessage(Player p) {
        p.sendMessage("--- DEBUG ---");
        p.sendMessage("Kill : " + kill);
        p.sendMessage("Killstreak : " + killstreak);
        p.sendMessage("Death : " + death);
        p.sendMessage("§7getPlayer : " + ((player != null) ? player.getName() : "§cnull"));
        p.sendMessage("§7Attacker : " + ((attacker != null) ? attacker.getName() : "§cnull"));

        StringBuilder attackedList = new StringBuilder("[]");
        if(attacked.size() > 0) {
            for(Player someone : attacked) {
                attackedList.append(" ").append(someone.getName());
            }
        }
        p.sendMessage("§7Attacked : " + attackedList);

        p.sendMessage("--- DEBUG ---");

    }

}

