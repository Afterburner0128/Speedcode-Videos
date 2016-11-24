package Com.Vance.Speedcoding.E5_Transportation.Aircraft;

import java.text.DecimalFormat;

import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.connorlinfoot.bountifulapi.BountifulAPI;

import Com.Vance.Speedcoding.E5_Transportation.Main;
import net.minecraft.server.v1_11_R1.PacketPlayInSteerVehicle;

public class AircraftMovement extends PacketAdapter {

	public AircraftMovement(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
		super(plugin, listenerPriority, types);
	}
	@SuppressWarnings("deprecation")
	public void onPacketReceiving(PacketEvent e){
		if(e.getPacketType() == PacketType.Play.Client.STEER_VEHICLE && e.getPlayer().getVehicle() instanceof ArmorStand){
			PacketPlayInSteerVehicle packet = (PacketPlayInSteerVehicle) e.getPacket().getHandle();
			ArmorStand airplane = (ArmorStand) e.getPlayer().getVehicle();
			
			if(!(airplane.getPassenger() instanceof Player)){
				return;
			}
			if(!Main.getplane.contains(airplane)){
				return;
			}
			Player p = (Player) airplane.getPassenger();
			
			//boolean shift = packet.d();
			//boolean space = packet.c();
			float forward = packet.b();
			float side = packet.a();
			
			if(forward > 0){ //Forward [W]
				//Provides the ability for the vehicle to travel at different speeds
				if(!(Main.planevelocity.get(airplane) >= 5)){//Sets max speed
					Main.planevelocity.put(airplane, Main.planevelocity.get(airplane) + 0.005); //Stores speed, and adds acceleration
				}
				
			}
			else if(forward < 0){ //Reverse [S]
				//Provides the ability for the vehicle to travel at different speeds
				if((Main.planevelocity.get(airplane) >= 0)){//Sets max speed
					Main.planevelocity.put(airplane, Main.planevelocity.get(airplane) - 0.005); //Stores speed, and adds deceleration
				}
				
			}
			double turn = p.getLocation().getYaw() - airplane.getLocation().getYaw();
			if(side > 0){
				((CraftArmorStand)airplane).getHandle().yaw = airplane.getLocation().getYaw() - 1;
				
				if(airplane.getHeadPose().getZ() >= 1.5){ //Sets planes max roll rate
					//Provides aircraft roll when turning (so it looks like the plane is turning, with the resourcepack)
					airplane.setHeadPose(new EulerAngle(Math.toRadians(p.getLocation().getPitch()), airplane.getHeadPose().getY(), 1.5));
				}
				else{
					airplane.setHeadPose(new EulerAngle(Math.toRadians(p.getLocation().getPitch()), airplane.getHeadPose().getY(), airplane.getHeadPose().getZ() + (turn/turn)/ 20));
				}
			}
			else if(side < 0){
				((CraftArmorStand)airplane).getHandle().yaw = airplane.getLocation().getYaw() + 1;
				
				if(airplane.getHeadPose().getZ() <= -1.5){ //Sets planes max roll rate
					//Provides aircraft roll when turning in the oppisite direction
					airplane.setHeadPose(new EulerAngle(Math.toRadians(p.getLocation().getPitch()), airplane.getHeadPose().getY(), -1.5));
				}
				else{
					airplane.setHeadPose(new EulerAngle(Math.toRadians(p.getLocation().getPitch()), airplane.getHeadPose().getY(), airplane.getHeadPose().getZ() - (turn/turn)/ 20));
				}
			}
			if(airplane.getVelocity().length() <= 0.3){
				Main.planevelocity.put(airplane, Main.planevelocity.get(airplane) + 0.03);
				airplane.setVelocity(airplane.getLocation().getDirection().multiply(Main.planevelocity.get(airplane)).setY(-3.5));
				
				DecimalFormat dformat = new DecimalFormat("#.##");
				String speed = dformat.format(Main.planevelocity.get(airplane));
				
				BountifulAPI.sendActionBar(p, "§4Stall: " + Double.valueOf(speed) * 500 + "Kmph");//Multiply by a large number to make the plane appear is if it is going fast
			}
			airplane.setVelocity(airplane.getLocation().getDirection().multiply(Main.planevelocity.get(airplane)).setY(p.getLocation().getDirection().getY()));
			airplane.setHeadPose(new EulerAngle(Math.toRadians(p.getLocation().getPitch()), airplane.getHeadPose().getY(), airplane.getHeadPose().getZ()));
			
			DecimalFormat dformat = new DecimalFormat("#.##");
			String speed = dformat.format(Main.planevelocity.get(airplane));
			p.getWorld().spigot().playEffect(p.getLocation(), Effect.EXPLOSION, 0, 0, 0, 0, 0, 0.001f, 1, 256);
			
			BountifulAPI.sendActionBar(p, "§9Speed: §6" + Double.valueOf(speed) * 500 + "Kmph");//Multiply by a large number to make the plane appear is if it is going fast
		}
	}
}
