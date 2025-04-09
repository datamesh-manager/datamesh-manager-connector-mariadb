package datameshmanager.mariadb;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "datameshmanager.client.mariadb")
public record MariaDbProperties(
        ConnectionProperties connection,
        AssetsProperties assets
) {

    public record ConnectionProperties(
            String host,
            int port,
            String database,
            String username,
            String password
    ) {
    }

    public record AssetsProperties(
            Boolean enabled,
            String connectorid,
            Duration pollinterval
    ) {
    }
}