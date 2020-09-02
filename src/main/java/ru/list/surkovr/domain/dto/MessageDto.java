package ru.list.surkovr.domain.dto;

import ru.list.surkovr.domain.Message;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.util.MessageHelper;

public class MessageDto {
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    private Long likes;
    private Boolean isSelfLiked;

    public MessageDto(Message message, Long likes, Boolean isSelfLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.isSelfLiked = isSelfLiked;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getIsSelfLiked() {
        return isSelfLiked;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", selfLiked=" + isSelfLiked +
                '}';
    }
}
