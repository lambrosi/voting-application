# Voting Application
The purpose of this application is control voting sessions. Its possible create a topic, open a session, and post votes on topics.

## Execution flow
This diagram was made to illustrate the flow of execution.
<p align="center">
  <img src="https://i.imgur.com/jGzMvMt.png">
</p>

## Technical decisions
#### Separation of concerns
Two distinct services were created for this application: voting-service and voting-totalizer-producer-service. The first service expose APIs to create topic, open sessions, make votes and etc. The second service is responsible for pooling for closed sessions able to send totalizers.

In a scenario of increasing votes and database queries, services can be scaled in isolation.

#### API versioning
The way the APIs were versioned was by adding a version number to the URI. This way, each version has its contracts (request/response) defined and if a new API version is required, it is easy to configure another route with a different/updated API version ('/v2' for example).

#### Monitoring
One concern when developing was to have a clear way to view the application logs. It is important to have an easy view of what is happening internally in execution for easy troubleshooting.

With that in mind, was used the ELK Stack. Thus, a Filebeat installed on the machine where the applications are located monitors the log files and sends this data to Logstash.

One index were created for this visualization:
*   "application-log" for the logs of the two services

#### Testing
Unit tests were implemented for both services. As all business logic is in voting-service, mutation tests were implemented to ensure that possible paths were covered. Pitest was used for mutation tests.
To run them, in the service folder enter
```sh
<voting-totalizer-producer-service>:~$ ./gradlew clean test

<voting-service>:~$ ./gradlew clean test
<voting-service>:~$ ./gradlew clean pitest
```

## How to run
#### 1. Provisioning an EC2
To run this application in the cloud (AWS), you will need an EC2 instance with Java, Docker and Docker-Compose up and running. If you already have one go to step 2.

In this repo, are included Terraform scripts that will provision a machine on AWS. In the 'voting-ec2.tf' file, replace the 
${instance.type} for the instance type what do you want and ${key.name} with your key_name of your KeyPair. To run this script, you will need aws-cli configured.

To initialize a working directory containing Terraform configuration files:
```sh
:~$ terraform init
```

To apply the changes in the configuration files in the provider.
```sh
:~$ terraform apply
```
When the machine is up, do SSH:
```sh
:~$ ssh -i "key_pair_file.pem" <user@machine>.amazonaws.com
```
Then just proceed with the installation of Java, Docker and Docker-compose
```sh
:~$ sudo apt-get update && sudo apt-get install default-jdk
:~$ sudo apt-get install docker.io
:~$ sudo apt-get install docker-compose
```
#### 2. Clone this repository

#### 3. Build a jar file with Gradle
This command will build file-processor-service application
```sh
voting-service:~$ ./gradlew clean build
```
This command will build directory-monitor-service application
```sh
voting-totalizer-producer-service:~$ ./gradlew clean build
```
#### 4. Increase the virtual memory if needed
Elasticsearch requires that the value of the max_map_count variable be at least 262144.
```sh
:~$ sudo sysctl -w vm.max_map_count=262144
```
#### 5. Run docker-compose
This command will raise the containers needed to run the entire application.
```sh
:~$ sudo docker-compose up
```
## Access applications
It will be possible to access the applications, for example:
#### To access the voting-service Swagger
```sh
<ec2_ip>:8080/swagger-ui.html
```
#### To access the Kibana UI
```sh
<ec2_ip>:5601
```
#### To access the RabbitMQ UI
```sh
<ec2_ip>:15672
```
## Improvements in this project
Some improvements this project could have
*   Adds a centralized configuration for not to keep repeated attributes in different applications;
*   Adds a Vault for save sensitive data, like MySQL user/pass, etc;
*   Adds a Circuit Breaker since external services are accessed;
*   Build a image for the EC2 with Packer with all we need: Java, Docker, etc.
