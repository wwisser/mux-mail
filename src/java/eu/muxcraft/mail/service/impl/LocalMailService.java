package eu.muxcraft.mail.service.impl;

import eu.muxcraft.mail.model.MailItem;
import eu.muxcraft.mail.service.MailService;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

public class LocalMailService implements MailService {

    private Map<String, List<MailItem>> mailCache = new HashMap<>();

    @Override
    public void add(String receiver, MailItem mailItem) {
        List<MailItem> items = this.mailCache.getOrDefault(receiver, new ArrayList<>());

        items.add(mailItem);
        this.mailCache.put(receiver, items);
    }

    @Override
    public void removeById(String name, UUID uuid) {
        Optional<MailItem> optionalMailItem = this.fetchById(name, uuid);

        optionalMailItem.ifPresent(mailItem -> this.mailCache.get(name).remove(mailItem));
    }

    @Override
    public List<MailItem> fetchByName(String name) {
        return this.mailCache.getOrDefault(name, Collections.emptyList());
    }

    @Override
    public Optional<MailItem> fetchById(String name, UUID uuid) {
        MailItem result = this.mailCache
            .get(name)
            .stream()
            .filter(mailItem -> mailItem.getUniqueId().equals(uuid))
            .collect(Collectors.toList()).get(0);

        return result == null ? Optional.empty() : Optional.of(result);
    }

    /**
     * Since this storage is local, we would have to iterate through all offline players via
     * {@link Bukkit#getOfflinePlayers()} which could cause big performance issues.
     */
    @Deprecated
    @Override
    public boolean existsUser(String name) {
        return true;
    }

}
