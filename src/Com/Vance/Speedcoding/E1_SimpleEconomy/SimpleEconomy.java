package Com.Vance.Speedcoding.E1_SimpleEconomy;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleEconomy extends JavaPlugin implements Listener{
	
	static SimpleEconomy plugin;
	
	public void onEnable(){
		plugin = this;
		EconomyDatabase.createFile();
		getCommand("Balance").setExecutor(new Commands());
		getCommand("Pay").setExecutor(new Commands());
		
	}
	public void onDisable(){
		
	}
	public static SimpleEconomy getInstance(){
		return plugin;
	}

}
