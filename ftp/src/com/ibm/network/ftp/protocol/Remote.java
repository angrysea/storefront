package com.ibm.network.ftp.protocol;

import com.ibm.network.ftp.FileInfo;
import com.ibm.network.ftp.Response;
import java.io.*;
import java.net.*;
import java.util.*;

// Referenced classes of package com.ibm.network.ftp.protocol:
//            FTPProtocol

class Remote
{

    private FTPProtocol parent;
    private String hostName;
    boolean passiveMode;
    private String type;
    private Socket controlConnectionSocket;
    private BufferedReader controlInputStream;
    private PrintStream controlOutputStream;
    private String serverOS;
    private InetAddress localHost;
    private Response lastResponse;
    private Response firstTransferResponse;
    private boolean forceNLST;
    private boolean allowClientSideResume;
    private boolean allowServerSideResume;
    private Vector fullDirectoryList;
    private boolean dirlistNeedsRefresh;
    boolean aborted;
    private String remoteDir;
    boolean loggedIn;
    boolean mgetMode;

    Remote(FTPProtocol ftpprotocol)
    {
        type = new String("ASCII");
        serverOS = "UNIX";
        fullDirectoryList = new Vector();
        mgetMode = false;
        parent = ftpprotocol;
    }

    int abort()
    {
        aborted = true;
        return executeCommand("ABOR").getResponse();
    }

    int changeDir(String s)
    {
        Response response = executeCommand("CWD", s);
        if(response.getResponse() == 250)
        {
            automaticFileList(null);
            pwd();
        }
        return response.getResponse();
    }

    private Response checkResponse()
    {
        try
        {
            String s = controlInputStream.readLine();
            if(s == null)
            {
                Response response = new Response();
                response.setResponse(-1);
                response.setStringResponse("-1");
                lastResponse = response;
                return response;
            }
            String s1 = s;
            boolean flag = true;
            while(s1 == null || s1.length() < 4 || !Character.isDigit(s1.charAt(0)) || !Character.isDigit(s1.charAt(1)) || !Character.isDigit(s1.charAt(2)) || s1.charAt(3) != ' ')
            {
                if(flag)
                {
                    s = s + "\n";
                    flag = false;
                }
                s1 = controlInputStream.readLine();
                s = s + s1 + "\n";
            }
            lastResponse = new Response();
            lastResponse.setResponse(Integer.parseInt(s.substring(0, 3)));
            lastResponse.setStringResponse(s);
            printText(s);
            return lastResponse;
        }
        catch(Exception exception)
        {
            System.out.println(exception);
            exception.printStackTrace();
            Response res = new Response( -1, exception.toString() );
            lastResponse = res;
            return res;
        }
    }

    int connect(String s)
    {
        return connect(s, 21);
    }

    int connect(String s, int i)
    {
        try
        {
            hostName = s;
            controlConnectionSocket = new Socket(s, i);
//            controlConnectionSocket.setSoTimeout(1000); // ~RK
            controlConnectionSocket.setSoTimeout( parent.getTimeout() ); // ~RK
            localHost = controlConnectionSocket.getLocalAddress();
            controlInputStream = new BufferedReader(new InputStreamReader(controlConnectionSocket.getInputStream()));
            controlOutputStream = new PrintStream(controlConnectionSocket.getOutputStream());
            return checkResponse().getResponse();
        }
        catch(Exception exception)
        {
            return -1;
        }
    }

    int deleteDir(String s)
    {
        dirlistNeedsRefresh = true;
        return executeCommand("RMD", s).getResponse();
    }

    int deleteFile(String s)
    {
        dirlistNeedsRefresh = true;
        return executeCommand("DELE", s).getResponse();
    }

    void disconnect()
    {
        try
        {
            executeCommand("QUIT");
            if( controlOutputStream != null )
            {
                try { controlOutputStream.close(); } catch( Exception e ) {}
                controlOutputStream = null;
            }
            if( controlInputStream != null )
            {
                try { controlInputStream.close(); } catch( Exception e ) {}
                controlInputStream = null;
            }
            if( controlConnectionSocket != null )
            {
                try { controlConnectionSocket.close(); } catch( Exception e ) {}
                controlConnectionSocket = null;
            }
        }
        catch(Exception exception) { }
    }

    private Response executeCommand(String s, String s1)
    {
        if(controlOutputStream == null)
        {
            printText("Not connected to any server!");
            return new Response( -1, "Not connected to any server" );
        }
        if(!loggedIn)
        {
            printText("Login was not successful! Aborting!");
            return new Response( -1, "Unauthorized user" );
        }
        if(s1 != null)
        {
            controlOutputStream.print(s + " " + s1 + "\r\n");
        } else
        {
            controlOutputStream.print(s + "\r\n");
        }
        if(!s.equals("PASS"))
        {
            printText("SENT: " + s + " " + (s1 != null ? s1 : ""));
        } else
        {
            printText("SENT: PASS XXXX");
        }
        return checkResponse();
    }

    private Response executeCommand(String s)
    {
        return executeCommand(s, null);
    }

    Vector fileList()
    {
        if(dirlistNeedsRefresh)
        {
            automaticFileList(null);
            dirlistNeedsRefresh = false;
        }
        return fullDirectoryList;
    }

    private Vector automaticFileList(String s)
    {
        Vector vector = null;
        Object obj = null;
        String s1 = null;
        if(!type.equalsIgnoreCase("ASCII"))
        {
            s1 = type;
            setType("ASCII");
        }
        try
        {
            if(s == null)
            {
                Socket socket;
                if(!passiveMode)
                {
                    socket = createPassiveSocket("LIST" + (s != null ? " " + s : ""));
                } else
                {
                    socket = createListenerPortOnTheServer("LIST" + (s != null ? " " + s : ""));
                }
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Vector vector1 = new Vector();
                String s2;
                while((s2 = bufferedreader.readLine()) != null)
                {
                    vector1.addElement(s2);
                }
                bufferedreader.close();
                socket.close();
                checkResponse();
                Vector vector3 = null;
                if(forceNLST)
                {
                    Socket socket1;
                    if(!passiveMode)
                    {
                        socket1 = createPassiveSocket("NLST" + (s != null ? " " + s : ""));
                    } else
                    {
                        socket1 = createListenerPortOnTheServer("NLST" + (s != null ? " " + s : ""));
                    }
                    BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                    vector3 = new Vector();
                    String s3;
                    while((s3 = bufferedreader1.readLine()) != null)
                    {
                        vector3.addElement(s3);
                    }
                    bufferedreader1.close();
                    socket1.close();
                    checkResponse();
                }
                vector = setFileListDescription(vector1, vector3);
                for(int i = 0; i < vector.size(); i++)
                {
                    if(((FileInfo)vector.elementAt(i)).getName().equals("..") || ((FileInfo)vector.elementAt(i)).getName().equals(".") || ((FileInfo)vector.elementAt(i)).getName().equals(""))
                    {
                        vector.removeElementAt(i);
                    }
                }

                if(s == null)
                {
                    fullDirectoryList = vector;
                }
                if(s1 != null)
                {
                    setType(s1);
                }
                return vector;
            }
            Socket socket2;
            if(!passiveMode)
            {
                socket2 = createPassiveSocket("NLST" + (s != null ? " " + s : ""));
            } else
            {
                socket2 = createListenerPortOnTheServer("NLST" + (s != null ? " " + s : ""));
            }
            BufferedReader bufferedreader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            Vector vector2 = new Vector();
            String s4;
            while((s4 = bufferedreader2.readLine()) != null)
            {
                FileInfo fileinfo = new FileInfo();
                fileinfo.setName(s4);
                vector2.addElement(fileinfo);
            }
            socket2.close();
            checkResponse();
            if(s1 != null)
            {
                setType(s1);
            }
            return vector2;
        }
        catch(Exception exception)
        {
            return vector;
        }
    }

    private Socket createPassiveSocket(String s)
    {
        ServerSocket serversocket = null;
        try
        {
            try
            {
                byte abyte0[] = InetAddress.getLocalHost().getAddress();
                serversocket = new ServerSocket(0);
                serversocket.setSoTimeout(parent.getTimeout());
                StringBuffer stringbuffer = new StringBuffer("PORT ");
                for(int i = 0; i < abyte0.length; i++)
                {
                    stringbuffer.append(abyte0[i] & 0xff);
                    stringbuffer.append(",");
                }

                stringbuffer.append(serversocket.getLocalPort() >>> 8 & 0xff);
                stringbuffer.append(",");
                stringbuffer.append(serversocket.getLocalPort() & 0xff);
                executeCommand(new String(stringbuffer));
                executeCommand(s);
                if(lastResponse.getStringResponse().startsWith("5"))
                {
                    Socket socket1 = null;
                    return socket1;
                }
                Socket socket2 = serversocket.accept();
                socket2.setSoTimeout(parent.getTimeout());
                Socket socket3 = socket2;
                return socket3;
            }
            catch(IOException ioexception)
            {
                printText("I/O error while setting up a ServerSocket on the client machine!" + ioexception);
            }
            Socket socket = null;
            return socket;
        }
        finally
        {
            try
            {
                serversocket.close();
            }
            catch(IOException ioexception1)
            {
                printText("ServerSocket.close() kozben hiba!" + ioexception1);
            }
        }
    }

    int cdup()
    {
        Response response = executeCommand("CDUP");
        pwd();
        automaticFileList(null);
        return response.getResponse();
    }

    private Socket createListenerPortOnTheServer(String s)
    {
        Response response = executeCommand("PASV");
        if(response.getResponse() / 100 == 2)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(response.getStringResponse(), ",)\r\n");
            for(int i = 0; i < 4; i++)
            {
                stringtokenizer.nextToken();
            }

            int j = Integer.parseInt(stringtokenizer.nextToken());
            int k = Integer.parseInt(stringtokenizer.nextToken());
            try
            {
                Socket socket = new Socket(hostName, j * 256 + k);
                socket.setSoTimeout(parent.getTimeout());
                executeCommand(s).getStringResponse();
                if(lastResponse.getStringResponse().startsWith("5"))
                {
                    socket.close(); // ~RK
                    return null;
                } else
                {
                    return socket;
                }
            }
            catch(IOException ioexception)
            {
                printText("I/O error while trying to open a connection to the server!");
            }
            return null;
        } else
        {
            return null;
        }
    }

    Vector getFile(Vector vector)
    {
        Vector vector1 = new Vector();
        for(int i = 0; i < vector.size(); i++)
        {
            getFile((String)vector.elementAt(i));
            vector1.addElement(new String(firstTransferResponse.getStringResponse()) + "\t" + new String(lastResponse.getStringResponse()) + "\r\n");
            if(aborted)
            {
                break;
            }
        }

        return vector1;
    }

    int getFile(String s)
    {
        return getFile(s, s);
    }

    int getFile(String s, String s1)
    {
        if(controlOutputStream == null)
        {
            printText("Not connected to any server!");
            return -1;
        }
        if(!loggedIn)
        {
            printText("Login was not successful! Aborting!");
            return -1;
        }
        aborted = false;
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        String s2 = parent.getCurrentDir(false);
        String s3 = new String(s2 + File.separatorChar + s1);
        if(allowClientSideResume && !parent.isStreamedOutput())
        {
            File file = new File(s3);
            if(file.exists())
            {
                executeCommand("REST", "" + file.length());
            }
        }
        parent.setBytesRead(0);
        int i = 0;
        try
        {
            Socket socket;
            if(!passiveMode)
            {
                socket = createPassiveSocket("RETR " + s);
            } else
            {
                socket = createListenerPortOnTheServer("RETR " + s);
            }
            firstTransferResponse = lastResponse;
            if(socket == null)
            {
                return lastResponse.getResponse();
            }
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            PrintStream printstream;
            if(allowClientSideResume && !parent.isStreamedOutput())
            {
                printstream = new PrintStream(new BufferedOutputStream(new FileOutputStream(s3, true)));
            } else
            if(!allowClientSideResume && !parent.isStreamedOutput())
            {
                printstream = new PrintStream(new BufferedOutputStream(new FileOutputStream(s3)));
            } else
            {
                printstream = new PrintStream(new BufferedOutputStream(parent.getOutStream()));
            }
            if(mgetMode && parent.isStreamedOutput())
            {
                String s4 = "-------hjgfjgsfsau63r73hdhgxhjcve56r73hjxff7jidfhvsd786t3 next file: " + s + "-------hjgfjgsfsau63r73hdhgxhjcve56r73hjxff7jidfhvsd786t3--";
                byte abyte1[] = new byte[s4.length()];
                char ac[] = s4.toCharArray();
                for(int l = 0; l < ac.length; l++)
                {
                    abyte1[l] = (byte)ac[l];
                }

                printstream.write(abyte1, 0, s4.length());
                printstream.flush();
            }
            if(type.equalsIgnoreCase("ASCII"))
            {
                String s5 = System.getProperty("line.separator");
                int k;
                while((k = datainputstream.read()) != -1)
                {
                    if(i++ % 10240 == 0)
                    {
                        printText(".");
                    }
                    if(k == 13)
                    {
                        if((k = datainputstream.read()) == 10)
                        {
                            printstream.print(s5);
                        } else
                        {
                            printstream.print('\r');
                            printstream.print((char)k);
                        }
                    } else
                    {
                        printstream.print((char)k);
                    }
                }
                parent.setBytesRead(i);
            } else
            {
                byte abyte0[] = new byte[parent.getBufferSize()];
                for(int j = 0; j != -1 && !aborted;)
                {
                    j = datainputstream.read(abyte0);
                    if(j == -1)
                    {
                        break;
                    }
                    printstream.write(abyte0, 0, j);
                    parent.setBytesRead(parent.getBytesRead() + j);
                    for(; (parent.getBytesRead() - i) / 10240 > 0; printText("."))
                    {
                        i += 10240;
                    }

                }

            }
            printstream.flush();
            printText("Transfer complete!");
            if(!parent.isStreamedOutput())
            {
                printstream.close();
            }
            datainputstream.close();
            socket.close();
            return checkResponse().getResponse();
        }
        catch(Exception exception)
        {
            printText("I/O error!");
        }
        return -1;
    }

    int login(String s, String s1)
    {
        if(controlOutputStream == null)
        {
            printText("Not connected to any server!");
            return -1;
        }
        loggedIn = true;
        executeCommand("USER", s);
        Response response = executeCommand("PASS", s1);
        if(response.getResponse() == 230)
        {
            loggedIn = true;
        } else
        {
            loggedIn = false;
            return 530;
        }
        setSystem();
        pwd();
        //automaticFileList(null);  ~RK
        return response.getResponse();
    }

    int makeDir(String s)
    {
        dirlistNeedsRefresh = true;
        return executeCommand("MKD", s).getResponse();
    }

    private void printText(String s)
    {
        parent.sendMessage(s);
    }

    Vector putFile(Vector vector)
    {
        Vector vector1 = new Vector();
        for(int i = 0; i < vector.size(); i++)
        {
            if(putFile((String)vector.elementAt(i)) != -1)
            {
                vector1.addElement(new String(firstTransferResponse.getStringResponse()) + "\t" + new String(lastResponse.getStringResponse()) + "\r\n");
            } else
            {
                vector1.addElement(new String((String)vector.elementAt(i) + " doesn't exist on the local machine!") + "\t\r\n");
            }
            if(aborted)
            {
                break;
            }
        }

        return vector1;
    }

    int putFile(String s)
    {
        return putFile(s, s);
    }

    int putFile(String s, String s1)
    {
        if(controlOutputStream == null)
        {
            printText("Not connected to any server!");
            return -1;
        }
        if(!loggedIn)
        {
            printText("Login was not successful! Aborting!");
            return -1;
        }
        aborted = false;
        Object obj = null;
        parent.setBytesWrite(0);
        int i = 0;
        if(!parent.isStreamedInput() && !(new File(parent.getCurrentDir(false) + File.separatorChar + s)).exists())
        {
            printText("Source file (" + parent.getCurrentDir(false) + File.separatorChar + s + ") doesn't exist!");
            return 550;
        }
        long l = 0L;
        if(allowServerSideResume)
        {
            fileList();  // Refresh fullDirectoryList if necessary. ~RK
            for(int j = 0; j < fullDirectoryList.size(); j++)
            {
                if(((FileInfo)fullDirectoryList.elementAt(j)).getName().equals(s1))
                {
                    executeCommand("REST", "" + ((FileInfo)fullDirectoryList.elementAt(j)).getSize());
                    l = Long.parseLong(((FileInfo)fullDirectoryList.elementAt(j)).getSize());
                }
            }

        }
        Object obj1 = null;
        Object obj2 = null;
        try
        {
            Socket socket;
            if(!passiveMode)
            {
                socket = createPassiveSocket("STOR " + s1);
            } else
            {
                socket = createListenerPortOnTheServer("STOR " + s1);
            }
            firstTransferResponse = lastResponse;
            String s2 = parent.getCurrentDir(false);
            String s3 = new String(s2 + File.separatorChar + s);
            DataInputStream datainputstream;
            FileInputStream fileinputstream;
            if(!parent.isStreamedInput())
            {
                datainputstream = new DataInputStream(new BufferedInputStream(fileinputstream = new FileInputStream(s3)));
            } else
            {
                datainputstream = new DataInputStream(new BufferedInputStream(parent.getInpStream()));
            }
            PrintStream printstream = new PrintStream(socket.getOutputStream());
            if(l != 0L)
            {
                for(long l1 = 0L; l1 < l; l1++)
                {
                    datainputstream.read();
                }

            }
            int k = parent.getBufferSize();
            if(type.equalsIgnoreCase("ASCII"))
            {
                String s4 = System.getProperty("line.separator");
                StringBuffer stringbuffer = new StringBuffer(k);
                int i1;
                while((i1 = datainputstream.read()) != -1 && !aborted)
                {
                    if(i++ % 10240 == 0)
                    {
                        printText(".");
                    }
                    switch(i1)
                    {
                    case 13: // '\r'
                        if(s4.equals("\r"))
                        {
                            stringbuffer.append("\r\n");
                        } else
                        if(s4.equals("\n"))
                        {
                            stringbuffer.append('\r');
                        } else
                        if(s4.equals("\r\n"))
                        {
                            if((i1 = datainputstream.read()) == 10)
                            {
                                stringbuffer.append("\r\n");
                            } else
                            {
                                stringbuffer.append('\r');
                                stringbuffer.append((char)i1);
                            }
                        }
                        break;

                    case 10: // '\n'
                        stringbuffer.append("\r\n");
                        break;

                    default:
                        if(i1 > 127)
                        {
                            printText("WARNING: non-ascii character value '" + i1 + "' in " + s);
                        }
                        stringbuffer.append((char)i1);
                        break;
                    }
                    if(stringbuffer.length() >= k)
                    {
                        printstream.print(stringbuffer.toString());
                        stringbuffer.setLength(0);
                    }
                }
                if(stringbuffer.length() > 0)
                {
                    printstream.print(stringbuffer);
                }
                parent.setBytesWrite(i);
            } else
            {
                byte abyte0[] = new byte[k];
                for(int j1 = 0; (j1 = datainputstream.read(abyte0)) != -1 && !aborted;)
                {
                    printstream.write(abyte0, 0, j1);
                    parent.setBytesWrite(parent.getBytesWrite() + j1);
                    for(; (parent.getBytesWrite() - i) / 10240 > 0; printText("."))
                    {
                        i += 10240;
                    }

                }

            }
            printstream.flush();
            printstream.close();
            datainputstream.close();
            socket.close();
            dirlistNeedsRefresh = true;
            printText("Transfer complete!");
            return checkResponse().getResponse();
        }
        catch(Exception exception)
        {
            return -1;
        }
    }

    int quote(String s)
    {
        return executeCommand(s).getResponse();
    }

    int rename(String s, String s1)
    {
        executeCommand("RNFR", s);
        dirlistNeedsRefresh = true;
        return executeCommand("RNTO", s1).getResponse();
    }

    private Vector setFileListDescription(Vector vector, Vector vector1)
    {
        Vector vector2 = new Vector();
        Object obj = null;
        for(int i = 0; i < vector.size(); i++)
        {
            String s = (String)vector.elementAt(i);
            FileInfo fileinfo = new FileInfo();
            if(serverOS.equals("VM"))
            {
                fileinfo.setRemoteDescriptionVM(s);
            } else
            if(serverOS.equals("MVS"))
            {
                fileinfo.setRemoteDescriptionMVS(s);
            } else
            if(serverOS.equals("WinNT"))
            {
                fileinfo.setRemoteDescriptionWinNT(s);
            } else
            if(serverOS.equals("OS/400"))
            {
                fileinfo.setRemoteDescriptionOS400(s);
            } else
            {
                fileinfo.setRemoteDescriptionUNIX(s);
            }
            if(forceNLST)
            {
                for(int j = 0; j < vector1.size(); j++)
                {
                    if(s.indexOf((String)vector1.elementAt(j)) != -1)
                    {
                        fileinfo.setName((String)vector1.elementAt(j));
                    }
                }

            }
            vector2.addElement(fileinfo);
        }

        return vector2;
    }

    void setPassive(boolean flag)
    {
        passiveMode = flag;
    }

    int setSystem()
    {
        Response response = executeCommand("SYST");
        serverOS = "UNIX";
        serverOS = response.getStringResponse().toUpperCase().indexOf("MVS") >= 0 ? "MVS" : serverOS;
        serverOS = response.getStringResponse().toUpperCase().indexOf("VM") >= 0 ? "VM" : serverOS;
        serverOS = response.getStringResponse().toUpperCase().indexOf("WINDOWS_NT") >= 0 ? "WinNT" : serverOS;
        serverOS = response.getStringResponse().toUpperCase().indexOf("OS/400") >= 0 ? "OS/400" : serverOS;
        return response.getResponse();
    }

    int setType(String s)
    {
        Response response = null;
        if(s.equalsIgnoreCase("ASCII"))
        {
            response = executeCommand("TYPE", "A");
        } else
        {
            response = executeCommand("TYPE", "I");
        }
        type = s;
        return response.getResponse();
    }

    int site(String s)
    {
        return executeCommand("SITE", s).getResponse();
    }

    int system()
    {
        return executeCommand("SYST").getResponse();
    }

    void forceNLST(boolean flag)
    {
        forceNLST = flag;
    }

    void setAllowClientSideResume(boolean flag)
    {
        allowClientSideResume = flag;
    }

    void setAllowServerSideResume(boolean flag)
    {
        allowServerSideResume = flag;
    }

    void downloadDirectoryRecursively(String s)
    {
        if(aborted)
        {
            return;
        }
        if(changeDir(s) != 250)
        {
            return;
        }
        String s1 = parent.getLocalDir();
        parent.makeDir(s, false);
        parent.changeDir(s, false);
        fileList();  // Refresh fullDirectoryList if necessary. ~RK
        for(int i = 0; i < fullDirectoryList.size(); i++)
        {
            String s2 = ((FileInfo)fullDirectoryList.elementAt(i)).getName();
            if(!((FileInfo)fullDirectoryList.elementAt(i)).isFile() && !s2.equals(".") && !s2.equals(".."))
            {
                downloadDirectoryRecursively(s2);
                continue;
            }
            if(s2.equals(".") || s2.equals(".."))
            {
                continue;
            }
            if(aborted)
            {
                break;
            }
            getFile(s2);
        }

        cdup();
        parent.changeDir(s1, false);
    }

    void uploadDirectoryRecursively(String s)
    {
        if(aborted)
        {
            return;
        }
        parent.changeDir(s, false);
        makeDir(s);
        changeDir(s);
        Vector vector = parent.fileList(false);
        for(int i = 0; i < vector.size(); i++)
        {
            String s1 = ((FileInfo)vector.elementAt(i)).getName();
            if(!((FileInfo)vector.elementAt(i)).isFile() && !s1.equals(".") && !s1.equals(".."))
            {
                uploadDirectoryRecursively(s1);
                continue;
            }
            if(s1.equals(".") || s1.equals(".."))
            {
                continue;
            }
            if(aborted)
            {
                break;
            }
            putFile(s1);
        }

        cdup();
        parent.changeDir("..", false);
    }

    Vector mget(String s)
    {
        Vector vector = automaticFileList(s);
        Vector vector1 = new Vector();
        for(int i = 0; i < vector.size(); i++)
        {
            String s1 = ((FileInfo)vector.elementAt(i)).getName();
            vector1.addElement(s1);
        }

        mgetMode = true;
        Vector vector2 = new Vector();
        if(vector.size() != 0)
        {
            vector2 = getFile(vector1);
        }
        mgetMode = false;
        return vector2;
    }

    String getSystem()
    {
        return serverOS;
    }

    String getType()
    {
        return type;
    }

    String getRemoteDir()
    {
        return remoteDir;
    }

    private void pwd()
    {
        String s = executeCommand("PWD").getStringResponse();
        int i = s.indexOf("\"");
        int j = s.lastIndexOf("\"");
        if(i != -1 && j != -1)
        {
            remoteDir = s.substring(i + 1, j);
        } else
        {
            remoteDir = "Can't parse remote dir!";
        }
    }

    void configureSocks(String s, String s1)
    {
        Properties properties = new Properties(System.getProperties());
        if(s == null || s1 == null || s.trim().equals("") || s1.trim().equals(""))
        {
            properties.remove("socksProxyHost");
            properties.remove("socksProxyPort");
            System.setProperties(properties);
            setPassive(false);
        } else
        {
            properties.put("socksProxyHost", s);
            properties.put("socksProxyPort", s1);
            System.setProperties(properties);
            setPassive(true);
            printText("Socks server set with hostname = " + s + " and port = " + s1 + " \r\n");
            return;
        }
    }

    public boolean isStillConnected()
    {
        Response res = executeCommand("NOOP");
        int code = res.getResponse();
        return ( code > 0 && code < 299 );
    }

    private boolean endOfReply(String s)
    {
        boolean flag = false;
        if(s != null && s.length() >= 4 && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2)) && s.charAt(3) == ' ')
        {
            flag = true;
        }
        return flag;
    }
}
