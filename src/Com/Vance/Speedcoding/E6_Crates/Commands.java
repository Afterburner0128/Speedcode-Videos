package Com.Vance.Speedcoding.E6_Crates;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("craterandom")){
			ItemStack item = new ItemStack(Material.PISTON_BASE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§fCrate (Random)");
			item.setItemMeta(meta);
			
			p.getInventory().addItem(item);
		}
		if(cmd.getName().equalsIgnoreCase("cratestorage")){
			ItemStack item = new ItemStack(Material.PISTON_BASE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§fCrate (Storage)");
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
		}
		return false;
	}

}
