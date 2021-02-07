# Netty Remoting All

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bc80abd17a444f0ba0d94ec807e07843)](https://app.codacy.com/manual/zhouhailin/netty-remoting-all?utm_source=github.com&utm_medium=referral&utm_content=zhouhailin/netty-remoting-all&utm_campaign=Badge_Grade_Settings)
[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/netty-remoting-all/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/netty-remoting-all/)
[![Gitter](https://badges.gitter.im/netty-remoting-all/community.svg)](https://gitter.im/netty-remoting-all/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## 使用

```xml

<dependency>
    <groupId>link.thingscloud</groupId>
    <artifactId>netty-remoting-all</artifactId>
    <version>0.2.0</version>
</dependency>
```

## 模块说明

### 1.netty-remoting

```java
RemotingServer remotingServer=RemotingBootstrapFactory.createRemotingServer(new RemotingServerConfig());
        remotingServer.start();
```

```java
RemotingClient remotingClient=RemotingBootstrapFactory.createRemotingClient(new RemotingClientConfig());
        RemotingCommand request=remotingClient.commandFactory().createRequest();
        remotingClient.invoke("127.0.0.1:8888",request,1000);
        remotingClient.start();
```

### 2.netty-remoting-spring-boot-starter

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
