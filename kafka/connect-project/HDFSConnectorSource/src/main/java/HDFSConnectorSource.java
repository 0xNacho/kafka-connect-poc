/**
 * Created by pablo.mesa on 28/03/17.
 */
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.connector.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pablo.mesa on 23/03/17.
 */
public class HDFSConnectorSource extends SourceConnector {

    public static final String TOPIC_CONFIG = "topics";
    public static final String FILE_CONFIG = "file";
    public static final String DEBUG = "debug";


    // Variables
    private String topic;
    private String filename;

    // Logs
    private static final Logger log = LoggerFactory.getLogger(HDFSConnectorSource.class);

    // Constructor

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String,String> prop) {

        // Las configuraciones que se pasan por línea de comandos tendrían que venir a este
        // método

        System.out.println("Properties: " + prop);


        topic = prop.get(TOPIC_CONFIG);
        filename = prop.get(FILE_CONFIG);

        System.out.println("Topic: " + topic + " ,Filename: " + filename);

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
        return HDFSTaskSource.class;
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
