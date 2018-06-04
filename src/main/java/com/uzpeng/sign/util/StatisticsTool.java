package com.uzpeng.sign.util;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.domain.SignRecordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author serverliu on 2018/4/10.
 */
public class StatisticsTool {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsTool.class);
    private static final double MIN_VALUE=0.01;

    public static void pickAbnormalPoint(CopyOnWriteArrayList<SignRecordDO> records){
        List<Double> longitude = new ArrayList<>();
        List<Double> latitude = new ArrayList<>();

        for (SignRecordDO c :
                records) {
            longitude.add(c.getLongitude());
            latitude.add(c.getLatitude());
        }

        double longitudeMean = getMean(longitude);
        double longitudeSD = getSD(longitude, longitudeMean);
        double latitudeMean = getMean(latitude);
        double latitudeSD = getSD(latitude, latitudeMean);

        logger.debug("longitudeMean: "+longitudeMean+",longitudeSD: "+longitudeSD+" latitudeMean:"+latitudeMean+
                ",latitudeSD"+latitudeSD);

        int size = records.size();
        ArrayList<Double> probabilities = new ArrayList<>();
        for (SignRecordDO record : records) {
            double x = getNormalDistributionProbabilityDestiny(record.getLongitude(), longitudeMean, longitudeSD);
            double y = getNormalDistributionProbabilityDestiny(record.getLatitude(), latitudeMean, latitudeSD);

            logger.debug("x: "+x+",y: "+y);

            probabilities.add(x * y);
        }
        for (int i = 0; i < records.size(); i++) {
            SignRecordDO record = records.get(i);
            double probability = probabilities.get(i);
            int result = probability > MIN_VALUE ? StatusConfig.RECORD_SUCCESS : StatusConfig.RECORD_FAILED;

            logger.debug("probability: "+probability+",signId is "+record.getId()+" result:"+result);

            record.setState(result);
        }
    }

    private static double getMean(List<Double> data){
        CopyOnWriteArrayList<Double> safeData = new CopyOnWriteArrayList<>();
        safeData.addAll(data);

        double result = 0;
        for (Double d : safeData) {
            result += d;
        }

        return result / data.size();
    }

    private static double getSD(List<Double> data){
       return getSD(data, getMean(data));
    }

    private static double getSD(List<Double> data, double mean){
        CopyOnWriteArrayList<Double> safeData = new CopyOnWriteArrayList<>();
        safeData.addAll(data);

        int size = data.size();
        double result = 0;

        for (Double d : safeData) {
            result += Math.pow((d - mean), 2);
        }

        return Math.sqrt(result / size);
    }

    private static double getNormalDistributionProbabilityDestiny(double data, double mean, double sd){
        double base = 1 / (Math.sqrt(2 * Math.PI * sd));
        double exponent = - ((Math.pow((data - mean), 2)) / (2 * Math.pow(sd, 2)));

        return  Math.pow(base, exponent);
    }

}
