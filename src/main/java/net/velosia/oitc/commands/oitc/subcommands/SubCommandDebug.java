package net.velosia.oitc.commands.oitc.subcommands;

import net.velosia.oitc.commands.SubCommand;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.managers.OitcManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.SaveCommand;
import org.bukkit.entity.Player;

public class SubCommandDebug extends SubCommand {

    @Override
    public String getName() { return "debug"; }

    @Override
    public String getDescription() { return "Command de debug"; }

    @Override
    public String getSyntax() { return "/oitc debug (player)"; }

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
        Player target = (args.length >= 2) ? Bukkit.getPlayer(args[1]) : player;

        OitcManager.getOitcPlayer(target).debugMessage(player);
    }

}
