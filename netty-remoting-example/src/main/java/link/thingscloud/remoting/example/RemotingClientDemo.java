package link.thingscloud.remoting.example;

import link.thingscloud.remoting.RemotingBootstrapFactory;
import link.thingscloud.remoting.api.RemotingClient;
import link.thingscloud.remoting.api.command.RemotingCommand;
import link.thingscloud.remoting.config.RemotingClientConfig;

/**
 * @author zhouhailin
 * @version 1.0.0
 */
public class RemotingClientDemo {
    public static void main(String[] args) {
        RemotingClient remotingClient = RemotingBootstrapFactory.createRemotingClient(new RemotingClientConfig());
        RemotingCommand request = remotingClient.commandFactory().createRequest();
        remotingClient.invoke("127.0.0.1:8888", request, 1000);
        remotingClient.start();
    }
}
