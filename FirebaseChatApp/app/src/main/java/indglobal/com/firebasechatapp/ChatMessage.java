package indglobal.com.firebasechatapp;

import java.util.Date;

/**
 * Created by Hathibelagal on 7/10/16.
 */
public class ChatMessage {

    private String messageText;
    private String messageEmail;
    private long messageTime;

    public ChatMessage(String messageText, String messageEmail) {
        this.messageText = messageText;
        this.messageEmail = messageEmail;
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
