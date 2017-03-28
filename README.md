# Kafka Connect - POC  CV-BD

Test CV - BD

Entorno dockerizado heredado de Proteus para una prueba de concepto entre líneas de Computer Vision y Big Data.

# Elementos clave dentro del directorio kafka

/connect-config/

Directorio con los ficheros de configuración que dentro del contenedor se mapena en el directorio /opt/kafka/connector-config/

/connect-project/

Código del proyecto del conector que dentro del contenedor se mapea en el directorio /opt/kafka/connectors-projects. Una vez se cree el fichero .jar este se mueve automáticamente a la carepta /opt/kafka/connectors/ 

/Dockerfile

Fichero de configuración del contenedor de Kafka

# Funcionamiento

Requisito: Definición correcta de la variable de entorno CLASSPATH

Standalone Mode




Distributed Mode


# Resumen de Versiones

JAVA_VERSION=1.8
KAFKA_VERSION=0.10.0.0
SCALA_VERSION=2.11
MAVEN_VERSION=3.3.9
