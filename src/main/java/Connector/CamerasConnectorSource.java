package Connector;

import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Task.CamerasSourceTask;


/**
 * Created by pablo.mesa on 23/03/17.
 */
public class CamerasConnectorSource extends SourceConnector {

    public static final String TOPIC_CONFIG = "topic";
    public static final String FILE_CONFIG = "file";
    public static final String DEBUG = "debug";


    // Variables
    private String topic;
    private String filename;

    // Logs
    private static final Logger log = LoggerFactory.getLogger(CamerasConnectorSource.class);

    // Constructor

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String,String> prop) {

        // Las configuraciones que se pasan por línea de comandos tendrían que venir a este
        // método


        topic = prop.get("TOPIC");
        filename = prop.get("FILENAME");

        if ( topic == null ) try {
            throw new java.net.ConnectException("Camera Connect must include a topic");
        } catch (java.net.ConnectException e) {
            e.printStackTrace();
        }

        if ( filename == null ) try {
            throw new java.net.ConnectException("Camera Connect must include a filename");
        } catch (java.net.ConnectException e){
            e.printStackTrace();
        }

    }

    public Class<? extends Task> taskClass() {

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
