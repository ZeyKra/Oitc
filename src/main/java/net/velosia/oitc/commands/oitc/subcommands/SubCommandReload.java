package net.velosia.oitc.commands.oitc.subcommands;

import net.velosia.oitc.commands.SubCommand;
import net.velosia.oitc.managers.ScoreboardManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.SaveCommand;
import org.bukkit.entity.Player;

public class SubCommandReload extends SubCommand {

    @Override
    public String getName() { return "reload"; }

    @Override
    public String getDescription() { return "reload les configurations"; }

    @Override
    public String getSyntax() { return "/oitc reload (element)"; }

    @Override
    public Boolean isOnlyPlayerCommand() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Player Only command");
            return;
        }
        Player player = (Player) sender;

        if(args.length == 1) {
            sender.sendMessage("§c erreur précisez un element (all|config|scoreboard|hologram)");
            return;
        }

        switch (args[2].toLowerCase()) {
            case "config":
                player.sendMessage("config reload ");
                break;
        }
        ScoreboardManager.getScoreboard(player).updateLine(1, "test line");
    }

}
