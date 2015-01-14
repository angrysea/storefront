package com.ibm.network.ftp.event;

import java.util.EventListener;

// Referenced classes of package com.ibm.network.ftp.event:
//            LocalFileListEvent

public interface LocalFileListListener
    extends EventListener
{

    public abstract void localFileListReceived(LocalFileListEvent localfilelistevent);
}
