package com.storefront.ftp;

public class FTPException extends Exception
{
    public FTPException()
    {
    }

    public FTPException( String msg )
    {
        super(msg);
    }

    public FTPException( int code, String msg )
    {
        super(msg);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    private int code = -1;
}
