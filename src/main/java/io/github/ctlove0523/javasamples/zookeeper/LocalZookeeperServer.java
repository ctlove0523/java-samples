package io.github.ctlove0523.javasamples.zookeeper;

import org.apache.commons.io.FileUtils;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.admin.AdminServer;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

class LocalZookeeperServer implements ZookeeperServer {
    private static final Logger log = LoggerFactory.getLogger(LocalZookeeperServer.class);

    private ZooKeeperServerMain server;
    private int port = 2181;
    private String dataDir;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalZookeeperServer(Builder builder) {
        this.port = builder.getPort();
        this.dataDir = builder.getDataDir();
        this.server = new ZooKeeperServerMain();
    }


    @Override
    public boolean start() {
        try {
            Properties properties = new Properties();
            properties.setProperty("dataDir", dataDir);
            properties.setProperty("clientPort", port+"");

            cleanDatDir(dataDir);
            QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
            quorumPeerConfig.parseProperties(properties);
            ServerConfig serverConfig = new ServerConfig();
            serverConfig.readFrom(quorumPeerConfig);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        server.runFromConfig(serverConfig);
                    } catch (IOException | AdminServer.AdminServerException e) {
                        e.printStackTrace();
                    }
                }
            });

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    shutdown();
                }
            }));
            log.info("start zookeeper server success");
            return true;
        } catch (Exception e) {
            log.warn("start zookeeper server failed ", e);
        }

        log.warn("start zookeeper server failed");
        return false;
    }

    @Override
    public boolean shutdown() {
        System.out.println("begin to shutdown");
        try {
            cleanDatDir(dataDir);
            return true;
        } catch (Exception e) {
            log.warn("failed to shutdown ", e);
        }
        return false;
    }

    private void cleanDatDir(String dataDir) {
        System.out.println("begin to clean data dir");
        try {
            Files.list(new File(dataDir).toPath())
                    .forEach(new Consumer<Path>() {
                        @Override
                        public void accept(Path path) {
                            System.out.println(path.getFileName());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
