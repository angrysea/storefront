package com.ibm.network.ftp.event;

import java.util.EventObject;

public class StatusEvent extends EventObject
{

    private String message;

    public StatusEvent(Object obj)
    {
        super(obj);
    }

    public StatusEvent(Object obj, String s)
    {
        super(obj);
        message = s;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String s)
    {
        message = s;
    }
}
