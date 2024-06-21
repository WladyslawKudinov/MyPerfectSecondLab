package calculatestatistics;

import org.apache.commons.math3.stat.StatUtils;
import read.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class Maximum implements statystics {
    private List<Double> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            double maximum = StatUtils.max(column.stream().mapToDouble(Double::doubleValue).toArray());
            result.add(maximum);
        }
    }

    @Override
    public List<Double> getResult() {
        return result;
    }
}
