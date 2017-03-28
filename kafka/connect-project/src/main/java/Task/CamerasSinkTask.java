package Task;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.util.Collection;
import java.util.Map;

/**
 * Created by pablo.mesa on 23/03/17.
 */
public class CamerasSinkTask extends SinkTask{

    public String version() {
        return AppInfoParser.getVersion();
    }

    public void start(Map<String, String> map) {

    }

    public void put(Collection<SinkRecord> collection) {

    }

    public void flush(Map<TopicPartition, OffsetAndMetadata> map) {

    }

    public void stop() {

    }
}
