# spring-boot-app-example
Spring Boot Hello Person example

# WebSocket Endpoint example:
http://localhost:8080

# REST Endpoint example:
http://localhost:8080/Hovhannisyan

# Run application 
mvn clean package && java -jar spring-boot-app-example-0.0.1.jar
## Running application from IDEA
* Please from IDEA please use following configs in metaring.config.json
```
      "ff4j": {
        "config": {
          "name": "ff4j-features-dev"
        }
      }
```
* For development mode from IDEA please use following configs in metaring.config.json
```
"data": {
        "mongodb": {
          "host": "localhost",
          "port": 27017,
          "database": "metaringPrototype"
        }
      }
```

## Running application as a Docker container
* For docker containers please use following configs in metaring.config.json
```
      "ff4j": {
        "config": {
          "name": "ff4j-features"
        }
      }
```
* For docker containers mode from IDEA please use following configs in metaring.config.json
```
"data": {
        "mongodb": {
          "host": "mongodb",
          "port": 27017,
          "database": "metaringPrototype"
        }
      }
```

# Build docker image
mvn dockerfile:build

# Create and run docker containers (TO RUN IN BASH)
APP_CONFIG='./src/main/resources' docker-compose up -d

# Quickly Build and run docker containers (TO RUN IN BASH)
mvn clean install && APP_CONFIG='./src/main/resources' docker-compose up

NOTE FOR WINDOWS USERS: 
- Please make sure that have installed docker desktop on your local machine and in Settings -> General -> (expose demon on tcp://localhost:2375 without TLS) is enabled.
- Set the environment variable COMPOSE_CONVERT_WINDOWS_PATHS=1
- In Settings -> Shared Drives share at least your main System Drive

# Short commands list for containers/images/volumes

### List all containers (only IDs)
docker ps -a   
### List all images 
docker images ls
### List all volumes
docker volume ls        
### Stop all running containers
docker stop $(docker ps -aq)
### Remove all containers
docker rm $(docker ps -aq)
### Remove all container(s) by id(s)
docker rm container_id1 container_id2
### Remove all images
docker rmi $(docker images -q)
### Removing All Unused Objects
docker system prune
### Remove image(s) by id(s)
docker rmi image_id1 image_id2 - delete image(s)
### Inspect the image
docker inspect image_id
### Inspect the container
docker inspect container_id

### verify records in Mongo
docker exec -i -t metaring-mongo-service /bin/bash 
root@ff55937c3772:/# mongo
root@ff55937c3772:/# show dbs
root@ff55937c3772:/# use metaringPrototype
root@ff55937c3772:/# db.users.find()
