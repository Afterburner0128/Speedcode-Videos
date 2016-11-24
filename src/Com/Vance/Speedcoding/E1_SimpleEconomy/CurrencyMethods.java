package Com.Vance.Speedcoding.E1_SimpleEconomy;

import org.bukkit.entity.Player;

public class CurrencyMethods {
	
	public static double getMoney(String playername){
		double balance = EconomyDatabase.getData(playername);
		return balance;
	}
	public static void addMoney(String playername, double amount){
		double balance = getMoney(playername);
		balance = balance + amount;
		EconomyDatabase.setData(playername, balance);
	}
	public static void subtractMoney(String playername, double amount){
		double balance = getMoney(playername);
		balance = balance - amount;
		EconomyDatabase.setData(playername, balance);
	}
	public static boolean hasLessMoney(double amount, Player p){
		double balance = getMoney(p.getName());
		return balance < amount;
	}
	public static boolean hasMoreMoney(double amount, Player p){
		double balance = getMoney(p.getName());
		return balance > amount;
	}

}
