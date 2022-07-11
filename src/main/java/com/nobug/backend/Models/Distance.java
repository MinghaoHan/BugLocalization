package com.nobug.backend.Models;

import com.nobug.backend.query.query;
import com.nobug.backend.query.wordMap;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class Distance {

    Vector<Double> v1;
    Vector<Double> v2;
    public Distance(Vector<Double> a,Vector<Double> b){
        v1=a;
        v2=b;
    }
    DecimalFormat df = new DecimalFormat("#00.0000");

    public double cosDistance() {
        Double v1_sum=0.0;
        Double v2_sum=0.0;
        Double v1Dotv2=0.0;

        for(int i=0;i<v1.size();i++){
            v1Dotv2 += v1.get(i)*v2.get(i);
            v1_sum += v1.get(i)*v1.get(i);
            v2_sum += v2.get(i)*v2.get(i);
        }
        return Double.valueOf(df.format(v1Dotv2/Math.sqrt(v1_sum*v2_sum)));

    }

    public double euclideanDistance() {
        double ed = 0;
        for (int i = 0; i < v1.size(); ++i) {
            double x = v1.get(i) - v2.get(i);
            ed += x*x;
        }
        return Math.sqrt(ed);
    }

    public double manhattanDistance() {
        double md = 0;
        for (int i = 0; i < v1.size(); ++i) {
            double x = v1.get(i) - v2.get(i);
            md += Math.abs(x);
        }
        return md;
    }

    public double vsmDistance(){
        Double v1_sum=0.0;
        Double v2_sum=0.0;
        Double v1Dotv2=0.0;

        for(int i=0;i<v1.size();i++){
            v1Dotv2 += v1.get(i)*v2.get(i);
            v1_sum += v1.get(i)*v1.get(i);
            v2_sum += v2.get(i)*v2.get(i);
        }

        return Double.valueOf(df.format(v1Dotv2/Math.sqrt(v1_sum*v2_sum)));
    }

}
