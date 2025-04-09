# DataMesh Manager Connector for MariaDB

This connector integrates MariaDB database assets with the DataMesh Manager platform. It extracts database schemas, tables, and views from your MariaDB database and synchronizes them with your DataMesh Manager instance.

## Features

- Extract schemas from MariaDB databases
- Extract tables with column metadata
- Extract views with column metadata
- Filter out system schemas like `information_schema`
- Schedule periodic synchronization with configurable intervals
- State tracking to only synchronize changed assets

## Configuration

The connector can be configured using environment variables or in the `application.yml` file:

```yaml
datameshmanager:
  client:
    host: https://api.datamesh-manager.com
    apikey: your-api-key
    
    # MariaDB Connector settings
    mariadb:
      connection:
        host: localhost
        port: 3306
        database: yourdb
        username: yourusername
        password: yourpassword
        
      assets:
        enabled: true
        connectorid: mariadb-assets
        pollinterval: PT10M  # ISO-8601 duration format - 10 minutes
```

### Environment Variables

| Environment Variable | Description | Default |
|----------------------|-------------|---------|
| `DATAMESHMANAGER_HOST` | DataMesh Manager API host | https://api.datamesh-manager.com |
| `DATAMESHMANAGER_APIKEY` | Your DataMesh Manager API key | - |
| `MARIADB_HOST` | MariaDB server hostname | localhost |
| `MARIADB_PORT` | MariaDB server port | 3306 |
| `MARIADB_DATABASE` | Database name to connect to | - |
| `MARIADB_USERNAME` | Username for MariaDB connection | - |
| `MARIADB_PASSWORD` | Password for MariaDB connection | - |
| `MARIADB_ASSETS_ENABLED` | Enable assets synchronization | true |
| `MARIADB_ASSETS_CONNECTORID` | Unique ID for this connector instance | mariadb-assets |
| `MARIADB_ASSETS_POLLINTERVAL` | Synchronization interval in ISO-8601 duration format | PT10M (10 minutes) |

## Building

```bash
./mvnw clean package
```

## Running

### Using Java

```bash
./mvnw spring-boot:run
```

### Using Docker

```bash
docker build -t datamesh-manager/connector-mariadb .

docker run -d \
  -e DATAMESHMANAGER_HOST=https://api.datamesh-manager.com \
  -e DATAMESHMANAGER_APIKEY=your-api-key \
  -e MARIADB_HOST=your-mariadb-server \
  -e MARIADB_PORT=3306 \
  -e MARIADB_DATABASE=your-database \
  -e MARIADB_USERNAME=your-username \
  -e MARIADB_PASSWORD=your-password \
  -e MARIADB_ASSETS_CONNECTORID=mariadb-assets \
  -p 8080:8080 \
  --name datamesh-manager-connector-mariadb \
  datamesh-manager/connector-mariadb
```

## Development

### Prerequisites

- Java 17
- Maven or the included Maven Wrapper
- Docker (for running tests with TestContainers)

### Running Tests

```bash
./mvnw test
```

## Implementation Details

The connector implements the DataMesh Manager SDK interfaces:

1. `DataMeshManagerAssetsProvider` - Extracts metadata from MariaDB using JDBC DatabaseMetaData
2. Uses the DataMesh Manager SDK's synchronization functionality to maintain state
3. Transforms MariaDB metadata into the DataMesh Manager Asset model
4. Schedules regular synchronization based on configured intervals

## Health Check

The connector exposes health and info endpoints:

- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/info`

## License

[Apache 2.0](LICENSE)