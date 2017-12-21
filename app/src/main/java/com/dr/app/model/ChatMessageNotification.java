package com.dr.app.model;

public class ChatMessageNotification {

    public String count;
    public ChatMessage chatMessages;

    public ChatMessage getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ChatMessage chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ChatMessageNotification(String count, ChatMessage chatMessages) {
        super();
        this.count = count;
        this.chatMessages = chatMessages;
    }

}
