package calculatestatistics;

import read.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class QuantityElem implements statystics {
    private List<Integer> result = new ArrayList<>();

    @Override
    public void calculate(List<List<Double>> columns) {
        result = new ArrayList<>();
        for (List<Double> column : columns) {
            Integer quantity = column.size();
            result.add(quantity);
        }
    }

    @Override
    public List<Integer> getResult() {
        return result;
    }
}
