package Com.Vance.Speedcoding.E5_Transportation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Com.Vance.Speedcoding.E5_Transportation.Main;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("Car")) {
			p.getInventory().addItem(Main.createVehicleItem());
		}
		if(cmd.getName().equalsIgnoreCase("Plane")){
			p.getInventory().addItem(Main.createAirplaneItem());
		}
		return false;
	}

}
