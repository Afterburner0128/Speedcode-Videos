package Com.Vance.Speedcoding.E1_SimpleEconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("Balance")){
			if(args.length == 0){
				p.sendMessage("§aYour Balance: §6" + CurrencyMethods.getMoney(p.getName()));
			}
			if(args.length == 1){
				if(EconomyDatabase.containsPlayer(args[0])){
					p.sendMessage("§aYour Balance: §6" + CurrencyMethods.getMoney(args[0]));	
				}
				else{
					p.sendMessage("§cUnknown player.");
				}
				
			}
		}
		if(cmd.getName().equalsIgnoreCase("Pay")){
			if(args.length <= 1){
				p.sendMessage("§c/Pay <Player> <Amount>");
			}
			if(args.length == 2){
				//args[0]: Player
				//args[1]: Amount
				if(EconomyDatabase.containsPlayer(args[0])){
					if(CurrencyMethods.hasLessMoney(Double.parseDouble(args[1]), p)){
						p.sendMessage("§cYou cannot afford that.");
						return false;
					}
					if(Double.parseDouble(args[1]) <=0){
						//prevents the player for paying a negative 
						//amount resulting in money being withdrawn from the target player
						p.sendMessage("§cUnse of negative integers is prohibited");
						return false;
					}
					p.sendMessage("§ePayment Sucessful");
					CurrencyMethods.addMoney(args[0], Double.parseDouble(args[1]));
					CurrencyMethods.subtractMoney(p.getName(), Double.parseDouble(args[1]));
					
				}
				else{
					p.sendMessage("§c/Pay <Player> <Amount>");
				}
			}
		}
		return false;
	}

}
