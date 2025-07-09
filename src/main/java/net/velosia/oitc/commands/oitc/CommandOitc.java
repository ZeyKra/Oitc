package net.velosia.oitc.commands.oitc;

import net.velosia.oitc.commands.SubCommand;
import net.velosia.oitc.commands.oitc.subcommands.SubCommandAddpos;
import net.velosia.oitc.commands.oitc.subcommands.SubCommandDebug;
import net.velosia.oitc.commands.oitc.subcommands.SubCommandReload;
import net.velosia.oitc.commands.oitc.subcommands.SubCommandTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandOitc implements CommandExecutor  {

    // Class for managing sub-commands and their arguments
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandOitc() {
        subCommands.add(new SubCommandAddpos());
        subCommands.add(new SubCommandDebug());
        subCommands.add(new SubCommandReload());
        subCommands.add(new SubCommandTest());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (SubCommand subCommand : subCommands){
                    if (args[0].equalsIgnoreCase(subCommand.getName())){
                        subCommand.perform(p, args);
                        return true;
                    }
                }
                p.sendMessage("Unknown subcommand: " + args[0]);
            } else {
                p.sendMessage("--------------------------------");
                for (SubCommand subCommand : subCommands){
                    p.sendMessage(subCommand.getSyntax() + " - " + subCommand.getDescription());
                }
                p.sendMessage("--------------------------------");
            }
        } else {
            sender.sendMessage("This command can only be executed by players.");
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() { 
        return subCommands; 
    }

}
