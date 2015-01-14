package com.ibm.network.ftp.protocol;

import com.ibm.network.ftp.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Vector;

// Referenced classes of package com.ibm.network.ftp.protocol:
//            CommandInterpreter, Local, Remote

public class FTPProtocol
    implements CommandListener, Serializable
{

    private transient Local lProtocol;
    private transient Remote rProtocol;
    private transient CommandInterpreter commandInterpreter;
    private transient Vector statusListener;
    private transient Vector localFileListListener;
    private transient Vector remoteFileListListener;
    private String status;
    protected transient PropertyChangeSupport propertyChange;
    private int fieldTimeout;
    private boolean fieldRestartable;
    private int fieldBytesRead;
    private int fieldBytesWrite;
    private int fieldRestartCounter;
    private int fieldRestartCount;
    private int fieldBufferSize;
    private boolean busy;
    private String name;
    private boolean streamedInput;
    private boolean streamedOutput;
    private InputStream inpStream;
    private OutputStream outStream;

    public FTPProtocol()
    {
        statusListener = new Vector();
        localFileListListener = new Vector();
        remoteFileListListener = new Vector();
        fieldTimeout = 50000;
        fieldRestartable = false;
        fieldBytesRead = 0;
        fieldBytesWrite = 0;
        fieldRestartCounter = 0;
        fieldRestartCount = 5;
        fieldBufferSize = 1024;
        busy = false;
        name = "";
        streamedInput = false;
        streamedOutput = false;
        inpStream = null;
        outStream = null;
        rProtocol = getRemoteProtocol();
        lProtocol = getLocalProtocol();
        commandInterpreter = getCommandInterpreter();
    }

    public int abort()
    {
        return rProtocol.abort();
    }

    public void addLocalFileListListener(LocalFileListListener localfilelistlistener)
    {
        localFileListListener.addElement(localfilelistlistener);
        Local local = getLocalProtocol();
        sendLocalList(local.getLocalFileList(), local.getCurrentDir());
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        getPropertyChange().addPropertyChangeListener(propertychangelistener);
    }

    public void addRemoteFileListListener(RemoteFileListListener remotefilelistlistener)
    {
        remoteFileListListener.addElement(remotefilelistlistener);
    }

    public synchronized void addStatusListener(StatusListener statuslistener)
    {
        statusListener.addElement(statuslistener);
    }

    public int changeDir(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                return rProtocol.changeDir(s);
            } else
            {
                return lProtocol.changeDir(s);
            }
        } else
        {
            sendMessage("Trying to change directory without specifying the name \n");
            throw new IllegalArgumentException("Directory name not specified in change directory");
        }
    }

    public Vector changeDirList(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                rProtocol.changeDir(s);
                return fileList(true);
            } else
            {
                lProtocol.changeDir(s);
                return fileList(false);
            }
        } else
        {
            sendMessage("Trying to change directory without specifying the name \n");
            throw new IllegalArgumentException("Directory name not specified in change directory");
        }
    }

    public void commandPerformed(CommandEvent commandevent)
        throws IllegalArgumentException
    {
        if(commandevent != null)
        {
            commandInterpreter.interpretCommand(commandevent);
        } else
        {
            throw new IllegalArgumentException("Command Events is null in CommandPerformed");
        }
    }

    public int connect(String s)
        throws IllegalArgumentException
    {
        return connect(s, "21");
    }

    public int connect(String s, String s1)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            return rProtocol.connect(s, Integer.parseInt(s1));
        } else
        {
            throw new IllegalArgumentException("Hostname is null in connect");
        }
    }

    public int connect(String s, int i)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            return rProtocol.connect(s, i);
        } else
        {
            throw new IllegalArgumentException("Hostname is null in connect");
        }
    }

    public int connectLogin(String s, String s1, String s2)
    {
        return connectLogin(s, "21", s1, s2);
    }

    public int connectLogin(String s, String s1, String s2, String s3)
    {
        if(s != null && s1 != null && s2 != null && s3 != null)
        {
            rProtocol.connect(s, Integer.parseInt(s1));
            int i = rProtocol.login(s2, s3);
//            fileList(true);
            return i;
        } else
        {
            throw new IllegalArgumentException("Hostname, port, username or password is null in connect and login");
        }
    }

    public int deleteFile(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                return rProtocol.deleteFile(s);
            }
            lProtocol.deleteFile(s);
        } else
        {
            throw new IllegalArgumentException("Filename is null in delete File");
        }
        return 0;
    }

    public int deleteFile(Vector vector, boolean flag)
        throws IllegalArgumentException
    {
        if(vector != null)
        {
            if(!flag)
            {
                for(int i = 0; i < vector.size(); i++)
                {
                    lProtocol.deleteFile(vector.elementAt(i).toString());
                }

            }
        } else
        {
            throw new IllegalArgumentException("Filename is null in delete File");
        }
        return 0;
    }

    public Vector deleteFileList(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                rProtocol.deleteFile(s);
                return fileList(true);
            } else
            {
                lProtocol.deleteFile(s);
                return fileList(false);
            }
        } else
        {
            throw new IllegalArgumentException("Filename is null in delete File");
        }
    }

    public Vector deleteFileList(Vector vector, boolean flag)
        throws IllegalArgumentException
    {
        if(vector != null)
        {
            if(flag)
            {
                for(int i = 0; i < vector.size(); i++)
                {
                    rProtocol.deleteFile(vector.elementAt(i).toString());
                }

                return fileList(true);
            }
            for(int j = 0; j < vector.size(); j++)
            {
                lProtocol.deleteFile(vector.elementAt(j).toString());
            }

            return fileList(false);
        } else
        {
            throw new IllegalArgumentException("Filename is null in delete File");
        }
    }

    public void disconnect()
    {
        rProtocol.disconnect();
    }

    public Vector fileList(boolean flag)
    {
        if(flag)
        {
            Vector vector = rProtocol.fileList();
            sendRemoteList(vector, "");
            return vector;
        } else
        {
            Vector vector1 = lProtocol.fileList();
            sendLocalList(lProtocol.getLocalFileList(), lProtocol.getLocalCurrentDir());
            return vector1;
        }
    }

    public void firePropertyChange(String s, Object obj, Object obj1)
    {
        getPropertyChange().firePropertyChange(s, obj, obj1);
    }

    public int getBufferSize()
    {
        return fieldBufferSize;
    }

    protected boolean getBusy()
    {
        return busy;
    }

    public int getBytesRead()
    {
        return fieldBytesRead;
    }

    public int getBytesWrite()
    {
        return fieldBytesWrite;
    }

    private CommandInterpreter getCommandInterpreter()
    {
        if(commandInterpreter == null)
        {
            commandInterpreter = new CommandInterpreter(this);
        }
        return commandInterpreter;
    }

    public String getCurrentDir(boolean flag)
    {
        if(flag)
        {
            return rProtocol.getRemoteDir();
        } else
        {
            return lProtocol.getCurrentDir();
        }
    }

    public int getFile(String s)
        throws IllegalArgumentException
    {
        return getFile(s, s);
    }

    public int getFile(String s, String s1)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            int i = rProtocol.getFile(s, s1);
            fileList(false);
            return i;
        } else
        {
            throw new IllegalArgumentException(" File Name is null in get file");
        }
    }

    public Vector getFileList(String s)
        throws IllegalArgumentException
    {
        int i = rProtocol.getFile(s);
        return fileList(false);
    }

    public Vector getFileList(Vector vector)
        throws IllegalArgumentException
    {
        Vector vector1 = rProtocol.getFile(vector);
        return fileList(false);
    }

    public InputStream getInpStream()
    {
        return inpStream;
    }

    public String getLocalDir()
    {
        return lProtocol.getCurrentDir();
    }

    public Vector getLocalFileList()
    {
        return getLocalProtocol().getLocalFileList();
    }

    private Local getLocalProtocol()
    {
        if(lProtocol == null)
        {
            lProtocol = new Local(this);
        }
        return lProtocol;
    }

    public String getName()
    {
        return name;
    }

    public OutputStream getOutStream()
    {
        return outStream;
    }

    protected PropertyChangeSupport getPropertyChange()
    {
        if(propertyChange == null)
        {
            propertyChange = new PropertyChangeSupport(this);
        }
        return propertyChange;
    }

    public String getRemoteDir()
    {
        return rProtocol.getRemoteDir();
    }

    public Vector getRemoteFileList()
    {
        Vector vector = rProtocol.fileList();
        sendRemoteList(vector, "");
        return vector;
    }

    public String getRemoteOperatingSystem()
    {
        return rProtocol.getSystem();
    }

    protected Remote getRemoteProtocol()
    {
        if(rProtocol == null)
        {
            rProtocol = new Remote(this);
        }
        return rProtocol;
    }

    public boolean getRestartable()
    {
        return fieldRestartable;
    }

    public int getRestartCount()
    {
        return fieldRestartCount;
    }

    public int getRestartCounter()
    {
        return fieldRestartCounter;
    }

    public String getStatus()
    {
        return status;
    }

    public int getTimeout()
    {
        return fieldTimeout;
    }

    public String getType()
    {
        return rProtocol.getType();
    }

    public boolean isStreamedInput()
    {
        return streamedInput;
    }

    public boolean isStreamedOutput()
    {
        return streamedOutput;
    }

    public int login(String s, String s1)
        throws IllegalArgumentException
    {
        if(s != null && s1 != null)
        {
            return rProtocol.login(s, s1);
        } else
        {
            throw new IllegalArgumentException("User name or passwd is null in login");
        }
    }

    public int makeDir(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                return rProtocol.makeDir(s);
            } else
            {
                return lProtocol.makeDir(s);
            }
        } else
        {
            throw new IllegalArgumentException("Directory name not specified in make directory");
        }
    }

    public Vector makeDirList(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                rProtocol.makeDir(s);
                return fileList(true);
            } else
            {
                lProtocol.makeDir(s);
                return fileList(false);
            }
        } else
        {
            throw new IllegalArgumentException("Directory name not specified in make directory");
        }
    }

    public void passiveServer()
    {
        rProtocol.passiveMode = true;
    }

    public int putFile(String s)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            return rProtocol.putFile(s);
        } else
        {
            throw new IllegalArgumentException("No name specified in putFile");
        }
    }

    public int putFile(String s, String s1)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            return rProtocol.putFile(s, s1);
        } else
        {
            throw new IllegalArgumentException("No name specified in put file");
        }
    }

    public Vector putFileList(String s)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            rProtocol.putFile(s);
            return fileList(true);
        } else
        {
            throw new IllegalArgumentException("No name specified in put file");
        }
    }

    public Vector putFileList(Vector vector)
        throws IllegalArgumentException
    {
        if(vector != null)
        {
            rProtocol.putFile(vector);
            return fileList(true);
        } else
        {
            throw new IllegalArgumentException("No name specified in put file");
        }
    }

    public int quote(String s)
        throws IllegalArgumentException
    {
        if(s == null)
        {
            throw new IllegalArgumentException("NULL parameter for QUOTE command");
        } else
        {
            return rProtocol.quote(s);
        }
    }

    public int removeDir(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                return rProtocol.deleteDir(s);
            }
            lProtocol.deleteDir(s);
        } else
        {
            throw new IllegalArgumentException("Directory name is null in remove directory");
        }
        return 0;
    }

    public Vector removeList(String s, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null)
        {
            if(flag)
            {
                if(!s.startsWith("[") || !s.endsWith("]"))
                {
                    rProtocol.deleteFile(s);
                } else
                {
                    s = new String(s.substring(1, s.length() - 1));
                    rProtocol.deleteDir(s);
                }
                return fileList(true);
            }
            if(!s.startsWith("[") || !s.endsWith("]"))
            {
                lProtocol.deleteFile(s);
            } else
            {
                s = new String(s.substring(1, s.length() - 1));
                lProtocol.deleteDir(s);
            }
            return fileList(false);
        } else
        {
            throw new IllegalArgumentException("Directory name is null in remove directory");
        }
    }

    public Vector removeList(Vector vector, boolean flag)
        throws IllegalArgumentException
    {
        if(vector != null)
        {
            if(flag)
            {
                for(int i = 0; i < vector.size(); i++)
                {
                    String s = (String)vector.elementAt(i);
                    if(!s.startsWith("[") || !s.endsWith("]"))
                    {
                        rProtocol.deleteFile(s);
                    } else
                    {
                        s = new String(s.substring(1, s.length() - 1));
                        rProtocol.deleteDir(s);
                    }
                }

                return fileList(true);
            }
            for(int j = 0; j < vector.size(); j++)
            {
                String s1 = (String)vector.elementAt(j);
                if(!s1.startsWith("[") || !s1.endsWith("]"))
                {
                    lProtocol.deleteFile(s1);
                } else
                {
                    s1 = new String(s1.substring(1, s1.length() - 1));
                    lProtocol.deleteDir(s1);
                }
            }

            return fileList(false);
        } else
        {
            throw new IllegalArgumentException("Directory name is null in remove directory");
        }
    }

    public synchronized void removeLocalFileListListener(LocalFileListListener localfilelistlistener)
    {
        localFileListListener.removeElement(localfilelistlistener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        getPropertyChange().removePropertyChangeListener(propertychangelistener);
    }

    public synchronized void removeRemoteFileListListener(RemoteFileListListener remotefilelistlistener)
    {
        remoteFileListListener.removeElement(remotefilelistlistener);
    }

    public synchronized void removeStatusListener(StatusListener statuslistener)
    {
        statusListener.removeElement(statuslistener);
    }

    public int rename(String s, String s1, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null && s1 != null)
        {
            if(flag)
            {
                return rProtocol.rename(s, s1);
            }
            lProtocol.rename(s, s1);
        } else
        {
            throw new IllegalArgumentException("Either oldname or new name is null in rename file");
        }
        return 0;
    }

    public Vector renameList(String s, String s1, boolean flag)
        throws IllegalArgumentException
    {
        if(s != null && s1 != null)
        {
            if(flag)
            {
                rProtocol.rename(s, s1);
                return fileList(true);
            } else
            {
                lProtocol.rename(s, s1);
                return fileList(false);
            }
        } else
        {
            throw new IllegalArgumentException("Either oldname or new name is null in rename file");
        }
    }

    void sendLocalList(Vector vector, String s)
    {
        LocalFileListEvent localfilelistevent = new LocalFileListEvent(this, vector, s);
        Vector vector1 = (Vector)localFileListListener.clone();
        for(int i = 0; i < vector1.size(); i++)
        {
            LocalFileListListener localfilelistlistener = (LocalFileListListener)vector1.elementAt(i);
            localfilelistlistener.localFileListReceived(localfilelistevent);
        }

    }

    void sendMessage(String s)
    {
        status = s;
        StatusEvent statusevent = new StatusEvent(this, s);
        Vector vector = (Vector)statusListener.clone();
        for(int i = 0; i < vector.size(); i++)
        {
            StatusListener statuslistener = (StatusListener)vector.elementAt(i);
            statuslistener.statusReceived(statusevent);
        }

    }

    void sendRemoteList(Vector vector, String s)
    {
        RemoteFileListEvent remotefilelistevent = new RemoteFileListEvent(this, vector, s);
        Vector vector1 = (Vector)remoteFileListListener.clone();
        for(int i = 0; i < vector1.size(); i++)
        {
            RemoteFileListListener remotefilelistlistener = (RemoteFileListListener)vector1.elementAt(i);
            remotefilelistlistener.remoteFileListReceived(remotefilelistevent);
        }

    }

    public void setBufferSize(int i)
    {
        int j = fieldBufferSize;
        fieldBufferSize = i;
        firePropertyChange("bufferSize", new Integer(j), new Integer(i));
    }

    public void setInpStream(InputStream inputstream)
    {
        inpStream = inputstream;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setOutStream(OutputStream outputstream)
    {
        outStream = outputstream;
    }

    public void setStreamedInput(boolean flag)
    {
        streamedInput = flag;
    }

    public void setStreamedOutput(boolean flag)
    {
        streamedOutput = flag;
    }

    public int setType(String s)
    {
        return rProtocol.setType(s);
    }

    public void setTimeout(int i)
    {
        fieldTimeout = i;
    }

    public int site(String s)
    {
        return rProtocol.site(s);
    }

    public int system()
    {
        return rProtocol.system();
    }

    public void forceNLST(boolean flag)
    {
        rProtocol.forceNLST(flag);
    }

    public Vector putFile(Vector vector)
    {
        return rProtocol.putFile(vector);
    }

    public Vector getFile(Vector vector)
    {
        return rProtocol.getFile(vector);
    }

    public void setAllowClientSideResume(boolean flag)
    {
        rProtocol.setAllowClientSideResume(flag);
    }

    public void setAllowServerSideResume(boolean flag)
    {
        rProtocol.setAllowServerSideResume(flag);
    }

    public void downloadDirectoryRecursively(String s)
    {
        rProtocol.aborted = false;
        rProtocol.downloadDirectoryRecursively(s);
    }

    public void uploadDirectoryRecursively(String s)
    {
        rProtocol.aborted = false;
        rProtocol.uploadDirectoryRecursively(s);
    }

    public Vector mget(String s)
    {
        return rProtocol.mget(s);
    }

    public String getSocksProxyPort()
    {
        String s = System.getProperty("socksProxyPort");
        return s;
    }

    public String getSocksProxyHost()
    {
        String s = System.getProperty("socksProxyHost");
        return s;
    }

    public void configureSocks(String s, String s1)
        throws IllegalArgumentException
    {
        if(s != null && s1 != null)
        {
            rProtocol.configureSocks(s.trim(), s1);
        } else
        {
            throw new IllegalArgumentException("Either hostname or port is null in configure socks");
        }
    }

    public int cdup()
    {
        return rProtocol.cdup();
    }

    public String status()
    {
        return status;
    }

    public Vector list(boolean flag)
    {
        return fileList(flag);
    }

    public boolean isStillConnected()
    {
        return rProtocol.isStillConnected();
    }

    protected void setBytesRead(int i)
    {
        int j = fieldBytesRead;
        fieldBytesRead = i;
        firePropertyChange("bytesRead", new Integer(j), new Integer(i));
    }

    protected void setBytesWrite(int i)
    {
        int j = fieldBytesWrite;
        fieldBytesWrite = i;
        firePropertyChange("bytesWrite", new Integer(j), new Integer(i));
    }
}
