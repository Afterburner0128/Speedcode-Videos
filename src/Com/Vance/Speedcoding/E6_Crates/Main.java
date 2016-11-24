package Com.Vance.Speedcoding.E6_Crates;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	public void onEnable(){
		getCommand("craterandom").setExecutor(new Commands());
		getCommand("cratestorage").setExecutor(new Commands());
		Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
		plugin = this;
	}
	public static Main getInstance(){
		return plugin;
	}

}
