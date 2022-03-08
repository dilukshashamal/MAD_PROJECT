package com.example.tochsm.Model

class Chat {

    private var senderId: String = ""
    private var receiverId: String = ""
    private var message: String = ""


    constructor()

    constructor(senderId: String, receiverId: String, message: String) {
        this.senderId = senderId
        this.receiverId = receiverId
        this.message = message

    }

    fun getSenderId(): String {
        return senderId
    }

    fun setSenderId(senderId: String) {
        this.senderId = senderId
    }

    fun getReceiverId(): String {
        return receiverId
    }

    fun setReceiverId(receiverId: String) {
        this.receiverId = receiverId
    }

    fun getMessage(): String {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }
}