package eu.muxcraft.mail.service;

import eu.muxcraft.mail.model.MailItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MailService {

    void add(String receiver, MailItem mailItem);

    void removeById(String name, UUID uuid);

    List<MailItem> fetchByName(String name);

    Optional<MailItem> fetchById(String name, UUID uuid);

    boolean existsUser(String name);

}
