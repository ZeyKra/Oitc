package net.velosia.oitc.managers;

import fr.mrmicky.fastboard.FastBoard;
import net.velosia.oitc.enums.Update;
import net.velosia.oitc.objects.OitcPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    static Map<UUID, FastBoard> ScoreboardList = new HashMap<>();

    public static FastBoard createScoreboard(Player player) {

        OitcPlayer oitcPlayer = OitcManager.getOitcPlayer(player);

        FastBoard board = new FastBoard(player) {
            @Override
            public boolean hasLinesMaxLength() {
                return true; // or just return true;
            }
        };

        board.updateTitle("      §2§lOITC     ");

        board.updateLines(
                "", // Empty line
                "  §7Kills: §a" + oitcPlayer.getKill(),
                "  §7Morts: §a" + oitcPlayer.getDeath(),
                "  §7Killstreak: §a" + oitcPlayer.getKillstreak(),
                "",
                "§8➣ §cAlliances interdites  ",
                "",
                "  §7Joueurs: x  ",
                "",
                "        §aplay.velosia.ml        "
        );
        ScoreboardList.put(player.getUniqueId(), board);
        return board;
    }


    public static FastBoard getScoreboard(Player player) { return ScoreboardList.get(player.getUniqueId()); };
    public static FastBoard removeScoreboard(Player player) { return ScoreboardList.remove(player.getUniqueId()); };

    public static void updateScoreboard(Player player, Update update) {
        OitcPlayer oitcPlayer = OitcManager.getOitcPlayer(player);
        switch (update) {
            case KILL:
                getScoreboard(player).updateLine(1, "  §7Kills: §a" + oitcPlayer.getKill());
                break;
            case DEATH:
                getScoreboard(player).updateLine(2, "  §7Morts: §a" + oitcPlayer.getDeath());
                break;
            case KILLSTREAK:
                getScoreboard(player).updateLine(3, "  §7Killstreak: §a" + oitcPlayer.getKillstreak());
                break;
            case PLAYERS:
                //getScoreboard(player).updateLine(2, "  §7Morts: §a" +  + "");
                break;
        }

    }




}


