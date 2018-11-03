package eu.muxcraft.mail.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MailInventory implements Listener {

    private static final int INVENTORY_SIZE = 6 * 9;
    private static final String INVENTORY_TITLE = "§0§lMuxMail §0| /mail";

    public Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_TITLE);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle().equals(INVENTORY_TITLE)) {

        }
    }

}
