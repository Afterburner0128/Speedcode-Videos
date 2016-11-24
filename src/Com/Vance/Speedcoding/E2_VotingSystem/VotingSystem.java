package Com.Vance.Speedcoding.E2_VotingSystem;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class VotingSystem extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getCommand("vote").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(this, this);
	}
	public void createConfig(){
		File directory = new File("plugins/Speedcoding/Database/Voting.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		if(!directory.exists()){
			try {
				config.set("#Voting Database ", " VotingSystem v1.0");
				
				config.set("Yes", 0);
				config.set("No", 0);
				config.set("Question", "Its just a prank bro ;)");
				config.createSection("Voted");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@EventHandler
	@SuppressWarnings("deprecation")
	public void on(PlayerInteractEvent e){
		if(e.getPlayer().getItemInHand().getType() == null){
			return;
		}
		if(!e.getPlayer().getItemInHand().hasItemMeta()){
			return;
		}
		if(!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
			return;
		}
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§rVote")){
			Inventory inv = Bukkit.createInventory(null, 27, "Vote");
			
			ItemStack yes = new ItemStack(Material.BOOK_AND_QUILL);
			ItemMeta yesmeta = yes.getItemMeta();
			yesmeta.setDisplayName("§eYes");
			yes.setItemMeta(yesmeta);
			
			ItemStack no = new ItemStack(Material.BOOK_AND_QUILL);
			ItemMeta nometa = no.getItemMeta();
			nometa.setDisplayName("§eNo");
			no.setItemMeta(nometa);
			
			ItemStack question = new ItemStack(Material.BOOK_AND_QUILL);
			ItemMeta questionmeta = question.getItemMeta();
			questionmeta.setDisplayName("§eQuestion");
			//questionmeta.setLore(Arrays.asList(getQuestion()));
			question.setItemMeta(questionmeta);
			
			inv.setItem(2, yes);
			inv.setItem(16, question);
			inv.setItem(19, no);
			
			e.getPlayer().openInventory(inv);
		}
	}
	@EventHandler
	public void on(InventoryClickEvent e){
		if(!e.getInventory().getName().equals("Vote")){
			return;
		}
		e.setCancelled(true);
		
		File directory = new File("plugins/Speedcoding/Database/Voting.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		if(config.getStringList("Voted").contains(e.getWhoClicked().getName())){
			return;
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eYes")){
			
			
			config.set("Yes", config.getDouble("Yes") + 1);
			ArrayList<String> list = (ArrayList<String>) config.getStringList("Voted");
			list.add(e.getWhoClicked().getName());
			
			config.set("Voted", list);
			e.getWhoClicked().closeInventory();
			e.getWhoClicked().sendMessage("§aVote Registered.");
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eNo")){
			config.set("No", config.getDouble("No") + 1);
			ArrayList<String> list = (ArrayList<String>) config.getStringList("Voted");
			list.add(e.getWhoClicked().getName());
			
			config.set("Voted", list);
			e.getWhoClicked().closeInventory();
			e.getWhoClicked().sendMessage("§aVote Registered.");
		}
		try {
			config.save(directory);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

}
