package com.ibm.network.ftp.protocol;

import java.awt.Image;
import java.beans.*;
import java.lang.reflect.Method;

public class FTPProtocolBeanInfo extends SimpleBeanInfo
{

    public MethodDescriptor abortMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("abort", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "abort", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor addLocalFileListListener_comibmnetworkftpeventLocalFileListListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.LocalFileListListener.class
                };
                aMethod = getBeanClass().getMethod("addLocalFileListListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "addLocalFileListListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor addPropertyChangeListener_javabeansPropertyChangeListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.beans.PropertyChangeListener.class
                };
                aMethod = getBeanClass().getMethod("addPropertyChangeListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "addPropertyChangeListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor addRemoteFileListListener_comibmnetworkftpeventRemoteFileListListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.RemoteFileListListener.class
                };
                aMethod = getBeanClass().getMethod("addRemoteFileListListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "addRemoteFileListListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor addStatusListener_comibmnetworkftpeventStatusListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.StatusListener.class
                };
                aMethod = getBeanClass().getMethod("addStatusListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "addStatusListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor allowClientSideResumePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Boolean.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setAllowClientSideResume", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setAllowClientSideResume", 1);
                }
                aDescriptor = new PropertyDescriptor("allowClientSideResume", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("allowClientSideResume", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("preferred", new Boolean(true));
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor allowServerSideResumePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Boolean.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setAllowServerSideResume", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setAllowServerSideResume", 1);
                }
                aDescriptor = new PropertyDescriptor("allowServerSideResume", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("allowServerSideResume", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("preferred", new Boolean(true));
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor bufferSizePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getBufferSize", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getBufferSize", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Integer.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setBufferSize", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setBufferSize", 1);
                }
                aDescriptor = new PropertyDescriptor("bufferSize", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("bufferSize", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setExpert(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor bytesReadPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getBytesRead", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getBytesRead", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("bytesRead", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("bytesRead", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setHidden(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor bytesWritePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getBytesWrite", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getBytesWrite", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("bytesWrite", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("bytesWrite", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setHidden(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor cdupMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("cdup", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "cdup", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor changeDir_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("changeDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "changeDir", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("newName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor changeDirList_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("changeDirList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "changeDirList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("newName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor commandPerformed_comibmnetworkftpeventCommandEventMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.CommandEvent.class
                };
                aMethod = getBeanClass().getMethod("commandPerformed", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "commandPerformed", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("cevent");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor configureSocks_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("configureSocks", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "configureSocks", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("port");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor connect_javalangString_intMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Integer.TYPE
                };
                aMethod = getBeanClass().getMethod("connect", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "connect", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("port");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor connect_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("connect", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "connect", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("port");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor connect_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("connect", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "connect", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor connectLogin_javalangString_javalangString_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("connectLogin", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "connectLogin", 4);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("port");
                ParameterDescriptor aParameterDescriptor3 = new ParameterDescriptor();
                aParameterDescriptor3.setName("arg3");
                aParameterDescriptor3.setDisplayName("user");
                ParameterDescriptor aParameterDescriptor4 = new ParameterDescriptor();
                aParameterDescriptor4.setName("arg4");
                aParameterDescriptor4.setDisplayName("pwd");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2, aParameterDescriptor3, aParameterDescriptor4
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor connectLogin_javalangString_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("connectLogin", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "connectLogin", 3);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("hostName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("user");
                ParameterDescriptor aParameterDescriptor3 = new ParameterDescriptor();
                aParameterDescriptor3.setName("arg3");
                aParameterDescriptor3.setDisplayName("pwd");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2, aParameterDescriptor3
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor deleteFile_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("deleteFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "deleteFile", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor deleteFile_javautilVector_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("deleteFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "deleteFile", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor deleteFileList_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("deleteFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "deleteFileList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor deleteFileList_javautilVector_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("deleteFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "deleteFileList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor disconnectMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("disconnect", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "disconnect", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor downloadDirectoryRecursively_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("downloadDirectoryRecursively", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "downloadDirectoryRecursively", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor fileList_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("fileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "fileList", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public static Method findMethod(Class aClass, String methodName, int parameterCount)
    {
        try
        {
            Method methods[] = aClass.getMethods();
            for(int index = 0; index < methods.length; index++)
            {
                Method method = methods[index];
                if(method.getParameterTypes().length == parameterCount && method.getName().equals(methodName))
                {
                    return method;
                }
            }

        }
        catch(Throwable _ex)
        {
            return null;
        }
        return null;
    }

    public MethodDescriptor firePropertyChange_javalangString_javalangObject_javalangObjectMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.Object.class, java.lang.Object.class
                };
                aMethod = getBeanClass().getMethod("firePropertyChange", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "firePropertyChange", 3);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("propertyName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("oldValue");
                ParameterDescriptor aParameterDescriptor3 = new ParameterDescriptor();
                aParameterDescriptor3.setName("arg3");
                aParameterDescriptor3.setDisplayName("newValue");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2, aParameterDescriptor3
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor forceNLST_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("forceNLST", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "forceNLST", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("forceNLST");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public BeanInfo[] getAdditionalBeanInfo()
    {
        BeanInfo superBeanInfo = null;
        Class superClass;
        try
        {
            superClass = getBeanDescriptor().getBeanClass().getSuperclass();
        }
        catch(Throwable _ex)
        {
            return null;
        }
        try
        {
            superBeanInfo = Introspector.getBeanInfo(superClass);
        }
        catch(IntrospectionException _ex) { }
        if(superBeanInfo != null)
        {
            BeanInfo ret[] = new BeanInfo[1];
            ret[0] = superBeanInfo;
            return ret;
        } else
        {
            return null;
        }
    }

    public static Class getBeanClass()
    {
        return com.ibm.network.ftp.protocol.FTPProtocol.class;
    }

    public static String getBeanClassName()
    {
        return "com.ibm.network.ftp.protocol.FTPProtocol";
    }

    public BeanDescriptor getBeanDescriptor()
    {
        BeanDescriptor aDescriptor = null;
        try
        {
            aDescriptor = new BeanDescriptor(com.ibm.network.ftp.protocol.FTPProtocol.class);
            aDescriptor.setDisplayName("com.ibm.network.ftp.protocol.FTPProtocol");
            aDescriptor.setShortDescription("com.ibm.network.ftp.protocol.FTPProtocol");
        }
        catch(Throwable _ex) { }
        return aDescriptor;
    }

    public MethodDescriptor getBufferSizeMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getBufferSize", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getBufferSize", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getBytesReadMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getBytesRead", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getBytesRead", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getBytesWriteMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getBytesWrite", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getBytesWrite", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getCurrentDir_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("getCurrentDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getCurrentDir", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public EventSetDescriptor[] getEventSetDescriptors()
    {
        try
        {
            EventSetDescriptor aDescriptorList[] = {
                localFileListEventSetDescriptor(), propertyChangeEventSetDescriptor(), remoteFileListEventSetDescriptor(), statusEventSetDescriptor()
            };
            return aDescriptorList;
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return null;
    }

    public MethodDescriptor getFile_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("getFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getFile", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("localFileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getFile_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("getFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getFile", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getFile_javautilVectorMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class
                };
                aMethod = getBeanClass().getMethod("getFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getFile", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getFileList_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("getFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getFileList", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getFileList_javautilVectorMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class
                };
                aMethod = getBeanClass().getMethod("getFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getFileList", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public Image getIcon(int iconKind)
    {
        Image image = null;
        if(iconKind == 1)
        {
            image = loadImage("/com/ibm/network/ftp/protocol/c1616.gif");
        }
        if(iconKind == 3)
        {
            image = loadImage("/com/ibm/network/ftp/protocol/c1616.gif");
        }
        if(iconKind == 2)
        {
            image = loadImage("/com/ibm/network/ftp/protocol/c3232.gif");
        }
        if(iconKind == 4)
        {
            image = loadImage("/com/ibm/network/ftp/protocol/c3232.gif");
        }
        return image;
    }

    public MethodDescriptor getInpStreamMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getInpStream", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getInpStream", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getLocalDirMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getLocalDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getLocalDir", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getLocalFileListMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getLocalFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getLocalFileList", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor[] getMethodDescriptors()
    {
        try
        {
            MethodDescriptor aDescriptorList[] = {
                abortMethodDescriptor(), addLocalFileListListener_comibmnetworkftpeventLocalFileListListenerMethodDescriptor(), addPropertyChangeListener_javabeansPropertyChangeListenerMethodDescriptor(), addRemoteFileListListener_comibmnetworkftpeventRemoteFileListListenerMethodDescriptor(), addStatusListener_comibmnetworkftpeventStatusListenerMethodDescriptor(), cdupMethodDescriptor(), changeDir_javalangString_booleanMethodDescriptor(), changeDirList_javalangString_booleanMethodDescriptor(), commandPerformed_comibmnetworkftpeventCommandEventMethodDescriptor(), configureSocks_javalangString_javalangStringMethodDescriptor(), 
                connect_javalangString_intMethodDescriptor(), connect_javalangString_javalangStringMethodDescriptor(), connect_javalangStringMethodDescriptor(), connectLogin_javalangString_javalangString_javalangString_javalangStringMethodDescriptor(), connectLogin_javalangString_javalangString_javalangStringMethodDescriptor(), deleteFile_javalangString_booleanMethodDescriptor(), deleteFile_javautilVector_booleanMethodDescriptor(), deleteFileList_javalangString_booleanMethodDescriptor(), deleteFileList_javautilVector_booleanMethodDescriptor(), disconnectMethodDescriptor(), 
                downloadDirectoryRecursively_javalangStringMethodDescriptor(), fileList_booleanMethodDescriptor(), firePropertyChange_javalangString_javalangObject_javalangObjectMethodDescriptor(), forceNLST_booleanMethodDescriptor(), getBufferSizeMethodDescriptor(), getBytesReadMethodDescriptor(), getBytesWriteMethodDescriptor(), getCurrentDir_booleanMethodDescriptor(), getFile_javalangString_javalangStringMethodDescriptor(), getFile_javalangStringMethodDescriptor(), 
                getFile_javautilVectorMethodDescriptor(), getFileList_javalangStringMethodDescriptor(), getFileList_javautilVectorMethodDescriptor(), getInpStreamMethodDescriptor(), getLocalDirMethodDescriptor(), getLocalFileListMethodDescriptor(), getNameMethodDescriptor(), getOutStreamMethodDescriptor(), getRemoteDirMethodDescriptor(), getRemoteFileListMethodDescriptor(), 
                getRemoteOperatingSystemMethodDescriptor(), getRestartableMethodDescriptor(), getRestartCounterMethodDescriptor(), getRestartCountMethodDescriptor(), getSocksProxyHostMethodDescriptor(), getSocksProxyPortMethodDescriptor(), getStatusMethodDescriptor(), getTimeoutMethodDescriptor(), getTypeMethodDescriptor(), isStreamedInputMethodDescriptor(), 
                isStreamedOutputMethodDescriptor(), list_booleanMethodDescriptor(), login_javalangString_javalangStringMethodDescriptor(), makeDir_javalangString_booleanMethodDescriptor(), makeDirList_javalangString_booleanMethodDescriptor(), mget_javalangStringMethodDescriptor(), passiveServerMethodDescriptor(), putFile_javalangString_javalangStringMethodDescriptor(), putFile_javalangStringMethodDescriptor(), putFile_javautilVectorMethodDescriptor(), 
                putFileList_javalangStringMethodDescriptor(), putFileList_javautilVectorMethodDescriptor(), quote_javalangStringMethodDescriptor(), removeDir_javalangString_booleanMethodDescriptor(), removeList_javalangString_booleanMethodDescriptor(), removeList_javautilVector_booleanMethodDescriptor(), removeLocalFileListListener_comibmnetworkftpeventLocalFileListListenerMethodDescriptor(), removePropertyChangeListener_javabeansPropertyChangeListenerMethodDescriptor(), removeRemoteFileListListener_comibmnetworkftpeventRemoteFileListListenerMethodDescriptor(), removeStatusListener_comibmnetworkftpeventStatusListenerMethodDescriptor(), 
                rename_javalangString_javalangString_booleanMethodDescriptor(), renameList_javalangString_javalangString_booleanMethodDescriptor(), setAllowClientSideResume_booleanMethodDescriptor(), setAllowServerSideResume_booleanMethodDescriptor(), setBufferSize_intMethodDescriptor(), setInpStream_javaioInputStreamMethodDescriptor(), setName_javalangStringMethodDescriptor(), setOutStream_javaioOutputStreamMethodDescriptor(), setStreamedInput_booleanMethodDescriptor(), setStreamedOutput_booleanMethodDescriptor(), 
                setTimeout_intMethodDescriptor(), setType_javalangStringMethodDescriptor(), site_javalangStringMethodDescriptor(), statusMethodDescriptor(), systemMethodDescriptor(), uploadDirectoryRecursively_javalangStringMethodDescriptor()
            };
            return aDescriptorList;
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return null;
    }

    public MethodDescriptor getNameMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getName", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getName", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getOutStreamMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getOutStream", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getOutStream", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
            PropertyDescriptor aDescriptorList[] = {
                allowClientSideResumePropertyDescriptor(), allowServerSideResumePropertyDescriptor(), bufferSizePropertyDescriptor(), bytesReadPropertyDescriptor(), bytesWritePropertyDescriptor(), inpStreamPropertyDescriptor(), localDirPropertyDescriptor(), localFileListPropertyDescriptor(), namePropertyDescriptor(), outStreamPropertyDescriptor(), 
                remoteDirPropertyDescriptor(), remoteFileListPropertyDescriptor(), remoteOperatingSystemPropertyDescriptor(), restartablePropertyDescriptor(), restartCounterPropertyDescriptor(), restartCountPropertyDescriptor(), socksProxyHostPropertyDescriptor(), socksProxyPortPropertyDescriptor(), statusPropertyDescriptor(), stillConnectedPropertyDescriptor(), 
                streamedInputPropertyDescriptor(), streamedOutputPropertyDescriptor(), timeoutPropertyDescriptor(), typePropertyDescriptor()
            };
            return aDescriptorList;
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return null;
    }

    public MethodDescriptor getRemoteDirMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRemoteDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRemoteDir", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getRemoteFileListMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRemoteFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRemoteFileList", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getRemoteOperatingSystemMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRemoteOperatingSystem", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRemoteOperatingSystem", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getRestartableMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRestartable", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRestartable", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getRestartCounterMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRestartCounter", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRestartCounter", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getRestartCountMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getRestartCount", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getRestartCount", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getSocksProxyHostMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getSocksProxyHost", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getSocksProxyHost", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getSocksProxyPortMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getSocksProxyPort", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getSocksProxyPort", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getStatusMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getStatus", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getStatus", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getTimeoutMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getTimeout", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getTimeout", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor getTypeMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("getType", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "getType", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    private void handleException(Throwable throwable)
    {
    }

    public PropertyDescriptor inpStreamPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getInpStream", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getInpStream", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        java.io.InputStream.class
                    };
                    aSetMethod = getBeanClass().getMethod("setInpStream", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setInpStream", 1);
                }
                aDescriptor = new PropertyDescriptor("inpStream", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("inpStream", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setExpert(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor isStreamedInputMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("isStreamedInput", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "isStreamedInput", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor isStreamedOutputMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("isStreamedOutput", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "isStreamedOutput", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor list_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("list", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "list", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor localDirPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getLocalDir", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getLocalDir", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("localDir", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("localDir", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public EventSetDescriptor localFileListEventSetDescriptor()
    {
        EventSetDescriptor aDescriptor = null;
        try
        {
            try
            {
                MethodDescriptor eventMethodDescriptors[] = {
                    localFileListlocalFileListReceived_comibmnetworkftpeventLocalFileListEventMethodEventDescriptor()
                };
                Method anAddMethod = null;
                try
                {
                    Class anAddMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.LocalFileListListener.class
                    };
                    anAddMethod = getBeanClass().getMethod("addLocalFileListListener", anAddMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    anAddMethod = findMethod(getBeanClass(), "addLocalFileListListener", 1);
                }
                Method aRemoveMethod = null;
                try
                {
                    Class aRemoveMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.LocalFileListListener.class
                    };
                    aRemoveMethod = getBeanClass().getMethod("removeLocalFileListListener", aRemoveMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aRemoveMethod = findMethod(getBeanClass(), "removeLocalFileListListener", 1);
                }
                aDescriptor = new EventSetDescriptor("localFileList", com.ibm.network.ftp.event.LocalFileListListener.class, eventMethodDescriptors, anAddMethod, aRemoveMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                String eventMethodNames[] = {
                    "localFileListReceived"
                };
                aDescriptor = new EventSetDescriptor(getBeanClass(), "localFileList", com.ibm.network.ftp.event.LocalFileListListener.class, eventMethodNames, "addLocalFileListListener", "removeLocalFileListListener");
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor localFileListlocalFileListReceived_comibmnetworkftpeventLocalFileListEventMethodEventDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.LocalFileListEvent.class
                };
                aMethod = com.ibm.network.ftp.event.LocalFileListListener.class.getMethod("localFileListReceived", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(com.ibm.network.ftp.event.LocalFileListListener.class, "localFileListReceived", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("event");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor localFileListPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getLocalFileList", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getLocalFileList", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("localFileList", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("localFileList", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor login_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("login", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "login", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("userName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("passwd");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor makeDir_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("makeDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "makeDir", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor makeDirList_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("makeDirList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "makeDirList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor mget_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("mget", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "mget", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("filenameWithWildcards");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor namePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getName", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getName", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        java.lang.String.class
                    };
                    aSetMethod = getBeanClass().getMethod("setName", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setName", 1);
                }
                aDescriptor = new PropertyDescriptor("name", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("name", getBeanClass());
            }
            aDescriptor.setBound(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor outStreamPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getOutStream", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getOutStream", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        java.io.OutputStream.class
                    };
                    aSetMethod = getBeanClass().getMethod("setOutStream", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setOutStream", 1);
                }
                aDescriptor = new PropertyDescriptor("outStream", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("outStream", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setExpert(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor passiveServerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("passiveServer", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "passiveServer", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public EventSetDescriptor propertyChangeEventSetDescriptor()
    {
        EventSetDescriptor aDescriptor = null;
        try
        {
            try
            {
                MethodDescriptor eventMethodDescriptors[] = {
                    propertyChangepropertyChange_javabeansPropertyChangeEventMethodEventDescriptor()
                };
                Method anAddMethod = null;
                try
                {
                    Class anAddMethodParameterTypes[] = {
                        java.beans.PropertyChangeListener.class
                    };
                    anAddMethod = getBeanClass().getMethod("addPropertyChangeListener", anAddMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    anAddMethod = findMethod(getBeanClass(), "addPropertyChangeListener", 1);
                }
                Method aRemoveMethod = null;
                try
                {
                    Class aRemoveMethodParameterTypes[] = {
                        java.beans.PropertyChangeListener.class
                    };
                    aRemoveMethod = getBeanClass().getMethod("removePropertyChangeListener", aRemoveMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aRemoveMethod = findMethod(getBeanClass(), "removePropertyChangeListener", 1);
                }
                aDescriptor = new EventSetDescriptor("propertyChange", java.beans.PropertyChangeListener.class, eventMethodDescriptors, anAddMethod, aRemoveMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                String eventMethodNames[] = {
                    "propertyChange"
                };
                aDescriptor = new EventSetDescriptor(getBeanClass(), "propertyChange", java.beans.PropertyChangeListener.class, eventMethodNames, "addPropertyChangeListener", "removePropertyChangeListener");
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor propertyChangepropertyChange_javabeansPropertyChangeEventMethodEventDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.beans.PropertyChangeEvent.class
                };
                aMethod = java.beans.PropertyChangeListener.class.getMethod("propertyChange", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(java.beans.PropertyChangeListener.class, "propertyChange", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("evt");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor putFile_javalangString_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("putFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "putFile", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remoteFileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor putFile_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("putFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "putFile", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor putFile_javautilVectorMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class
                };
                aMethod = getBeanClass().getMethod("putFile", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "putFile", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor putFileList_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("putFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "putFileList", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor putFileList_javautilVectorMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class
                };
                aMethod = getBeanClass().getMethod("putFileList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "putFileList", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("fileNames");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor quote_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("quote", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "quote", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("param");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor remoteDirPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRemoteDir", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRemoteDir", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("remoteDir", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("remoteDir", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public EventSetDescriptor remoteFileListEventSetDescriptor()
    {
        EventSetDescriptor aDescriptor = null;
        try
        {
            try
            {
                MethodDescriptor eventMethodDescriptors[] = {
                    remoteFileListremoteFileListReceived_comibmnetworkftpeventRemoteFileListEventMethodEventDescriptor()
                };
                Method anAddMethod = null;
                try
                {
                    Class anAddMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.RemoteFileListListener.class
                    };
                    anAddMethod = getBeanClass().getMethod("addRemoteFileListListener", anAddMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    anAddMethod = findMethod(getBeanClass(), "addRemoteFileListListener", 1);
                }
                Method aRemoveMethod = null;
                try
                {
                    Class aRemoveMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.RemoteFileListListener.class
                    };
                    aRemoveMethod = getBeanClass().getMethod("removeRemoteFileListListener", aRemoveMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aRemoveMethod = findMethod(getBeanClass(), "removeRemoteFileListListener", 1);
                }
                aDescriptor = new EventSetDescriptor("remoteFileList", com.ibm.network.ftp.event.RemoteFileListListener.class, eventMethodDescriptors, anAddMethod, aRemoveMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                String eventMethodNames[] = {
                    "remoteFileListReceived"
                };
                aDescriptor = new EventSetDescriptor(getBeanClass(), "remoteFileList", com.ibm.network.ftp.event.RemoteFileListListener.class, eventMethodNames, "addRemoteFileListListener", "removeRemoteFileListListener");
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor remoteFileListPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRemoteFileList", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRemoteFileList", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("remoteFileList", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("remoteFileList", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor remoteFileListremoteFileListReceived_comibmnetworkftpeventRemoteFileListEventMethodEventDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.RemoteFileListEvent.class
                };
                aMethod = com.ibm.network.ftp.event.RemoteFileListListener.class.getMethod("remoteFileListReceived", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(com.ibm.network.ftp.event.RemoteFileListListener.class, "remoteFileListReceived", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("event");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor remoteOperatingSystemPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRemoteOperatingSystem", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRemoteOperatingSystem", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("remoteOperatingSystem", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("remoteOperatingSystem", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeDir_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("removeDir", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeDir", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeList_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("removeList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeList_javautilVector_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.util.Vector.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("removeList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeList", 2);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("dirNames");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeLocalFileListListener_comibmnetworkftpeventLocalFileListListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.LocalFileListListener.class
                };
                aMethod = getBeanClass().getMethod("removeLocalFileListListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeLocalFileListListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removePropertyChangeListener_javabeansPropertyChangeListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.beans.PropertyChangeListener.class
                };
                aMethod = getBeanClass().getMethod("removePropertyChangeListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removePropertyChangeListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeRemoteFileListListener_comibmnetworkftpeventRemoteFileListListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.RemoteFileListListener.class
                };
                aMethod = getBeanClass().getMethod("removeRemoteFileListListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeRemoteFileListListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor removeStatusListener_comibmnetworkftpeventStatusListenerMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.StatusListener.class
                };
                aMethod = getBeanClass().getMethod("removeStatusListener", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "removeStatusListener", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("listener");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor rename_javalangString_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("rename", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "rename", 3);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("oldName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("newName");
                ParameterDescriptor aParameterDescriptor3 = new ParameterDescriptor();
                aParameterDescriptor3.setName("arg3");
                aParameterDescriptor3.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2, aParameterDescriptor3
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor renameList_javalangString_javalangString_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class, java.lang.String.class, Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("renameList", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "renameList", 3);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("oldName");
                ParameterDescriptor aParameterDescriptor2 = new ParameterDescriptor();
                aParameterDescriptor2.setName("arg2");
                aParameterDescriptor2.setDisplayName("newName");
                ParameterDescriptor aParameterDescriptor3 = new ParameterDescriptor();
                aParameterDescriptor3.setName("arg3");
                aParameterDescriptor3.setDisplayName("remote");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1, aParameterDescriptor2, aParameterDescriptor3
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor restartablePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRestartable", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRestartable", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("restartable", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("restartable", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor restartCounterPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRestartCounter", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRestartCounter", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("restartCounter", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("restartCounter", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor restartCountPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getRestartCount", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getRestartCount", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("restartCount", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("restartCount", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setAllowClientSideResume_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("setAllowClientSideResume", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setAllowClientSideResume", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("b");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setAllowServerSideResume_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("setAllowServerSideResume", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setAllowServerSideResume", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("b");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setBufferSize_intMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Integer.TYPE
                };
                aMethod = getBeanClass().getMethod("setBufferSize", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setBufferSize", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("bufferSize");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setInpStream_javaioInputStreamMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.io.InputStream.class
                };
                aMethod = getBeanClass().getMethod("setInpStream", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setInpStream", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("in");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setName_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("setName", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setName", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("newValue");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setOutStream_javaioOutputStreamMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.io.OutputStream.class
                };
                aMethod = getBeanClass().getMethod("setOutStream", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setOutStream", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("out");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setStreamedInput_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("setStreamedInput", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setStreamedInput", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("in");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setStreamedOutput_booleanMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Boolean.TYPE
                };
                aMethod = getBeanClass().getMethod("setStreamedOutput", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setStreamedOutput", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("out");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setTimeout_intMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    Integer.TYPE
                };
                aMethod = getBeanClass().getMethod("setTimeout", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setTimeout", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("to");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor setType_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("setType", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "setType", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("newType");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor site_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("site", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "site", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("s");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor socksProxyHostPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getSocksProxyHost", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getSocksProxyHost", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("socksProxyHost", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("socksProxyHost", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor socksProxyPortPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getSocksProxyPort", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getSocksProxyPort", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("socksProxyPort", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("socksProxyPort", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public EventSetDescriptor statusEventSetDescriptor()
    {
        EventSetDescriptor aDescriptor = null;
        try
        {
            try
            {
                MethodDescriptor eventMethodDescriptors[] = {
                    statusstatusReceived_comibmnetworkftpeventStatusEventMethodEventDescriptor()
                };
                Method anAddMethod = null;
                try
                {
                    Class anAddMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.StatusListener.class
                    };
                    anAddMethod = getBeanClass().getMethod("addStatusListener", anAddMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    anAddMethod = findMethod(getBeanClass(), "addStatusListener", 1);
                }
                Method aRemoveMethod = null;
                try
                {
                    Class aRemoveMethodParameterTypes[] = {
                        com.ibm.network.ftp.event.StatusListener.class
                    };
                    aRemoveMethod = getBeanClass().getMethod("removeStatusListener", aRemoveMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aRemoveMethod = findMethod(getBeanClass(), "removeStatusListener", 1);
                }
                aDescriptor = new EventSetDescriptor("status", com.ibm.network.ftp.event.StatusListener.class, eventMethodDescriptors, anAddMethod, aRemoveMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                String eventMethodNames[] = {
                    "statusReceived"
                };
                aDescriptor = new EventSetDescriptor(getBeanClass(), "status", com.ibm.network.ftp.event.StatusListener.class, eventMethodNames, "addStatusListener", "removeStatusListener");
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor statusMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("status", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "status", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor statusPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getStatus", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getStatus", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("status", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("status", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor statusstatusReceived_comibmnetworkftpeventStatusEventMethodEventDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    com.ibm.network.ftp.event.StatusEvent.class
                };
                aMethod = com.ibm.network.ftp.event.StatusListener.class.getMethod("statusReceived", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(com.ibm.network.ftp.event.StatusListener.class, "statusReceived", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("event");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor stillConnectedPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("isStillConnected", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "isStillConnected", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("stillConnected", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("stillConnected", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor streamedInputPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("isStreamedInput", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "isStreamedInput", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Boolean.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setStreamedInput", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setStreamedInput", 1);
                }
                aDescriptor = new PropertyDescriptor("streamedInput", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("streamedInput", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setExpert(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor streamedOutputPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("isStreamedOutput", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "isStreamedOutput", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Boolean.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setStreamedOutput", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setStreamedOutput", 1);
                }
                aDescriptor = new PropertyDescriptor("streamedOutput", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("streamedOutput", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setExpert(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor systemMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = new Class[0];
                aMethod = getBeanClass().getMethod("system", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "system", 0);
            }
            try
            {
                ParameterDescriptor aParameterDescriptors[] = new ParameterDescriptor[0];
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor timeoutPropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getTimeout", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getTimeout", 0);
                }
                Method aSetMethod = null;
                try
                {
                    Class aSetMethodParameterTypes[] = {
                        Integer.TYPE
                    };
                    aSetMethod = getBeanClass().getMethod("setTimeout", aSetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aSetMethod = findMethod(getBeanClass(), "setTimeout", 1);
                }
                aDescriptor = new PropertyDescriptor("timeout", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("timeout", getBeanClass());
            }
            aDescriptor.setBound(true);
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public PropertyDescriptor typePropertyDescriptor()
    {
        PropertyDescriptor aDescriptor = null;
        try
        {
            try
            {
                Method aGetMethod = null;
                try
                {
                    Class aGetMethodParameterTypes[] = new Class[0];
                    aGetMethod = getBeanClass().getMethod("getType", aGetMethodParameterTypes);
                }
                catch(Throwable exception)
                {
                    handleException(exception);
                    aGetMethod = findMethod(getBeanClass(), "getType", 0);
                }
                Method aSetMethod = null;
                aDescriptor = new PropertyDescriptor("type", aGetMethod, aSetMethod);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new PropertyDescriptor("type", getBeanClass());
            }
            aDescriptor.setBound(true);
            aDescriptor.setValue("preferred", new Boolean(true));
            aDescriptor.setValue("ivjDesignTimeProperty", new Boolean(false));
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public MethodDescriptor uploadDirectoryRecursively_javalangStringMethodDescriptor()
    {
        MethodDescriptor aDescriptor = null;
        try
        {
            Method aMethod = null;
            try
            {
                Class aParameterTypes[] = {
                    java.lang.String.class
                };
                aMethod = getBeanClass().getMethod("uploadDirectoryRecursively", aParameterTypes);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aMethod = findMethod(getBeanClass(), "uploadDirectoryRecursively", 1);
            }
            try
            {
                ParameterDescriptor aParameterDescriptor1 = new ParameterDescriptor();
                aParameterDescriptor1.setName("arg1");
                aParameterDescriptor1.setDisplayName("localDirName");
                ParameterDescriptor aParameterDescriptors[] = {
                    aParameterDescriptor1
                };
                aDescriptor = new MethodDescriptor(aMethod, aParameterDescriptors);
            }
            catch(Throwable exception)
            {
                handleException(exception);
                aDescriptor = new MethodDescriptor(aMethod);
            }
        }
        catch(Throwable exception)
        {
            handleException(exception);
        }
        return aDescriptor;
    }

    public FTPProtocolBeanInfo()
    {
    }
}
