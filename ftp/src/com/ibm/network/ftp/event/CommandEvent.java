package com.ibm.network.ftp.event;

import java.util.EventObject;
import java.util.Vector;

public class CommandEvent extends EventObject
{

    public static final String CHANGE_DIR_LIST = "changeDir&list";
    public static final String CHANGE_DIR = "changeDir";
    public static final String CONNECT_LOGIN = "connect&login";
    public static final String CONNECT = "connect";
    public static final String LOGIN = "login";
    public static final String DISCONNECT = "disconnect";
    public static final String SET_TYPE = "setType";
    public static final String MAKE_DIR = "makeDir";
    public static final String MAKE_DIR_LIST = "makeDir&list";
    public static final String REMOVE_DIR = "removeDir";
    public static final String REMOVE_LIST = "remove&list";
    public static final String DOWNLOAD_LIST = "getFile&list";
    public static final String DOWNLOAD = "getFile";
    public static final String DOWNLOAD_LOCAL_NAME = "lGetFile";
    public static final String UPLOAD = "putFile";
    public static final String UPLOAD_REMOTE_NAME = "rPutFile";
    public static final String UPLOAD_LIST = "putFile&list";
    public static final String DELETE_FILE = "deleteFile";
    public static final String DELETE_FILE_LIST = "deleteFile&list";
    public static final String RENAME = "rename";
    public static final String RENAME_LIST = "rename&list";
    public static final String STATUS = "getStatus";
    public static final String SOCKS = "configureSocks";
    public static final String ABORT = "abort";
    public static final String SYSTEM = "system";
    public static final String PASSIVE = "passive";
    public static final String QUOTE = "quote";
    public static final String SITE = "site";
    public static final String LIST = "list";
    private String command;
    private Vector parameters;
    private boolean remote;

    public CommandEvent(Object obj)
    {
        super(obj);
        command = null;
        parameters = null;
    }

    public CommandEvent(Object obj, String s, Vector vector, boolean flag)
    {
        super(obj);
        command = null;
        parameters = null;
        command = s;
        parameters = vector;
        remote = flag;
    }

    public String getCommand()
    {
        return command;
    }

    public Vector getParameters()
    {
        return parameters;
    }

    public boolean isRemote()
    {
        return remote;
    }

    public void setCommand(String s)
    {
        command = s;
    }

    public void setParameters(Vector vector)
    {
        parameters = vector;
    }

    public void setRemote(boolean flag)
    {
        remote = flag;
    }
}
