package eu.muxcraft.mail.inventory;

import eu.muxcraft.mail.model.MailItem;
import eu.muxcraft.mail.service.MailService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MailInventory implements Listener {

    private static final int INVENTORY_SIZE = 6 * 9;
    private static final String INVENTORY_TITLE = "§0§lMuxMail §0| /mail";
    private static final Material MATERIAL_MAIL_IDENTIFIER = Material.BOOK;

    private MailService mailService;

    public Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_TITLE);

        int count = 0;
        for (MailItem mailItem : this.mailService.fetchByName(player.getName())) {
            if (count >= INVENTORY_SIZE) {
                player.sendMessage("§aDeine Mails sammeln sich an, du solltest sie langsam lesen...");
                break;
            }

            ItemStack itemStack = new ItemStack(MATERIAL_MAIL_IDENTIFIER);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName("§aMail von §b" + mailItem.getNameFrom());
            itemMeta.setLore(Arrays.asList(
                "§f",
                "§7Gesendet: §f" + mailItem.getCreated().toString(),
                "§7ID: §f" + mailItem.getUniqueId().toString()
            ));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(count++, itemStack);
        }

        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (!event.getInventory().getTitle().equals(INVENTORY_TITLE)
            || itemStack == null
            || itemStack.getType() != MATERIAL_MAIL_IDENTIFIER) {
            return;
        }

        Optional<MailItem> optionalMail = this.extractFromItem(player, itemStack);

        optionalMail.ifPresent(mailItem -> {
            player.sendMessage("§aMail von §b" + mailItem.getNameFrom());
            player.sendMessage("§7Gesendet: §f" + mailItem.getCreated().toString());
            player.sendMessage(" ");
            player.sendMessage("§e§o" + mailItem.getMessage());

            this.mailService.removeById(player.getName(), optionalMail.get().getUniqueId());
            player.closeInventory();
        });

        event.setCancelled(true);
    }

    private Optional<MailItem> extractFromItem(Player player, ItemStack itemStack) {
        List<String> lore = itemStack.getItemMeta().getLore();

        for (String line : lore) {
            if (line.contains("ID")) {
                String idAsString = ChatColor.stripColor(line.split(" ")[1]);

                return this.mailService.fetchById(player.getName(), UUID.fromString(idAsString));
            }
        }

        return Optional.empty();
    }

}
