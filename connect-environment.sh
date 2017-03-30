# Variables

SERVICE=docker
STATUS=$(service $SERVICE status)

# Check Docker service and start Proteus Environment

if (( $(ps -ef | grep -v grep | grep $SERVICE | wc -l) > 0 ))
then

        echo " _  __ __  ___ _  __ __     ___ __  __  _ __  _ ___ ________  "
        echo "| |/ //  \| __| |/ //  \   / _//__\|  \| |  \| | __/ _/_   _| "
        echo "|   <| /\ | _||   <| /\ | | \_| \/ | | ' | | ' | _| \__ | |   "
        echo "|_|\_\_||_|_| |_|\_\_||_|  \__/\__/|_|\__|_|\__|___\__/ |_|   "
        echo "                                                              "
        echo ""
	echo "Kafka Connect"
	echo ""
        ./scripts/core.sh
else
	echo "Docker is Stopped."
fi 

