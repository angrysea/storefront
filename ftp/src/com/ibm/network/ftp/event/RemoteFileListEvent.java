package com.ibm.network.ftp.event;

import java.util.EventObject;
import java.util.Vector;

public class RemoteFileListEvent extends EventObject
{

    private Vector fileInfo;
    private String currentRemoteDir;

    public RemoteFileListEvent(Object obj)
    {
        super(obj);
    }

    public RemoteFileListEvent(Object obj, Vector vector, String s)
    {
        super(obj);
        fileInfo = vector;
        currentRemoteDir = s;
    }

    public String getRemoteDir()
    {
        return currentRemoteDir;
    }

    public Vector getRemoteFileInfo()
    {
        if(fileInfo == null)
        {
            fileInfo = new Vector();
        }
        return fileInfo;
    }

    public void setRemoteCurrentDir(String s)
    {
        currentRemoteDir = s;
    }

    public void setRemoteFileInfo(Vector vector)
    {
        fileInfo = vector;
    }
}
