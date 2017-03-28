import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.common.utils.AppInfoParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class TestTaskSource extends SourceTask {

    // Variables
    private String topic;
    private String filename;

    // Path del Volumen del Conetenedor
    protected String HDFS = "/opt/dataset/";

    // Logger
    protected static final Logger logger = LoggerFactory.getLogger(TestTaskSource.class);


    public String version() { return AppInfoParser.getVersion();  }

    public void start(Map<String, String> map) {

        logger.info("Task Start");
        filename = HDFS + map.get(TestConnectorSource.FILE_CONFIG);
        topic = map.get(TestConnectorSource.TOPIC_CONFIG);
        logger.info("Filename: " + filename + " ,Topic: " + topic);

    }

    public List<SourceRecord> poll() throws InterruptedException {

        // SourceRecord List donde se van a almacenar los valores

        List<SourceRecord> lista = new ArrayList<SourceRecord>();
        logger.info("Poll");
        logger.info("Starting Camera Source Task...");


        // Leemos el origen de datos
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();

            while (line != null) {
                Map sourcePartition = Collections.singletonMap("filename", filename);
                Map sourceOffset = Collections.singletonMap("position", line);
                lista.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.STRING_SCHEMA, line));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void stop() {
        logger.info("Task stop");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
