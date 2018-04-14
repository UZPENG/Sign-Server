package com.uzpeng.sign.util;

import com.uzpeng.sign.support.Coordinate;
import com.uzpeng.sign.support.Probability;

import java.util.ArrayList;

/**
 * @author serverliu on 2018/4/10.
 */
public class StatisticsTool {

    public static ArrayList<Probability>  pickAbnormalPoint(ArrayList<Coordinate> coordinates){
        ArrayList<Double> longitude = new ArrayList<>();
        ArrayList<Double> latitude = new ArrayList<>();

        for (Coordinate c :
                coordinates) {
            longitude.add(c.getLongitude());
            latitude.add(c.getLatitude());
        }

        double longitudeMean = getMean(longitude);
        double longitudeSD = getSD(longitude, longitudeMean);
        double latitudeMean = getMean(latitude);
        double latitudeSD = getSD(latitude, latitudeMean);

        int size = coordinates.size();
        ArrayList<Probability> probabilities = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            double x = getNormalDistributionProbabilityDestiny(coordinate.getLongitude(), longitudeMean, longitudeSD);
            double y = getNormalDistributionProbabilityDestiny(coordinate.getLatitude(), latitudeMean, latitudeSD);

            probabilities.add(new Probability(x, y));
        }
        return probabilities;
    }

    private static double getMean(ArrayList<Double> data){
        double result = 0;
        for (Double d : data) {
            result += d;
        }

        return result / data.size();
    }

    private static double getSD(ArrayList<Double> data){
       return getSD(data, getMean(data));
    }

    private static double getSD(ArrayList<Double> data, double mean){
        int size = data.size();
        double result = 0;

        for (Double d : data) {
            result += Math.pow((d - mean), 2);
        }

        return Math.sqrt(result / size);
    }

    private static double getNormalDistributionProbabilityDestiny(double data, double mean, double sd){
        double base = 1 / (Math.sqrt(2 * Math.PI) * sd);
        double exponent = - (Math.pow((data - mean), 2) / 2 * Math.pow(sd, 2));

        return  Math.pow(base, exponent);
    }

}
