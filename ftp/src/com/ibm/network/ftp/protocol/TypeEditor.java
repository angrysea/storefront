package com.ibm.network.ftp.protocol;

import java.beans.PropertyEditorSupport;

public class TypeEditor extends PropertyEditorSupport
{

    public String getAsText()
    {
        if(((String)getValue()).equals("ASCII"))
        {
            return "ASCII";
        } else
        {
            return "BINARY";
        }
    }

    public String[] getTags()
    {
        String result[] = {
            "ASCII", "BINARY"
        };
        return result;
    }

    public void setAsText(String text)
        throws IllegalArgumentException
    {
        if(text.equals("ASCII"))
        {
            setValue("ASCII");
        } else
        if(text.equals("BINARY"))
        {
            setValue("BINARY");
        } else
        {
            throw new IllegalArgumentException(text);
        }
    }

    public TypeEditor()
    {
    }
}
