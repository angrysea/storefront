package com.ibm.network.ftp.event;

import java.util.EventObject;
import java.util.Vector;

public class LocalFileListEvent extends EventObject
{

    Vector fileInfo;
    String currentLocalDir;

    public LocalFileListEvent(Object obj)
    {
        super(obj);
    }

    public LocalFileListEvent(Object obj, Vector vector, String s)
    {
        super(obj);
        fileInfo = vector;
        currentLocalDir = s;
    }

    public String getLocalDir()
    {
        return currentLocalDir;
    }

    public Vector getLocalFileInfo()
    {
        return fileInfo;
    }

    public void setCurrentLocalDir(String s)
    {
        currentLocalDir = s;
    }

    public void setLocalFileInfo(Vector vector)
    {
        fileInfo = vector;
    }
}
