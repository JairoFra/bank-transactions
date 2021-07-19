package com.challenge.banktransactions.controller.request;

import com.challenge.banktransactions.enums.ChannelEnum;

/**
 * POJO used to map the Get Status request.
 */
public class GetStatusRequest {

    private String reference;

    private ChannelEnum channel;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public ChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ChannelEnum channel) {
        this.channel = channel;
    }

}
