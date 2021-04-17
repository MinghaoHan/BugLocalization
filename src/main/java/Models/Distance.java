package Models;

import java.text.DecimalFormat;
import java.util.Vector;

public class Distance {

    DecimalFormat df = new DecimalFormat("#00.0000");

    public double cosDistance(Vector<Double> v1, Vector<Double> v2) {
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

    public double euclideanDistance(Vector<Double> v1, Vector<Double> v2) {
        double ed = 0;
        for (int i = 0; i < v1.size(); ++i) {
            double x = v1.get(i) - v2.get(i);
            ed += x*x;
        }
        return Math.sqrt(ed);
    }

    public double manhattanDistance(Vector<Double> v1, Vector<Double> v2) {
        double md = 0;
        for (int i = 0; i < v1.size(); ++i) {
            double x = v1.get(i) - v2.get(i);
            md += Math.abs(x);
        }
        return md;
    }

}
