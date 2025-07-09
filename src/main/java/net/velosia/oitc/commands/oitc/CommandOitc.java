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

/**
 * Main command executor for the /oitc command.
 * Manages sub-commands and provides help information to players.
 * 
 * @author ZeyKra
 */
public class CommandOitc implements CommandExecutor  {

    /** List of all available sub-commands */
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    /**
     * Initializes the command executor and registers all sub-commands.
     */
    public CommandOitc() {
        subCommands.add(new SubCommandAddpos());
        subCommands.add(new SubCommandDebug());
        subCommands.add(new SubCommandReload());
        subCommands.add(new SubCommandTest());
    }

    /**
     * Handles the execution of the /oitc command and its sub-commands.
     * Shows help information when no arguments are provided.
     * 
     * @param sender The command sender (player or console)
     * @param command The command that was executed
     * @param label The command label used
     * @param args The command arguments
     * @return true if the command was handled successfully
     */
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

    /**
     * Gets the list of all registered sub-commands.
     * 
     * @return ArrayList containing all sub-commands
     */
    public ArrayList<SubCommand> getSubCommands() { 
        return subCommands; 
    }

}
