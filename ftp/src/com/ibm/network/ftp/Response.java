package com.ibm.network.ftp;

import java.io.Serializable;

public class Response
    implements Serializable
{

    private int response = -1;
    private String stringResponse = "Uninitialized";

    public Response()
    {
    }

    public Response( int code, String msg )
    {
        response = code;
        stringResponse = msg;
    }

    public int getResponse()
    {
        return response;
    }

    public String getStringResponse()
    {
        return stringResponse;
    }

    public void setResponse(int i)
    {
        response = i;
    }

    public void setStringResponse(String s)
    {
        stringResponse = s;
    }
}
