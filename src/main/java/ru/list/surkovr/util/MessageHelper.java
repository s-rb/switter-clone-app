package ru.list.surkovr.util;

import ru.list.surkovr.domain.User;

public abstract class MessageHelper {

    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
