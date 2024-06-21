package calculatestatistics;

import org.apache.commons.math3.stat.StatUtils;
import read.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class CoefficientVariation implements statystics {
    private List<Double> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        List<Double> sd = new ArrayList<>();
        for (List<Double> column : columns) {
            double ssdd = StatUtils.variance((column.stream().mapToDouble(Double::doubleValue).toArray()));
            sd.add(Math.sqrt(ssdd));
        }
        List<Double> mean = new ArrayList<>();
        for (List<Double> column : columns) {
            double mn = StatUtils.mean(column.stream().mapToDouble(Double::doubleValue).toArray());
            mean.add(mn);
        }
        for (int ind = 0; ind < columns.size(); ind++) {
            double cv = sd.get(ind) / mean.get(ind);
            result.add(cv);
        }
    }

    @Override
    public List<Double> getResult() {
        return result;
    }
}
