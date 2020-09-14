package org.warlodya.community.entities;

import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class BotUser {
    public static final int UNKNOWN_PRIVATE_CHAT_ID = -1;

    @Id
    @Unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int userId;
    @Column(unique = true)
    @NotNull
    private int telegramId;
    private long privateChatId;
    private String firstName;
    private String secondName;
    private String userName;
    private String languageCode;
    @NotNull
    private LocalDateTime createdDt;

    public BotUser(
            int telegramId,
            long privateChatId,
            String firstName,
            String secondName,
            String userName,
            String languageCode
    ) {
        this.telegramId = telegramId;
        this.privateChatId = privateChatId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.userName = userName;
        this.languageCode = languageCode;
    }

    public BotUser() {
    }

    @PrePersist
    protected void onCreate() {
        createdDt = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public long getPrivateChatId() {
        return privateChatId;
    }

    public void setPrivateChatId(long privateChatId) {
        this.privateChatId = privateChatId;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUserName() {
        return userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }
}
