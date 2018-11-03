package eu.muxcraft.mail;

import eu.muxcraft.mail.command.MailCommand;
import eu.muxcraft.mail.inventory.MailInventory;
import eu.muxcraft.mail.service.MailService;
import eu.muxcraft.mail.service.impl.LocalMailService;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "MuxMail", version = "0.1-SNAPSHOT")
@Author(name = "Wende2k")
@Command(name = "mail")
@Description(desc = "Allows you to send offline mail-messages.")
public class MuxMail extends JavaPlugin {

    @Override
    public void onEnable() {
        MailService mailService = new LocalMailService();
        MailInventory mailInventory = new MailInventory(mailService);

        super.getServer().getPluginManager().registerEvents(mailInventory, this);
        super.getCommand("mail").setExecutor(
            new MailCommand(mailService, mailInventory)
        );
    }

}
