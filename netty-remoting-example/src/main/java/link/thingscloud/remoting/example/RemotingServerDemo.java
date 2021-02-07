package link.thingscloud.remoting.example;

import link.thingscloud.remoting.RemotingBootstrapFactory;
import link.thingscloud.remoting.api.RemotingServer;
import link.thingscloud.remoting.config.RemotingServerConfig;

/**
 * @author zhouhailin
 * @version 1.0.0
 */
public class RemotingServerDemo {
    public static void main(String[] args) {
        RemotingServer remotingServer = RemotingBootstrapFactory.createRemotingServer(new RemotingServerConfig());
        remotingServer.start();
    }
}
