package Com.Vance.Speedcoding.E5_Transportation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;

import Com.Vance.Speedcoding.E5_Transportation.Aircraft.AircraftMovement;
import Com.Vance.Speedcoding.E5_Transportation.Listeners.VehicleListeners;
import Com.Vance.Speedcoding.E5_Transportation.Vehicles.VehicleMovement;

public class Main extends JavaPlugin{
	
	static Main plugin;
	public static HashMap<ArmorStand, ArmorStand> getcar = new HashMap<ArmorStand, ArmorStand>();
	public static HashMap<ArmorStand, ArmorStand> getseat = new HashMap<ArmorStand, ArmorStand>();
	public static HashMap<ArmorStand, Double> carvelocity = new HashMap<ArmorStand, Double>();
	
	public static HashMap<ArmorStand, Double> planevelocity = new HashMap<ArmorStand, Double>(); 
	
	public static ArrayList<ArmorStand> getplane = new ArrayList<ArmorStand>();
	
	public void onEnable(){
		plugin = this;
		ProtocolLibrary.getProtocolManager().addPacketListener(new VehicleMovement(this,  ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Client.STEER_VEHICLE}));
		ProtocolLibrary.getProtocolManager().addPacketListener(new AircraftMovement(this, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Client.STEER_VEHICLE}));
		Bukkit.getPluginManager().registerEvents(new VehicleListeners(), this);
		
		getCommand("Car").setExecutor(new Commands());
		getCommand("Plane").setExecutor(new Commands());
		
	}
	public static Main getInstance(){
		return plugin;
	}
	public static ItemStack createVehicleItem(){
		ItemStack is = new ItemStack(Material.NETHER_BRICK, 1);
		ItemMeta ismeta = is.getItemMeta();
		ismeta.setDisplayName("§eCar");
		ismeta.setLore(Arrays.asList(new String[]{
				"§aSpeed: §r" + 1*50 + " Kmph/" + 1*50*0.62 + " Mph",
				"§aDurability: §r" + 20,
		}));
		is.setItemMeta(ismeta);
		return is;
		
	}
	public static ItemStack createAirplaneItem(){
		ItemStack is = new ItemStack(Material.PURPUR_STAIRS, 1);
		ItemMeta ismeta = is.getItemMeta();
		ismeta.setDisplayName("§eF-16 Fighter Plane");
		ismeta.setLore(Arrays.asList(new String[]{
				"§aSpeed: §r" + 4*500 + " Kmph/", 
				"§aDurability: §r" + 20,
		}));
		is.setItemMeta(ismeta);
		return is;
	}

}
