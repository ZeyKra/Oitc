package net.velosia.oitc.commands.oitc.subcommands;

import net.velosia.oitc.commands.SubCommand;
import net.velosia.oitc.enums.ECosmetic;
import net.velosia.oitc.managers.CosmeticManager;
import net.velosia.oitc.managers.OitcManager;
import net.velosia.oitc.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandTest extends SubCommand {

    @Override
    public String getName() { return "test"; }

    @Override
    public String getDescription() { return "Command de test"; }

    @Override
    public String getSyntax() { return "/oitc test"; }

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
        CosmeticManager.summon(ECosmetic.TETE_DE_FLAME, player);
    }

}
