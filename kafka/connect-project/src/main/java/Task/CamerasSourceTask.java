package Task;

import Connector.CamerasConnectorSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.common.utils.AppInfoParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pablo.mesa on 23/03/17.
 *
 */
public class CamerasSourceTask extends SourceTask {

    // Variables
    private String topic;
    private String filename;
    private InputStream stream;
    protected FileSystem fs;
    protected String HDFS = "hdfs://192.168.4.245:8020";
    protected String TABLE = "/proteus/final/sorted/000000_0";
    protected Configuration conf;
    private static Producer<String, String> producer;

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(CamerasSourceTask.class);


    public String version() { return AppInfoParser.getVersion();  }

    public void start(Map<String, String> map) {

        System.out.println("Task - Start");

        System.out.println("MAP: " + map);

        filename = map.get(CamerasConnectorSource.FILE_CONFIG);
        topic = map.get(CamerasConnectorSource.TOPIC_CONFIG);

        System.out.println("Filename: " + filename + " , Topic: " + topic);
}

    public List<SourceRecord> poll() throws InterruptedException {

        // SourceRecord List donde se van a almacenar los valores

        List<SourceRecord> lista = new ArrayList<SourceRecord>();




        System.out.println("Poll");
        logger.info("Starting Camera Source Task...");

        Configuration conf = new Configuration();

        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());


        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(HDFS), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Filesystem: " + fs);


        String timeStampInicio;
        String timeStampFinal;
        int loopIteration = 1;

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
        // Read line by line HDFS

        timeStampInicio = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        logger.info("Hora de inicio de la tarea: " + timeStampInicio);


        while (loopIteration < 10) {
            logger.info("Starting a new kafka iteration over the HDFS: ", (loopIteration++));

            BufferedReader br = null;
            try {
                br = new BufferedReader(
                        new InputStreamReader(fs.open(new Path(HDFS + "/proteus/split/proteus-awk/" + topic + ".csv"))));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            int contador = 0;

            while ( contador < 10){


                try {
                    System.out.println("Bloque TRY");
                    String line = br.readLine();
                    if (line != null) {
                        Map sourcePartition = Collections.singletonMap("filename", filename);
                        Map sourceOffset = Collections.singletonMap("position", line);
                        lista.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.STRING_SCHEMA, line));
                    } }catch (NullPointerException e) {
                    logger.error("Error in the Proteus Kafka producer", e);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            timeStampFinal = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                logger.info("Final de producción: " + timeStampFinal);
            }

        return null;
    }

    public void stop() {
        System.out.println("Task Stop");

    }
}
