package eu.muxcraft.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class MailItem {

    private UUID uniqueId;
    private String nameFrom;
    private String nameTo;
    private Date created;
    private String message;

}
