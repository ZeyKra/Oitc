package net.velosia.oitc.commands.oitc.subcommands;

import net.velosia.oitc.commands.SubCommand;
import net.velosia.oitc.enums.Yaml;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandAddpos extends SubCommand {

    @Override
    public String getName() { return "addpos"; }

    @Override
    public String getDescription() { return "Ajouter une position aux positions aléatoires"; }

    @Override
    public String getSyntax() { return "/oitc addpos"; }

    @Override
    public Boolean isOnlyPlayerCommand() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            Yaml config = Yaml.CONFIG;
            config.setConfigSection("pos");


        }
        sender.sendMessage("Player Only command");
    }



}
