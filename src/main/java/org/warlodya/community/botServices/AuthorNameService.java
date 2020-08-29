package org.warlodya.community.botServices;

import org.springframework.stereotype.Component;
import org.warlodya.community.entities.BotUser;
import org.warlodya.community.interfaces.NameService;

@Component
public class AuthorNameService implements NameService {
    public String getAuthorName(BotUser user) {
        String fName = user.getFirstName();
        String lName = user.getSecondName();
        String userName = user.getUserName();

        String authorName;
        if (userName != null) {
            authorName = '@' + userName;
        } else {
            String firstName = fName != null ? fName : "";
            String lastName = lName != null ? lName : "";
            authorName = firstName + " " + lastName;
        }
        return authorName;
    }
}
