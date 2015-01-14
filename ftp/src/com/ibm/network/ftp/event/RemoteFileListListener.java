package com.ibm.network.ftp.event;

import java.util.EventListener;

// Referenced classes of package com.ibm.network.ftp.event:
//            RemoteFileListEvent

public interface RemoteFileListListener
    extends EventListener
{

    public abstract void remoteFileListReceived(RemoteFileListEvent remotefilelistevent);
}
