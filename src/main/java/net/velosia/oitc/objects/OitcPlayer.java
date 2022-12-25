package net.velosia.oitc.objects;

import net.velosia.oitc.managers.OitcManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OitcPlayer {


    Player player, attacker;
    int kill, death, killstreak;

    List<Player> attacked = new ArrayList<>(); // Represente les joueurs qui ont était ataqué par  oitcplayer actuelement

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
    public List<Player> getAttacked() { return attacked; }

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

    public void HandleAttacked(Player player) {
        if(attacked.size() == 0) return;
        for(Player p : attacked) {
            if(!OitcManager.exists(p)) continue;
            OitcPlayer oitcP = OitcManager.getOitcPlayer(p);

            if(oitcP.getAttacker() != player) return;
            oitcP.resetAttacked();

        }

    }

}
