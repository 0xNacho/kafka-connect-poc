package Connector;

import com.sun.org.apache.xalan.internal.Version;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Connector;
import org.apache.kafka.connect.connector.Task;

import Task.CamerasSourceTask;
import Task.CamerasSinkTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * Created by pablo.mesa on 23/03/17.
 */
public class CamerasConnectorSource extends Connector {

    public static final String TOPIC_CONFIG = "topic";
    public static final String FILE_CONFIG = "file";


    // Variables
    private String topic;
    private String filename;

    // Logs
    private static final Logger log = LoggerFactory.getLogger(CamerasConnectorSource.class);

    public String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<String,String> prop) {

        // De este método se toman las configuraciones

        topic = prop.get("TOPIC");
        filename = prop.get("FILENAME");


    }

    public Class<? extends Task> taskClass() {
        return CamerasSourceTask.class;
    }

    public List<Map<String, String>> taskConfigs(int maxTasks) {

        ArrayList<Map<String,String>> configs = new ArrayList<Map<String, String>>();

        Map<String,String> config = new HashMap<String, String>();

        // Este es el "core" del conector

        if ( filename != null) config.put(FILE_CONFIG, filename);
        config.put(TOPIC_CONFIG, topic);
        configs.add(config);

        return configs;
    }

    public void stop() {

        // Aquí se harían las tareas de monitorización en backgroud

    }

    public ConfigDef config() {
        return null;
    }
}
