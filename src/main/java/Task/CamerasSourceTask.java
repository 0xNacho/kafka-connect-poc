package Task;

import Connector.CamerasConnectorSource;
import com.sun.org.apache.xalan.internal.Version;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.connect.connector.Task;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

/**
 * Created by pablo.mesa on 23/03/17.
 *
 */
public class CamerasSourceTask implements Task {

    private String topic;
    private String filename;
    private InputStream stream;

    public String version() {
        return Version.getVersion();
    }

    public void start(Map<String, String> map) {

        System.out.println("Task - Start");

        filename = map.get(CamerasConnectorSource.FILE_CONFIG);
        topic = map.get(CamerasConnectorSource.TOPIC_CONFIG);

        filename = "hdfs://192.168.4.245:8020/proteus/final/sorted/000000_0";

        Configuration conf = new Configuration();

        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());

        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create("hdfs://192.168.4.245:8020"), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fs.open(new Path(filename))));

            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    "192.168.4.246:6667,192.168.4.247:6667,192.168.4.248:6667");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            properties.put(ProducerConfig.ACKS_CONFIG, "all");
            properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 100);

            Producer<String, String> producer = new KafkaProducer<String, String>(properties);

            String line = br.readLine();

            while ( line !=  null){
                producer.send(new ProducerRecord<String, String>("proteus", line));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Task Filename: " + filename + " , Task topic: " + topic);

    }

    public void stop() {

        System.out.println("Task Stop");

    }
}
