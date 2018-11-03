package eu.muxcraft.mail;

import eu.muxcraft.mail.inventory.MailInventory;
import eu.muxcraft.mail.service.MailService;
import eu.muxcraft.mail.service.impl.LocalMailService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "MuxMail", version = "0.1-SNAPSHOT")
@Author(name = "Wende2k")
@Description(desc = "Allows you to send offline mail-messages.")
public class MuxMail extends JavaPlugin {

    @Getter
    private static MailService mailService;

    @Getter
    private static MailInventory mailInventory;

    @Override
    public void onEnable() {
        MuxMail.mailService = new LocalMailService();
        MuxMail.mailInventory = new MailInventory(MuxMail.mailService);

        super.getServer().getPluginManager().registerEvents(MuxMail.mailInventory, this);
    }

}
