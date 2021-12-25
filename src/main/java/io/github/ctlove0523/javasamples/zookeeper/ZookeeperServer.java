package io.github.ctlove0523.javasamples.zookeeper;

public interface ZookeeperServer {

    static Builder newBuilder() {
        return new Builder();
    }

    boolean start();

    boolean shutdown();


    class Builder {
        private String dataDir;
        private int port;

        private Builder() {

        }

        public Builder withDatDir(String dataDir) {
            this.dataDir = dataDir;
            return this;
        }

        public Builder withPort(int port) {
            if (port < 0 || port > 65535) {
                throw new IllegalArgumentException("port invalid");
            }
            this.port = port;
            return this;
        }

        public ZookeeperServer build() {
            return new LocalZookeeperServer(this);
        }



        public String getDataDir() {
            return dataDir;
        }

        public int getPort() {
            return port;
        }
    }
}
