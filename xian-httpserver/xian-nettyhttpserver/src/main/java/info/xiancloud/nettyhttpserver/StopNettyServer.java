package info.xiancloud.nettyhttpserver;

import info.xiancloud.core.init.shutdown.ShutdownHook;
import info.xiancloud.core.util.LOG;

/**
 * netty http server shutdown hook
 *
 * @author happyyangyuan
 */
public class StopNettyServer implements ShutdownHook {

    @Override
    public boolean shutdown() {
        synchronized (NettyServer.HTTP_SERVER_START_STOP_LOCK) {
            if (StartNettyServer.nettyServerSingleton != null) {
                StartNettyServer.nettyServerSingleton.stopServer();
                return true;
            } else {
                LOG.warn("httpServer没启动啊！停什么鬼?难道你想报空指针不成？");
                return false;
            }
        }
    }

}
