package eu.muxcraft.mail.command;

import eu.muxcraft.mail.inventory.MailInventory;
import eu.muxcraft.mail.model.MailItem;
import eu.muxcraft.mail.service.MailService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class MailCommand implements CommandExecutor {

    private static final String USAGE = "§cVerwendung: /mail <view|send> <target> <message>";

    private MailService mailService;
    private MailInventory mailInventory;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Command ist nur für Spieler.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(USAGE);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("view")) {
            player.openInventory(this.mailInventory.createInventory(player));
        } else {
            sender.sendMessage(USAGE);
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("send")) {
            String target = args[1];
            String message = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");

            if (!this.mailService.existsUser(target)) {
                player.sendMessage("§cZiel '" + target + "' existiert nicht.");
                return true;
            }

            this.mailService.add(target, new MailItem(
                UUID.randomUUID(),
                player.getName(),
                target,
                new Date(),
                message
            ));

            player.sendMessage("§aDeine Mail wurde abgeschickt!");
        } else {
            sender.sendMessage(USAGE);
        }

        return true;
    }

}
