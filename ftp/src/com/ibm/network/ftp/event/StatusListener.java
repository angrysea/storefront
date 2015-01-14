package com.ibm.network.ftp.event;

import java.util.EventListener;

// Referenced classes of package com.ibm.network.ftp.event:
//            StatusEvent

public interface StatusListener
    extends EventListener
{

    public abstract void statusReceived(StatusEvent statusevent);
}
