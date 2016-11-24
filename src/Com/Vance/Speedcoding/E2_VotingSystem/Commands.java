package Com.Vance.Speedcoding.E2_VotingSystem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor{

	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("Vote")){
			
			ItemStack is = new ItemStack(Material.BOOK_AND_QUILL);
			ItemMeta ismeta = is.getItemMeta();
			ismeta.setDisplayName("§rVote");
			is.setItemMeta(ismeta);
			
			p.getInventory().setItemInHand(is);
			
		}
		return false;
	}

}
