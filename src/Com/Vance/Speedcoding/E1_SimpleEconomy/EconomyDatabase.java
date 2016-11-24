package Com.Vance.Speedcoding.E1_SimpleEconomy;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class EconomyDatabase {
	
	public static int getData(String path){
		File directory = new File("plugins/Speedcode Videos/Database/Balance.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		/*The path is the players name, which contains the players balance
		This setup can be easily converted to get a specific part of the players
		data, and that more can be added
		
		Example:
		
		Afterburner0128:
		   Balance: $420.00
		   Location: (world) CraftPlayer{-1342,75, 124}
		   Health: 20.0
		   Deaths: 69
		
		This setup is as follows
		
		Afterburner0128: $420*/
		
		return config.getInt(path);
	}
	public static void setData(String path, Object value){
		File directory = new File("plugins/Speedcode Videos/Database/Balance.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		config.set(path, value);
		
		try {
			config.save(directory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean containsPlayer(String name){
		File directory = new File("plugins/Speedcode Videos/Database/Balance.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		return config.contains(name);
	}
	public static void createFile() {
		File directory = new File("plugins/Speedcode Videos/Database/Balance.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(directory);
		
		if(!directory.exists()){
			config.set("#Player Database ", "SimpleEconomy v1.0#");
		}
		
		try {
			config.save(directory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
