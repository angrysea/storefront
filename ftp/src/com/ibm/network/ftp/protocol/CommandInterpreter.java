package com.ibm.network.ftp.protocol;

import com.ibm.network.ftp.event.CommandEvent;
import java.io.PrintStream;
import java.util.Vector;

// Referenced classes of package com.ibm.network.ftp.protocol:
//            FTPProtocol

public class CommandInterpreter
{

    private String command;
    private Vector parameters;
    private boolean remote;
    private FTPProtocol parent;

    protected CommandInterpreter(FTPProtocol parent)
    {
        this.parent = parent;
    }

    public void interpretCommand(CommandEvent cevent)
    {
        command = cevent.getCommand();
        parameters = cevent.getParameters();
        remote = cevent.isRemote();
        if(command.equals("abort"))
        {
            parent.abort();
        } else
        if(command.equals("changeDir"))
        {
            parent.changeDir((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("changeDir&list"))
        {
            parent.changeDirList((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("configureSocks"))
        {
            parent.configureSocks((String)parameters.elementAt(0), (String)parameters.elementAt(1));
        } else
        if(command.equals("connect"))
        {
            parent.connect((String)parameters.elementAt(0), (String)parameters.elementAt(1));
        } else
        if(command.equals("connect&login"))
        {
            parent.connectLogin((String)parameters.elementAt(0), (String)parameters.elementAt(1), (String)parameters.elementAt(2), (String)parameters.elementAt(3));
        } else
        if(command.equals("deleteFile"))
        {
            parent.deleteFile((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("deleteFile&list"))
        {
            parent.deleteFileList(parameters, remote);
        } else
        if(command.equals("disconnect"))
        {
            parent.disconnect();
        } else
        if(command.equals("fileList"))
        {
            parent.fileList(remote);
        } else
        if(command.equals("getCurrentDir"))
        {
            parent.getCurrentDir(remote);
        } else
        if(command.equals("getFile"))
        {
            parent.getFile(parameters.elementAt(0).toString());
        } else
        if(command.equals("getFile"))
        {
            parent.getFile(parameters);
        } else
        if(command.equals("getFile&list"))
        {
            parent.getFileList(parameters);
        } else
        if(command.equals("getStatus"))
        {
            parent.status();
        } else
        if(command.equals("login"))
        {
            parent.login((String)parameters.elementAt(0), (String)parameters.elementAt(1));
        } else
        if(command.equals("makeDir&list"))
        {
            parent.makeDirList((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("makeDir"))
        {
            parent.makeDir((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("putFile"))
        {
            parent.putFile((String)parameters.elementAt(0));
        } else
        if(command.equals("putFile&list"))
        {
            parent.putFileList(parameters);
        } else
        if(command.equals("removeDir"))
        {
            parent.removeDir((String)parameters.elementAt(0), remote);
        } else
        if(command.equals("remove&list"))
        {
            parent.removeList(parameters, remote);
        } else
        if(command.equals("rename"))
        {
            parent.rename((String)parameters.elementAt(0), (String)parameters.elementAt(1), remote);
        } else
        if(command.equals("rename&list"))
        {
            parent.renameList((String)parameters.elementAt(0), (String)parameters.elementAt(1), remote);
        } else
        if(command.equals("setType"))
        {
            parent.setType((String)parameters.elementAt(0));
        } else
        {
            System.out.println("unrecognized command: " + command);
        }
    }

    public void setCommand(String command)
    {
        this.command = command;
    }
}
