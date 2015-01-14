package com.ibm.network.ftp.event;

import java.util.EventListener;

// Referenced classes of package com.ibm.network.ftp.event:
//            CommandEvent

public interface CommandListener
    extends EventListener
{

    public abstract void commandPerformed(CommandEvent commandevent);
}
