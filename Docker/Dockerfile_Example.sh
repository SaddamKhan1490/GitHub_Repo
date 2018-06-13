# Dockerfile Goal: Run Java Jar after downloading JRE from DockerHub
vi Dockerfile
FROM cpe.docker.target.com/kubernetes/alpine-jre:latest
MAINTAINER GDM_Team
ADD build/libs/*.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-XX:+UseG1GC -Xms256m -Xmx2560m -XX:-OmitStackTraceInFastThrow -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.rmi.port=1099 -Djava.rmi.server.hostname=127.0.0.1"
ENTRYPOINT [ "sh", "-c", "/usr/bin/java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]

docker build -t "java_app:v0" .                              # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                                # View the newly created image
docker run -it --name test java_app:v0 /bin/bash             # Spin docker container using our newly created docker image i.e. 'java_app:v0' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download Ubuntu Image from DockerHub & Insatll telnet
vi Dockerfile
FROM ubuntu:xenial
MAINTAINER xyz <xyz@gmail.com>
RUN apt-get update
RUN apt-get install openssh-server telnet -y

docker build -t "ubuntu_telnet:v0" .                        # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                               # View the newly created image
docker run -it --name test ubuntu_telnet:v0 /bin/bash       # Spin docker container using our newly created docker image i.e. 'ubuntu_telnet:v0' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download and install CentOs Image from DockerHub & Insatll telnet
vi Dockerfile
FROM centos:6.9
MAINTAINER xyz <xyz@gmail.com>
RUN yum update -y
RUN yum install openssh-server telnet -y

docker build -t "centos_telnet:v0" .                        # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                               # View the newly created image
docker run -it --name test centos_telnet:v0 /bin/bash       # Spin docker container using our newly created docker image i.e. 'centos_telnet:v0' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download CentOs Image from DockerHub, Install and Start httpd
vi Dockerfile
FROM centos:6.9
MAINTAINER xyz
RUN yum install httpd -y
RUN service httpd restart
RUN chkconfig httpd on

docker build -t "httpd:v0" .                               # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                              # View the newly created image
docker run -it --name test httpd:v0 /bin/bash              # Spin docker container using our newly created docker image i.e. 'httpd:v0' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Build Docker Image from Dockerfile, Install and Start httpd, Run apache image: version1 and start container on port 8080 via port_forwarding, link application on localhost:8080 using elinks, finally execute statement to restart and bringup httpd service
vi Dockerfile
docker build -t "http:v2" .
RUN yum install httpd -y
RUN service httpd restart
RUN chkconfig httpd on
docker run --name test1 -itd -p 8081:80 apache:1 /bin/bash
elinks http://localhost
elinks http://localhost:8081
docker exec test1 service httpd restart

docker build -t "httpd:v1" .                              # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                             # View the newly created image
docker run -it --name test httpd:v1 /bin/bash             # Spin docker container using our newly created docker image i.e. 'httpd:v1' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download centos:latest image from DockerHub, add new user xyz, set JAVA_HOME inside .bin/bash and finally set environment as JAVA_BIN
vi Dockerfile
FROM centos:latest
MAINTAINER xyz
RUN useradd -s /bin/bash user
USER user
RUN echo "export JAVA_HOME=/usr/bin/java">>~/.bashrc
ENV JAVA_BIN /usr/bin/test

docker build -t "run-user:v0" .                           # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                             # View the newly created image
docker run -it --name test run-user:v1 /bin/bash          # Spin docker container using our newly created docker image i.e. 'run-user:v0' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download centos:6.9 image from DockerHub, update the downloaded image and add new user xyz, Create a file with "Hello all"
vi Dockerfile
FROM centos:6.9
MAINTAINER xyz@gmail.com
RUN yum update -y && useradd username
USER username
RUN echo "Hello all" >> /etc/passwd

docker build -t "run-user:v1" .                           # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                             # View the newly created image
docker run -it --name test run-user:v1 /bin/bash          # Spin docker container using our newly created docker image i.e. 'run-user:v1' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download centos:6.9 image from DockerHub, add maintainer as shan.aix@gmail.com, update the downloaded image, make file with content "Hello all" under /etc/passwd
vi Dockerfile
FROM centos:6.9
MAINTAINER xyz@gmail.com
RUN yum update -y && useradd username
RUN echo "Hello all" >> /etc/passwd

docker build -t "run-user:v2" .                          # Move to folder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                            # View the newly created image
docker run -it --name test run-user:v2 /bin/bash         # Spin docker container using our newly created docker image i.e. 'run-user:v2' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------

# Dockerfile Goal : Download centos:6.9 image from DockerHub, add maintainer as xyz@gmail.com, update the downloaded image, make directory, set JAVA_HOME inside .bin/bash 
vi Dockerfile
FROM centos:6.9
MAINTAINER xyz@gmail.com
RUN yum update -y && mkdir /mydir && echo "export JAVA_HOME="/mydir" " >> ~/.bashrc

docker build -t "env:v1" .                              # Move to f0lder where Dockerfile is & Build Docker Image using Dockerfile in order to spin container. Here "." represents home directory of Dockerfile
docker images                                           # View the newly created image
docker run -it --name envtest1 env:v1 /bin/bash         # Spin docker container using our newly created docker image i.e. 'env:v1' | Here same command we can execute as many number of times, same as number of docker containers we want to spin

--------------------------------------------------------------------------------
