import org.apache.kafka.connect.sink.SinkConnector;

/**
 * Created by pablo.mesa on 28/03/17.
 */

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;


import java.util.List;
import java.util.Map;



public class HDFSConnectorSink extends SinkConnector{


    public String version() {
        return AppInfoParser.getVersion();
    }

    public void start(Map<String, String> map) {

    }

    public Class<? extends Task> taskClass() {
        return null;
    }

    public List<Map<String, String>> taskConfigs(int i) {
        return null;
    }

    public void stop() {

    }

    public ConfigDef config() {
        return null;
    }
}
