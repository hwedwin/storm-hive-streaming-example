/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storm_hive_streaming_example;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.FailedException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import storm_hive_streaming_example.model.Stock;
import storm_hive_streaming_example.serializer.StockAvroSerializer;

/**
 *
 * @author hkropp
 */
public class AvroStockDataBolt extends BaseBasicBolt {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AvroStockDataBolt.class);

    @Override
    public void declareOutputFields(OutputFieldsDeclarer ofDeclarer) {
        ofDeclarer.declare(new Fields(FieldNames.STOCK_FIELD));
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector outputCollector) {
        try {
            Stock stock = (Stock) tuple.getValueByField(FieldNames.STOCK_FIELD);
            outputCollector.emit(new Values(stock));
        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            throw new FailedException(ex.toString());
        }
    }
}
