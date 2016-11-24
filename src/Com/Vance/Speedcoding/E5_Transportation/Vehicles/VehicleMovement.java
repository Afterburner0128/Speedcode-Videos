package Com.Vance.Speedcoding.E5_Transportation.Vehicles;

import java.text.DecimalFormat;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.connorlinfoot.bountifulapi.BountifulAPI;

import Com.Vance.Speedcoding.E5_Transportation.Main;
import net.minecraft.server.v1_11_R1.PacketPlayInSteerVehicle;

public class VehicleMovement extends PacketAdapter {

	public VehicleMovement(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
		super(plugin, listenerPriority, types);
	}

	@SuppressWarnings("deprecation")
	public void onPacketReceiving(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE && event.getPlayer().getVehicle() instanceof ArmorStand) {
			ArmorStand seat = (ArmorStand) event.getPlayer().getVehicle();
			if(!Main.getcar.containsKey(seat)){
				return;
			}
			ArmorStand car = Main.getcar.get(seat);

			if (!(seat.getPassenger() instanceof Player)) {
				return;
			}
			Player p = (Player) seat.getPassenger();
			PacketPlayInSteerVehicle packet = (PacketPlayInSteerVehicle) event.getPacket().getHandle();

			// boolean shift = ppisv.d();
			// boolean space = ppisv.c();
			float forward = packet.b();
			float side = packet.a();
			Block b = car.getLocation().getBlock();
			if (forward > 0) { // Forward[W]
				if (!(Main.carvelocity.get(car) >= 1)) {
					Main.carvelocity.put(car, Main.carvelocity.get(car) + 0.001);
				}

			} else if (forward < 0) { // Reverse[S]
				if (Main.carvelocity.get(car) >= 0.1) {
					Main.carvelocity.put(car, Main.carvelocity.get(car) - 0.01);
				} else {
					Main.carvelocity.put(car, 0.0);
				}

			}
			if (side > 0) { // Side[A]
				if (car.getVelocity().length() > 0.1) {
					((CraftArmorStand) car).getHandle().yaw = (float) (car.getLocation().getYaw() + (0.1 * -1));
					((CraftArmorStand) seat).getHandle().yaw = (float) (car.getLocation().getYaw() + (0.1 * -1));
				}

			} else if (side < 0) { // Side[D]
				if (car.getVelocity().length() > 0.1) {
					((CraftArmorStand) car).getHandle().yaw = (float) (car.getLocation().getYaw() + 0.1);
					((CraftArmorStand) seat).getHandle().yaw = (float) (car.getLocation().getYaw() + 0.1);

				}

			}
			car.setVelocity(car.getLocation().getDirection().multiply(Main.carvelocity.get(car)).setY(-2));
			seat.setVelocity(p.getLocation().getDirection().setY(-2));
			((CraftArmorStand) seat).getHandle().setPosition(car.getLocation().getX(), car.getLocation().getY(),
					car.getLocation().getZ());

			// CHECK X AXIS
			if ((b.getRelative(1, 1, 0).getTypeId() == 0 && b.getRelative(1, 0, 0).getTypeId() != 0)) {
				getRelative(p, b, 1, 0, car, seat);
			}
			// CHECK -X AXIS
			if ((b.getRelative(-1, 1, 0).getTypeId() == 0 && b.getRelative(-1, 0, 0).getTypeId() != 0)) {
				getRelative(p, b, -1, 0, car, seat);
			}
			// CHECK Z AXIS
			if ((b.getRelative(0, 1, 1).getTypeId() == 0 && b.getRelative(0, 0, 1).getTypeId() != 0)) {
				getRelative(p, b, 0, 1, car, seat);
			}
			// CHECK -Z AXIS
			if ((b.getRelative(0, 1, -1).getTypeId() == 0 && b.getRelative(0, 0, -1).getTypeId() != 0)) {
				getRelative(p, b, 0, -1, car, seat);
			}
			((CraftArmorStand) seat).getHandle().setPosition(car.getLocation().getX(), car.getLocation().getY(),
					car.getLocation().getZ());

			DecimalFormat dformat = new DecimalFormat("#.##");
			String speed = dformat.format(Main.carvelocity.get(car));
			BountifulAPI.sendActionBar(p, "§9Speed: " + Double.valueOf(speed) * 50 + " Kmph/"
					+ dformat.format((Double.valueOf(speed) * 50) * 0.62) + " Mph");

		}

	}

	@SuppressWarnings("deprecation")
	private void getRelative(Player p, Block b, int X, int Z, ArmorStand car, ArmorStand seat) {
		if (b.getRelative(X, 1, Z).getTypeId() == 38 || b.getRelative(X, 1, Z).getTypeId() == 31
				|| b.getRelative(X, 1, Z).getTypeId() == 175
				|| b.getRelative(X, 1, Z).getType().equals(Material.SUGAR_CANE_BLOCK)
				|| b.getRelative(X, 1, Z).getTypeId() == 39 || b.getRelative(X, 1, Z).getTypeId() == 40
				|| b.getRelative(X, 1, Z).getTypeId() == 37 ||

				b.getRelative(X, 0, Z).getTypeId() == 38 || b.getRelative(X, 0, Z).getTypeId() == 31
				|| b.getRelative(X, 0, Z).getTypeId() == 175
				|| b.getRelative(X, 0, Z).getType().equals(Material.SUGAR_CANE_BLOCK)
				|| b.getRelative(X, 0, Z).getTypeId() == 39 || b.getRelative(X, 0, Z).getTypeId() == 37
				|| b.getRelative(X, 0, Z).getTypeId() == 40) {
			return;

		}

		car.setVelocity(p.getLocation().getDirection().setY(0.3));
		((CraftArmorStand) seat).getHandle().setPosition(car.getLocation().getX(), car.getLocation().getY(),
				car.getLocation().getZ());
		return;

	}

}