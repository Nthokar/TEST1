package com.sipibibu.messenger.app.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class MessageEntity{
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "text", nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "sender")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "chat")
    private ChatEntity receiver;
    @Column(name = "sending_date")
    private LocalDateTime date;
    @Column(name = "change_date")
    private LocalDateTime lastChange;

    public MessageEntity(String text, LocalDateTime date,
                         UserEntity sender, ChatEntity receiver)throws RuntimeException {
        setText(text);
        this.date = date;
        this.lastChange = date;
        this.sender = (UserEntity) sender;
        this.receiver = receiver;
    }
    public void setText(String newText) throws RuntimeException{
        if (!newText.replaceAll(" ","").isEmpty())
            this.text = newText.strip();
        else
            throw new RuntimeException("Incorrect text");
    }
    public void setLastChange(LocalDateTime date) throws RuntimeException{
        if(date.isAfter(lastChange))
            this.lastChange=date;
        else
            throw new RuntimeException("Incorrect date");
    }
}
