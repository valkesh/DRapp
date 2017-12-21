package com.dr.app.model;

public class ChatMessage {

    public boolean left;
    public String message;
    public String senderName;
    public String receiverName;
    public String timestamp;
    public String isread;
    public String type;
    public String name;
    public String user_id;
    public String recv_id;
    public String otherUserImage;
    public boolean user_status;

    public ChatMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ChatMessage(boolean left, String message, String senderName, String receiverName, String timestamp,
                       String isread, String type, String name, String user_id, String recv_id, String otherUserImage, boolean user_status) {
        super();
        this.left = left;
        this.message = message;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.timestamp = timestamp;
        this.isread = isread;
        this.type = type;
        this.name = name;
        this.user_id = user_id;
        this.recv_id = recv_id;
        this.otherUserImage = otherUserImage;
        this.user_status = user_status;
    }

    public boolean isUser_status() {
        return user_status;
    }

    public void setUser_status(boolean user_status) {
        this.user_status = user_status;
    }

    public String getOtherUserImage() {
        return otherUserImage;
    }

    public void setOtherUserImage(String otherUserImage) {
        this.otherUserImage = otherUserImage;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRecv_id() {
        return recv_id;
    }

    public void setRecv_id(String recv_id) {
        this.recv_id = recv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
