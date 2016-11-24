package Com.Vance.Speedcoding.E5_Transportation.Listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;

import Com.Vance.Speedcoding.E5_Transportation.Main;

public class VehicleListeners implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand() == null) {
				return;
			}
			// CHECK IF PLAYER PLACED A VEHICLE
			if(!p.getItemInHand().hasItemMeta()){
				return;
			}
			if(!p.getItemInHand().getItemMeta().hasDisplayName()){
				return;
			}
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("§eCar")){
				ArmorStand car = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1, 0), ArmorStand.class);
				car.setVisible(false);
				car.setGravity(true);
				car.setFireTicks(0);

				ArmorStand seat = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1, 0), ArmorStand.class);
				seat.setHelmet(Main.createVehicleItem());
				seat.setCustomName("§rCar");
				seat.setCustomNameVisible(false);
				seat.setMarker(true);
				seat.setGravity(false);
				seat.setVisible(false);
				seat.setFireTicks(0);
				p.getInventory().remove(Main.createVehicleItem());

				Main.getcar.put(seat, car);
				Main.getseat.put(car, seat);
				Main.carvelocity.put(car, 0.0);
			}
			//CHECK IF THE PLAYER PLACED A AIRPLANE
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("§eF-16 Fighter Plane")){
				ArmorStand plane = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0,1,0), ArmorStand.class);
				
				plane.setHelmet(Main.createAirplaneItem());
				plane.setVisible(false);
				plane.setGravity(true);
				plane.setCustomName("§rAirplane");
				plane.setCustomNameVisible(false);
				plane.setFireTicks(0);
				
				p.getInventory().remove(Main.createAirplaneItem());
				Main.getplane.add(plane);
				Main.planevelocity.put(plane, 0.0);
			}
			

		}
	}

	@EventHandler
	public void on(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked() instanceof ArmorStand) {
			if (Main.getcar.containsValue(e.getRightClicked())) {
				Main.getseat.get(e.getRightClicked()).setPassenger(e.getPlayer());
			}
			if(Main.getplane.contains(e.getRightClicked())){
				e.getRightClicked().setPassenger(e.getPlayer());
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000, false));
			}

		}

	}

	@EventHandler
	public void on(EntityDismountEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (e.getDismounted() instanceof ArmorStand) {
			if (Main.getcar.containsKey(e.getDismounted())) {
				Main.getcar.get(e.getDismounted()).teleport(e.getDismounted().getLocation());
				Main.carvelocity.put((ArmorStand) e.getDismounted(), 0.0);
			}
			if (Main.getplane.contains(e.getDismounted())) {
				Player p = (Player) e.getDismounted().getPassenger();
				if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)){
					p.removePotionEffect(PotionEffectType.INVISIBILITY);
				}
			}
		}
	}

	@EventHandler
	public void on(PlayerArmorStandManipulateEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void on(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof ArmorStand) {
			if (Main.getseat.containsKey(e.getEntity())) {
				e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
						Main.getseat.get(e.getEntity()).getHelmet());
				Main.getseat.get(e.getEntity()).remove();
				Main.getcar.remove(Main.getseat.get(e.getEntity()));
				Main.getseat.remove(e.getEntity());
				e.getEntity().remove();

			}
			if(Main.getplane.contains(e.getEntity())){
				e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), ((ArmorStand) e.getEntity()).getHelmet());
				Main.getplane.remove(e.getEntity());
				e.getEntity().remove();
			}
		}
	}

}
