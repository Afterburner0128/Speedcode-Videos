package Com.Vance.Speedcoding.E6_Crates;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener{
	
	private static ArrayList<ItemStack> tier1 = new ArrayList<ItemStack>(); 
	private static ArrayList<ItemStack> tier2 = new ArrayList<ItemStack>(); 
	private static ArrayList<ItemStack> tier3 = new ArrayList<ItemStack>(); 
	private static ArrayList<ItemStack> tier4 = new ArrayList<ItemStack>(); 
	
	private HashMap<String, String> inventory = new HashMap<String, String>();
	private HashMap<String, Location> cooldown = new HashMap<String, Location>();
	
	@EventHandler
	public void on(BlockPlaceEvent e){
		File directory = new File("plugins/SpeedCode/Inventories.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		if(e.getItemInHand().getItemMeta().getDisplayName().equals("§fCrate (Random)")){
			int size = config.getKeys(false).size() + 1;
			
			config.set(size + "Random.loc.world", e.getBlock().getLocation().getWorld().getName());
			config.set(size + "Random.loc.x", e.getBlock().getLocation().getX());
			config.set(size + "Random.loc.y", e.getBlock().getLocation().getY());
			config.set(size + "Random.loc.z", e.getBlock().getLocation().getZ());
			
			config.set(size + "Random.inv",createInventoryItems().getContents());
			try {
				config.save(directory);
			} catch (Exception e2) {
				
			}
			e.getPlayer().sendMessage("§aA RANDOM CRATE HAS BEEN CREATED");
		}
		if(e.getItemInHand().getItemMeta().getDisplayName().equals("§fCrate (Storage)")){
			int size = config.getKeys(false).size() + 1;
			
			config.set(size + "Storage.loc.world", e.getBlock().getLocation().getWorld().getName());
			config.set(size + "Storage.loc.x", e.getBlock().getLocation().getX());
			config.set(size + "Storage.loc.y", e.getBlock().getLocation().getY());
			config.set(size + "Storage.loc.z", e.getBlock().getLocation().getZ());
			
			config.set(size + "Storage.inv",null);
			try {
				config.save(directory);
			} catch (Exception e2) {
				
			}
			e.getPlayer().sendMessage("§aA STORAGE CRATE HAS BEEN CREATED");
		}
	}
	@SuppressWarnings("unchecked")
	@EventHandler
	public void on(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			File directory = new File("plugins/SpeedCode/Inventories.yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
			
			for(String str : config.getKeys(false)){
				Location loc = new Location(Bukkit.getWorld(config.getString(str + ".loc.world")), config.getDouble(str + ".loc.x"), config.getDouble(str + ".loc.y"), config.getDouble(str + ".loc.z"));
				
				if(e.getClickedBlock().getLocation().equals(loc)){
					if(str.contains("Random")){
						if(cooldown.containsKey(e.getPlayer().getName())){
							e.getPlayer().sendMessage("§cYou must wait 10 seconds before you can use the next crate.");
							return;
						}
						Inventory inv =  Bukkit.createInventory(null, 27, "Crate");
						ItemStack[] content = ((ArrayList<ItemStack>) config.get(str + ".inv")).toArray(new ItemStack[0]); 
						
						inv.setContents(content);
						e.getPlayer().openInventory(inv);
						
						config.set(str + ".inv", createInventoryItems().getContents()); //CREATES A NEW SET OF ITEMS
						cooldown.put(e.getPlayer().getName(), loc);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){

							@Override
							public void run() {
								cooldown.remove(e.getPlayer().getName());
								
							}
							
						}, 200);
						try {
							config.save(directory);
						} catch (Exception e2) {
							
						}
					}
					if(str.contains("Storage")){
						if(config.get(str + ".inv") == null){
							Inventory inv =  Bukkit.createInventory(null, 27, "Crate");
							e.getPlayer().openInventory(inv);
							inventory.put(e.getPlayer().getName(), str);
						}
						ItemStack[] content = ((ArrayList<ItemStack>) config.get(str + ".inv")).toArray(new ItemStack[0]); 
						Inventory inv =  Bukkit.createInventory(null, 27, "Crate");
						inv.setContents(content);
						e.getPlayer().openInventory(inv);
						inventory.put(e.getPlayer().getName(), str);
					}
				}
			}
		}
		
	}
	@EventHandler
	public void on(InventoryCloseEvent e){
		File directory = new File("plugins/SpeedCode/Inventories.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		if(inventory.containsKey(e.getPlayer().getName())){
			config.set(inventory.get(e.getPlayer().getName())+ ".inv", e.getInventory().getContents());
			inventory.remove(e.getPlayer().getName());
			try {
				config.save(directory);
			} catch (Exception e2) {
				
			}
		}
	}
	public static Inventory createInventoryItems(){
		Inventory inv =  Bukkit.createInventory(null, 27, "Crate");
		
		Random r = new Random();
		
		int items = r.nextInt(5) + 1;
		int tier = r.nextInt(4) + 1;
		
		//FOOD
				tier1.add(new ItemStack(Material.APPLE, 3));
				tier1.add(new ItemStack(Material.CARROT, 4));
				//GUNS
				tier1.add(new ItemStack(Material.DIAMOND_AXE, 1));
				tier1.add(new ItemStack(Material.DIAMOND_SPADE, 1));
				tier1.add(new ItemStack(Material.CARROT_STICK, 1));
				tier1.add(new ItemStack(Material.DIAMOND_SPADE, 1));
				tier1.add(new ItemStack(Material.WOOD_SWORD, 1));
				//AMMO
				tier1.add(new ItemStack(Material.SULPHUR, 8));
				tier1.add(new ItemStack(Material.SULPHUR, 13));
				tier1.add(new ItemStack(Material.SULPHUR, 15));
				tier1.add(new ItemStack(Material.SULPHUR, 7));
				tier1.add(new ItemStack(Material.SULPHUR, 4));
				
				tier1.add(new ItemStack(Material.STICK, 25));
				tier1.add(new ItemStack(Material.PUMPKIN_SEEDS, 30));
			
				//ARMOR
				tier1.add(new ItemStack(Material.LEATHER_BOOTS, 1));
				tier1.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				tier1.add(new ItemStack(Material.LEATHER_HELMET, 1));
				tier1.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				
				//FOOD
				tier2.add(new ItemStack(Material.COOKED_FISH, 3));
				tier2.add(new ItemStack(Material.COOKED_MUTTON, 2));
				tier2.add(new ItemStack(Material.COOKED_RABBIT, 3));
				//GUNS
				tier2.add(new ItemStack(Material.STONE_SPADE, 1));
				tier2.add(new ItemStack(Material.STONE_HOE, 1));
				tier2.add(new ItemStack(Material.STONE_AXE, 1));
				tier2.add(new ItemStack(Material.WOOD_AXE, 1));
				tier2.add(new ItemStack(Material.IRON_PICKAXE, 1));
				tier2.add(new ItemStack(Material.STONE_PICKAXE, 1));
				tier2.add(new ItemStack(Material.GOLD_HOE, 1));
				tier3.add(new ItemStack(Material.STONE_SWORD, 1));
				//AMMO
				tier2.add(new ItemStack(Material.DIAMOND, 16));
				tier2.add(new ItemStack(Material.DIAMOND, 16));
				tier2.add(new ItemStack(Material.INK_SACK, 5, (short) 9));
				tier2.add(new ItemStack(Material.INK_SACK, 10, (short) 10));
				tier2.add(new ItemStack(Material.INK_SACK, 5, (short) 4));
				tier2.add(new ItemStack(Material.INK_SACK, 5, (short) 13));
				tier2.add(new ItemStack(Material.INK_SACK, 5, (short) 5));
				tier2.add(new ItemStack(Material.QUARTZ, 5));
				//ARMOR
				tier2.add(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				tier2.add(new ItemStack(Material.GOLD_HELMET, 1));
				tier2.add(new ItemStack(Material.GOLD_LEGGINGS, 1));
				tier2.add(new ItemStack(Material.GOLD_BOOTS, 1));
				//FOOD
				tier3.add(new ItemStack(Material.MELON, 5));
				tier3.add(new ItemStack(Material.COOKIE, 5));
				tier3.add(new ItemStack(Material.GOLDEN_APPLE, 1));
				tier3.add(new ItemStack(Material.PUMPKIN_PIE, 1));
			    //GUNS
				tier3.add(new ItemStack(Material.WOOD_PICKAXE, 1));
				tier3.add(new ItemStack(Material.GOLD_AXE, 1));
				tier3.add(new ItemStack(Material.SHEARS, 1));
				tier3.add(new ItemStack(Material.SADDLE, 1));
				tier3.add(new ItemStack(Material.MINECART, 1));
				//AMMO
				tier3.add(new ItemStack(Material.STICK, 25));
				tier3.add(new ItemStack(Material.PUMPKIN_SEEDS, 30));
				tier3.add(new ItemStack(Material.WHEAT, 32));
				tier3.add(new ItemStack(Material.FLINT, 50));
				tier3.add(new ItemStack(Material.INK_SACK, 50, (short) 1));
				//ARMOR
				tier3.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
				tier3.add(new ItemStack(Material.IRON_LEGGINGS, 1));
				tier3.add(new ItemStack(Material.GOLD_SWORD, 1));
				//FOOD
				tier4.add(new ItemStack(Material.COOKED_BEEF, 1));
				tier4.add(new ItemStack(Material.COOKED_CHICKEN, 3));
				tier4.add(new ItemStack(Material.GRILLED_PORK, 2));
				//GUNS
				tier4.add(new ItemStack(Material.IRON_AXE, 1));
				tier4.add(new ItemStack(Material.GOLD_SPADE, 1));
				tier4.add(new ItemStack(Material.IRON_SPADE, 1));
				tier4.add(new ItemStack(Material.EXPLOSIVE_MINECART, 1));
				tier4.add(new ItemStack(Material.WOOD_SPADE, 1));
				//AMMO
				tier4.add(new ItemStack(Material.COAL, 20));
				tier4.add(new ItemStack(Material.CLAY_BRICK, 40));
				tier4.add(new ItemStack(Material.INK_SACK, 20));
				tier4.add(new ItemStack(Material.GLOWSTONE_DUST, 1));
				tier4.add(new ItemStack(Material.INK_SACK, 50, (short) 2));
				//ARMOR
				tier4.add(new ItemStack(Material.IRON_BOOTS));
				tier4.add(new ItemStack(Material.IRON_LEGGINGS));
				tier4.add(new ItemStack(Material.IRON_SWORD, 1));
		
		for (int i = 0; i < items; i++) {
			int choose;
			ItemStack item = null;
			
			if(tier == 1){
				choose = r.nextInt(tier1.size()); //GETS RANDOM MATERIALS FROM THE TIER 1 ARRAY
				item = tier1.get(choose);
			}
			if(tier == 2){
				choose = r.nextInt(tier2.size()); //GETS RANDOM MATERIALS FROM THE TIER 2 ARRAY
				item = tier2.get(choose);
			}
			if(tier == 3){
				choose = r.nextInt(tier3.size()); //GETS RANDOM MATERIALS FROM THE TIER 3 ARRAY
				item = tier3.get(choose);
			}
			if(tier == 4){
				choose = r.nextInt(tier4.size()); //GETS RANDOM MATERIALS FROM THE TIER 4 ARRAY
				item = tier4.get(choose);
			}
			int index = r.nextInt(27);
			inv.setItem(index, item);
		}
		return inv;
	}

}
