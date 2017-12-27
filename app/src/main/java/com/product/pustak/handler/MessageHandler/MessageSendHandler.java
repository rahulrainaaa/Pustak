package com.product.pustak.handler.MessageHandler;

import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.model.Message;

/**
 * Handler class to send message to user (who posted the POST).
 */
public class MessageSendHandler extends BaseHandler {

    public MessageSendHandler(BaseActivity activity) {

        super(activity);
    }

    public Message createMessage(String senderUserPhone, String txtMsgASCII, String postDocumentRef) {

        Message message = new Message();

        message.setFrom("");
        message.setTo("");
        message.setMsg("");
        message.setPost("");
        message.setTime("2017-10-20 20:39:04");

        return message;
    }

}
