package com.ibm.network.ftp.protocol;

import com.ibm.network.ftp.FileInfo;
import java.io.File;
import java.util.Vector;

// Referenced classes of package com.ibm.network.ftp.protocol:
//            FTPProtocol

public class Local
{

    private FTPProtocol parent;
    private String localCurrentDir;
    private Vector localFileList;

    protected Local(FTPProtocol parent)
    {
        localCurrentDir = null;
        this.parent = parent;
    }

    public int changeDir(String newDirString)
    {
        if(newDirString.equals("."))
        {
            printText("lcd " + newDirString + "\n");
            return 250;
        }
        String currentDirString;
        File currentDir;
        if(newDirString.equals(".."))
        {
            currentDirString = getCurrentDir();
            currentDir = new File(currentDirString);
            String parentDirString = currentDir.getParent();
            if(parentDirString != null)
            {
                localCurrentDir = parentDirString;
                printText("lcd " + newDirString + "\n");
                return 250;
            } else
            {
                printText("lcd " + newDirString + "\n");
                printText("The parent directory doesn't exist \n");
            }
            return 550;
        }
        currentDirString = getCurrentDir();
        currentDir = new File(currentDirString);
        File newDir = new File(newDirString);
        if(currentDirString.endsWith("\\"))
        {
            int length = currentDirString.length();
            currentDirString = new String(currentDirString.substring(0, length - 1));
        }
        if(newDir.isAbsolute())
        {
            if(newDir.isDirectory())
            {
                localCurrentDir = newDirString;
                printText("lcd " + newDirString + "\n");
                return 250;
            }
        } else
        {
            if(newDir.equals(".."))
            {
                String parentDir = currentDir.getParent();
                if(parentDir != null)
                {
                    localCurrentDir = parentDir;
                    printText("lcd ..");
                } else
                {
                    printText("no parent dir\n");
                }
                return 250;
            }
            if((new File(currentDirString + File.separatorChar + newDirString)).isDirectory())
            {
                String childDir = new String(currentDirString + File.separatorChar + newDirString);
                if(childDir != null)
                {
                    localCurrentDir = childDir;
                    printText("lcd   " + newDirString + "\n");
                    return 250;
                }
            }
        }
        printText("Directory " + newDirString + " not found" + "\n");
        return 550;
    }

    public void deleteDir(String dirName)
    {
        String currentDir = getCurrentDir();
        File file = new File(currentDir + File.separatorChar + dirName);
        printText("del  " + dirName + "\n");
        if(file.exists())
        {
            if(file.isDirectory())
            {
                if(file.delete())
                {
                    printText("deleted file  " + dirName);
                } else
                {
                    printText("could not delete file " + dirName + "may not be empty \n");
                }
            }
        } else
        {
            printText("file  " + dirName + "  doesn't exists \n");
        }
    }

    public void deleteFile(String fileName)
    {
        String currentDir = getCurrentDir();
        File file = new File(currentDir + File.separatorChar + fileName);
        printText("del  " + fileName);
        printText("\n");
        if(file.exists())
        {
            if(file.isFile())
            {
                if(file.delete())
                {
                    printText("deleted file  " + fileName);
                    printText("\n");
                } else
                {
                    printText("could not delete file  " + fileName);
                }
            } else
            if(file.delete())
            {
                printText("deleted file  " + fileName);
                printText("\n");
            } else
            {
                printText("could not delete file " + fileName + "may not be empty \n");
            }
        } else
        {
            printText("file  " + fileName + "  doesn't exists");
        }
    }

    public Vector fileList()
    {
        Vector fileList = null;
        printText("local file list\n");
        String currentDirString = getCurrentDir();
        int dirLength = currentDirString.length();
        if(currentDirString.charAt(dirLength - 1) == File.separatorChar)
        {
            currentDirString = currentDirString + ".";
        }
        File currentDir = new File(currentDirString);
        String fileListString[] = currentDir.list();
        fileList = new Vector();
        for(int i = 0; i < fileListString.length; i++)
        {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setLocalDescription(fileListString[i], currentDirString);
            fileList.addElement(fileInfo);
        }

        localFileList = fileList;
        return fileList;
    }

    public String getCurrentDir()
    {
        if(localCurrentDir == null)
        {
            String currentDir = System.getProperty("user.dir");
            localCurrentDir = currentDir;
            return currentDir;
        } else
        {
            return localCurrentDir;
        }
    }

    public String getLocalCurrentDir()
    {
        return localCurrentDir;
    }

    public Vector getLocalFileList()
    {
        return localFileList;
    }

    public int makeDir(String dirName)
    {
        String currentDir = getCurrentDir();
        printText("mkdir " + dirName + "\n");
        File file = new File(currentDir + File.separatorChar + dirName);
        if(!file.exists())
        {
            if(file.mkdirs())
            {
                printText("Created directory " + dirName + "\n");
                return 257;
            } else
            {
                printText("Could not create directory " + dirName + "\n");
                return 550;
            }
        } else
        {
            printText(dirName + " already exists\n");
            return 550;
        }
    }

    protected void printText(String text)
    {
        parent.sendMessage(text);
    }

    public void rename(String oldName, String newName)
    {
        String currentDir = getCurrentDir();
        printText("rename " + oldName + "  " + newName + "\n");
        if((new File(newName)).isAbsolute())
        {
            printText("ERROR: You tried replacing relative path " + oldName + " with absolute path " + newName + "\n");
        } else
        {
            File oldFile = new File(currentDir + File.separatorChar + oldName);
            File newFile = new File(currentDir + File.separatorChar + newName);
            if(oldFile.exists())
            {
                if(oldFile.renameTo(newFile))
                {
                    printText("renamed  " + oldName + " to " + newName + "\n");
                } else
                {
                    printText("could not rename  " + oldName + " to " + newName + "\n");
                }
            } else
            {
                printText(oldName + " not found\n");
            }
        }
    }
}
