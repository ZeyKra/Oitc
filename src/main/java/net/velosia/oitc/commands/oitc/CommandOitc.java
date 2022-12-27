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

    //Class permettant de gerer les sous-commands/ arguments de celle si

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
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            }else if(args.length == 0){
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }

        }


        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subCommands;
    }


    public ArrayList<SubCommand> getSubCommands() { return subCommands; }

}
