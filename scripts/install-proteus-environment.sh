# Treelogic Proteus Environment
# Tested on
# Kernel 3.13.0-32-generic - Ubuntu 14.04 - OK
# Kernel 3.13.0-92-generic - Ubuntu 12.02 - OK
# Kernel 4.2.0-27-generic - Ubuntu 14.04 - OK
# Kernel 4.2.0-27-generic - Ubuntu 16.04 - OK


### Proteus Network

NETWORK=computer-vision

if [ "$(sudo docker network inspect $NETWORK -d --format '{{ .Name }}')" == "$NETWORK" ]; then
        echo "Existe la red: $NETWORK"
else echo "Proteus Network don´t exist. Creating...  "$(sudo docker network create computer-vision)
fi

## Build Images
echo "Build"

sudo docker build -t base:cv-poc ./base/.
sudo docker build -t ssh:cv-poc ./ssh/.
sudo docker build -t zookeeper:cv-poc ./zookeeper/.
sudo docker build -t kafka:cv-poc ./kafka/.
sudo docker build -t landoop:cv-poc ./landoop-ui/.

echo "Docker Compose"

### Build

sudo docker-compose up -d zookeeper
sudo docker-compose up -d kafka
sudo docker-compose up -d landoop
