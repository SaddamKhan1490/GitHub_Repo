#*
#* Created by Saddam on 03/14/2018.
#*

# Get details of our running docker engine viz- version, number of containers, number of images, etc...
docker info

# Get network details of docker engine
docker network -h OR docker network ls

# Bring up the docker engine
service docker restart OR dockered

# View version of running docker instance 
docker version

# Download existing docker image from docker hub
docker pull image_name:version

# List of all available images
docker images

# Remove or Delete an existing docker image from the docker registery
docker rmi -f image_id

# List all the running processes
docker ps -a

# Create customized docker image from running container
docker commit -m "my_initial_image" [i.e. message] container_1 [i.e. container name] golden_image:v1 [i.e. image name]

# Create docker container out of given image and switch to linux shell
docker run -it --name clone_of_container_1 golden_image:v1 /bin/bash

# Spin container out of existing image 
docker run -i -t image_name:version command i.e. /bin/bash

# Come out of the conatiner
ctrl + p + q

# Get row level details of our running docker conatiner
docker inspect container_id | more

# Stop the running docker container
docker stop container_id

# Start the running docker container
docker start container_id

# Rename i.e. assign new name to the running container
docker rename 4e5ea830c95e container_1

# Switch to already running docker container
docker attach container_id

# View detailed docker image history
docker image history

# View detailed docker conatiner logs
docker logs container_id

# View or inspect changes on container file system
docker diff

# Kill docker running or stopped docker container
docker kill container_id

# Spin docker container and execute command on running docker container
docker exec container_1 /bin/bash -c "df -hT;uname;whoami;ls -lart"

# Docker Port Forwarding
docker run --name test1 [i.e. container_name] -itd -p 8081:80 [i.e. port forwarding which means forwarding request from webserver of container to that of application running under the assigned container] http:v2 [i.e. image name] /bin/bash

# Create docker image out of dockerfile
vi Dockerfile [step 8-9]
docker build -t "golden_image:v2" . [i.e. Dockerfile path, here its same directory so '.']

# EXAMPLE : Trigger Infinite Loop inside conatiner from terminal
docker attach container_id                 # Connect to specified docker container
while true                                 # Execute commands on docker shell
> do
> echo "Hello World"
> sleep 2
> done
Hello World                                # Display output on console
Hello World
Hello World
docker stop container_id                   # Stop already docker container
docker start container_id                  # Re-start docker container
docker attach container_id                 # Connect to specified docker container
