package com.storefront.ftp;
import com.ibm.network.ftp.protocol.*;
import java.io.*;
import java.util.Calendar;

public class FtpClient
{
    public FtpClient()
    {
    }

    public FtpClient( String server, String user, String password )
    {
        this.server = server;
        this.user = user;
        this.password = password;
    }

    public void setServerName( String name )
    {
        server = name;
    }

    public void setUserCredentials( String user, String password )
    {
        this.user = user;
        this.password = password;
    }

    /**
     * Configures the timeout of the FTP operations performed by this client
     * @param seconds
     */
    public void setTransactionTimeout( int seconds )
    {
        client.setTimeout( seconds * 1000 );
    }

    public void setPassiveServer( boolean bPassive )
    {
        this.bPassiveServer = bPassive;
    }

    public boolean connect() throws FTPException
    {
        try
        {
            int status;
            String statusMsg;

            boolean bConnected = verifyConnected();
            if( !bConnected )

            //if( !bInit )
            {
                client.disconnect();
                if( bPassiveServer )
                {
                    client.passiveServer();
                }
                status = client.connect( server );
                statusMsg = client.getStatus();
                if( status != 220 )
                {
                    throw new FTPException(status, "Failed to connect to server '" + server + ": " + statusMsg);
                }

                status = client.login( user, password );
                statusMsg = client.getStatus();
                if( status != 230 )
                {
                    throw new FTPException(status, "User authorization failure: " + statusMsg);
                }

                setLastAccessed();
            }
            return !bConnected;
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch( Exception e )
        {
            throw new FTPException("Unknown FTP connect exception: " + e);
        }
    }

    public boolean connect( int retries, int retryInterval ) throws FTPException
    {
        boolean bNewConnection = false;
        FTPException ftpex = null;
        while( retries >= 0 )
        {
            ftpex = null;
            try
            {
                bNewConnection = this.connect();
                break;
            }
            catch( FTPException e )
            {
                retries--;
                ftpex = e;
            }

            try { Thread.sleep( retryInterval * 1000 ); } catch( Exception e ) {}
        }

        if( ftpex != null )
        {
            throw ftpex;
        }

        return bNewConnection;

    }

    public void disconnect()
    {
        client.disconnect();
    }

    public void putFile( String fileName, String remoteFileName) throws FTPException
    {
        try
        {
            //connect();
            int status = client.putFile( fileName, remoteFileName );
            String statusMsg = client.getStatus();
            if( status != 226 )
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Source file " + remoteFileName + " not found: " + statusMsg);
                }
                else
                {
                    throw new FTPException( status, "Transer of file " + remoteFileName + " failed: " + statusMsg);
                }
            }
            setLastAccessed();
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while transferring file: " + ex);
        }
    }

    public void putFileFromString( String strContents, String remoteFileName) throws FTPException
    {
        ByteArrayInputStream is = new ByteArrayInputStream( strContents.getBytes() );

        try
        {
            client.setStreamedInput(true);
            client.setInpStream( is );

            //connect();
            int status = client.putFile( remoteFileName );
            String statusMsg = client.getStatus();

            if( status > 0 && status < 226 )
            {
                System.out.println("FTP status returned pending " + status);
            }

            //if( status != 226 )
            if( status <= 0 )
            {
                throw new FTPException( status, "FTP connection lost: " + statusMsg);
            }
            else if( status > 299 )  // all 200 codes and below treated as success
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Source file " + remoteFileName + " not found: " + statusMsg);
                }
                else
                {
                    throw new FTPException( status, "Transer of file " + remoteFileName + " failed: " + statusMsg);
                }
            }
            setLastAccessed();
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while transferring file: " + ex);
        }
        finally
        {
            client.setStreamedInput(false);
            try{ is.close(); } catch(Exception e) {}
        }
    }

    public void getFile( String fileName, String remoteFileName) throws FTPException
    {
        try
        {
            //connect();
            int status = client.getFile( fileName, remoteFileName );
            String statusMsg = client.getStatus();
            if( status != 226 )
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Source file " + remoteFileName + " not found: " + statusMsg);
                }
                else
                {
                    throw new FTPException( status, "Transer of file " + remoteFileName + " failed: " + statusMsg);
                }
            }
            setLastAccessed();
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while transferring file: " + ex);
        }
    }

    public String getFileToString( String remoteFileName ) throws FTPException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
            client.setStreamedOutput(true);
            client.setOutStream( os );

            //connect();
            int status = client.getFile( remoteFileName );
            String statusMsg = client.getStatus();

            if( status > 0 && status < 226 )
            {
                System.out.println("FTP status returned pending " + status);
            }

            if( status <= 0 )
            {
                throw new FTPException( status, "FTP connection lost: " + statusMsg);
            }
            //if( status != 226 )
            else if( status > 299 )  // all 200 codes and below treated as success
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Source file " + remoteFileName + " not found: " + statusMsg);
                }
                else
                {
                    throw new FTPException( status, "Transer of file " + remoteFileName + " failed: " + statusMsg);
                }
            }

            // os must be flushed and closed
            os.flush();
            os.close();
            byte[] rawBuff = os.toByteArray();
            String retStr = new String( rawBuff );

            setLastAccessed();
            return retStr;
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while transferring file: " + ex);
        }
        finally
        {
            client.setStreamedOutput(false);
            try{ os.close(); } catch( Exception e) {}
        }
    }

    public String getFileToString( String remoteFileName, int retries ) throws FTPException
    {
        String contents = null;
        FTPException ftpex = null;
        while( retries >= 0 )
        {
            ftpex = null;
            try
            {
                contents = this.getFileToString( remoteFileName );
                break;
            }
            catch( FTPException e )
            {
                retries--;
                ftpex = e;
            }

            try { Thread.sleep( 1 * 1000 ); } catch( Exception e ) {}
        }

        if( ftpex != null )
        {
            throw ftpex;
        }

        return contents;
    }

    public void site( String command ) throws FTPException
    {
        try
        {
            //connect();
            int status = client.site( command );
            String statusMsg = client.getStatus();
            if( status <= 0 || ( status - 200 > 100 ) )
            {
                throw new FTPException( status, "Error executing command: " + statusMsg);
            }
            setLastAccessed();
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while executing site command: " + ex);
        }

    }

    public void makeDir(java.lang.String newName,  boolean remote) throws FTPException
    {
        try
        {
            //connect();
            int status = client.makeDir( newName, remote );
            String statusMsg = client.getStatus();
            if( status != 250 )
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Failed to change directory: " + statusMsg);
                }
                else
                {
                    throw new FTPException(status, statusMsg);
                }
            }
            if( remote )
            {
                setLastAccessed();
            }
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while changing directory: " + ex);
        }
    }

    public void changeDir(java.lang.String newName,  boolean remote) throws FTPException
    {
        try
        {
            //connect();
            int status = client.changeDir( newName, remote );
            String statusMsg = client.getStatus();
            if( status != 250 )
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Failed to change directory: " + statusMsg);
                }
                else
                {
                    throw new FTPException(status, statusMsg);
                }
            }
            if( remote )
            {
                setLastAccessed();
            }
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while changing directory: " + ex);
        }
    }

    public void deleteFile(String filename, boolean remote) throws FTPException
    {
        try
        {
            int status = client.deleteFile(filename, remote);
            String statusMsg = client.getStatus();
            if( status <= 0 || ( status - 200 > 100 ) )
            {
                throw new FTPException( status, "Error executing command: " + statusMsg);
            }
            if( remote )
            {
                setLastAccessed();
            }
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while deleting file: " + ex);
        }
    }

    public void setType(java.lang.String newType) throws FTPException
    {
        try
        {
            //connect();
            int status = client.setType(newType);
            String statusMsg = client.getStatus();
            if( status != 200 )
            {
                if( status == 550 )
                {
                    throw new FTPException( status, "Failed to change directory: " + statusMsg);
                }
                else
                {
                    throw new FTPException(status, statusMsg);
                }
            }
            setLastAccessed();
        }
        catch( FTPException e )
        {
            throw e;
        }
        catch (Exception ex)
        {
            throw new FTPException("Unknown exception while changing directory: " + ex);
        }
    }

    private boolean verifyConnected()
    {
        boolean bRet = false;

        try
        {
            boolean bTimedOut = isTimedOut();
            if( bTimedOut )
            {
                //System.out.println("FTP Connection internal timeout");
            }

            if( !bTimedOut && client.isStillConnected() )
            {
                //System.out.println("FTP reports connected = true");
                //String dummy = client.getfil.getRemoteDir();
                try
                {
                    this.site("JunkCommandToTestConnection");
                }
                catch( Exception e )
                {
                    //System.out.println("FTP connection test failed");
                }
                bRet = client.isStillConnected();
                //System.out.println("FTP reports connected = " + bRet);
            }
        }
        catch (Exception ex)
        {
        }
        return bRet;
    }

    private void setLastAccessed()
    {
        lastAccessed = Calendar.getInstance();
    }

    private boolean isTimedOut()
    {
        Calendar now = Calendar.getInstance();
        Calendar timeoutTime = (Calendar)lastAccessed.clone();
        timeoutTime.add( Calendar.MINUTE, 10);
        return now.after( timeoutTime);
    }

    private FTPProtocol client = new FTPProtocol();
    private String server;
    private String user;
    private String password;
    private boolean bPassiveServer = false;
    private Calendar lastAccessed = Calendar.getInstance();;
}
