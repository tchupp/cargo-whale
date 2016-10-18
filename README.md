# CargoWhaleDocker

## Development

Maven 3+ is required to build the application.
To start in the dev profile, run:

    mvn spring-boot:run

## Testing

To launch the tests, run:

    mvn clean test
    
## Using Docker

To fully dockerize the application and all the services that it depends on, run:

    mvn package docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up
    
Or:

    docker-compose -f src/main/docker/app.yml up -d
    
To run as a daemon.

To stop & remove the daemon, run:

    docker-compose -f src/main/docker/app.yml down
