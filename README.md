Data Mesh Manager Connector for MariaDB
===

The connector for MariaDB is a Spring Boot application that uses the [datamesh-manager-sdk](https://github.com/datamesh-manager/datamesh-manager-sdk) internally, and is available as a ready-to-use Docker image [datameshmanager/datamesh-manager-connector-mariadb](https://hub.docker.com/repository/docker/datameshmanager/datamesh-manager-connector-mariadb) to be deployed in your environment.

## Features

- **Asset Synchronization**: Extract database schemas, tables, and views from your MariaDB database and synchronize them with your DataMesh Manager instance.
- Filter out system schemas like `information_schema`
- Schedule periodic synchronization with configurable intervals
- State tracking to only synchronize changed assets

## Usage

Start the connector using Docker. You must pass the API keys as environment variables.

```
docker run \
  -e DATAMESHMANAGER_CLIENT_APIKEY='insert-api-key-here' \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_HOST='your-mariadb-server' \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PORT='3306' \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_DATABASE='your-database' \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_USERNAME='your-username' \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PASSWORD='your-password' \
  datameshmanager/datamesh-manager-connector-mariadb:latest
```

## Configuration

| Environment Variable                                    | Default Value                      | Description                                                                       |
|--------------------------------------------------------|------------------------------------|-----------------------------------------------------------------------------------|
| `DATAMESHMANAGER_CLIENT_HOST`                           | `https://api.datamesh-manager.com` | Base URL of the Data Mesh Manager API.                                            |
| `DATAMESHMANAGER_CLIENT_APIKEY`                         |                                    | API key for authenticating requests to the Data Mesh Manager.                     |
| `DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_HOST`        | `localhost`                        | MariaDB server hostname                                                           |
| `DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PORT`        | `3306`                             | MariaDB server port                                                               |
| `DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_DATABASE`    |                                    | Database name to connect to                                                       |
| `DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_USERNAME`    |                                    | Username for MariaDB connection                                                   |
| `DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PASSWORD`    |                                    | Password for MariaDB connection                                                   |
| `DATAMESHMANAGER_CLIENT_MARIADB_ASSETS_ENABLED`         | `true`                             | Enable assets synchronization                                                     |
| `DATAMESHMANAGER_CLIENT_MARIADB_ASSETS_CONNECTORID`     | `mariadb-assets`                   | Unique ID for this connector instance                                             |
| `DATAMESHMANAGER_CLIENT_MARIADB_ASSETS_POLLINTERVAL`    | `PT10M`                            | Synchronization interval in ISO-8601 duration format (PT10M means 10 minutes)     |

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
docker build -t datameshmanager/datamesh-manager-connector-mariadb .

docker run -d \
  -e DATAMESHMANAGER_CLIENT_HOST=https://api.datamesh-manager.com \
  -e DATAMESHMANAGER_CLIENT_APIKEY=your-api-key \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_HOST=your-mariadb-server \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PORT=3306 \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_DATABASE=your-database \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_USERNAME=your-username \
  -e DATAMESHMANAGER_CLIENT_MARIADB_CONNECTION_PASSWORD=your-password \
  -e DATAMESHMANAGER_CLIENT_MARIADB_ASSETS_CONNECTORID=mariadb-assets \
  -p 8080:8080 \
  --name datamesh-manager-connector-mariadb \
  datameshmanager/datamesh-manager-connector-mariadb
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