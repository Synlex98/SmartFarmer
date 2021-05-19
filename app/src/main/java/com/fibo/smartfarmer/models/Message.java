package com.fibo.smartfarmer.models;

public class Message {
    String messageId, fromName,fromId,timeSent,messageBody;

    public Message(String messageId,String fromName, String fromId, String timeSent, String messageBody) {
        this.fromName = fromName;
        this.fromId = fromId;
        this.timeSent = timeSent;
        this.messageBody = messageBody;
        this.messageId=messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getFromName() {
        return fromName;
    }

    public String getFromId() {
        return fromId;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
