# CargoWhaleDocker

## Development

Maven 3+ is required to build the application.
To start in the dev profile, run:

    mvn spring-boot:run

## Using Docker

Docker and Docker Compose are utilized to run the application in a production environment.
Docker v1.10+ and Docker Compose v1.8+ are required.

To fully dockerize the application and all the services that it depends on, run:

    mvn package -Pprod docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up
    
Or:

    docker-compose -f src/main/docker/app.yml up -d
    
To run as a daemon.

To stop & remove the daemon, run:

    docker-compose -f src/main/docker/app.yml down
