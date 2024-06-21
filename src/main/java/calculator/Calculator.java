package calculator;

import calculatestatistics.*;
import read.ExcelReader;
import write.ExcelWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {
    private Map<String, statystics> statObjects = new HashMap<>();
    private Map<String, List<?>> allResults = new HashMap<>();
    private List<List<Double>> columns;
    private  List<String>labels;

    public Calculator() {
        statObjects.put("Стандартное отклонение", new StandardDeviation());
        statObjects.put("Доверительный интервал для мат ожидания", new ConfidenceInterval());
        statObjects.put("Количество элементов", new QuantityElem());
        statObjects.put("Среднее значение", new Mean());
        statObjects.put("Максимальное значение", new Maximum());
        statObjects.put("Минимальное значение", new Minimum());
        statObjects.put("Среднее геометрическое", new GeometricMean());
        statObjects.put("Коэффициент вариации", new CoefficientVariation());
        statObjects.put("Дисперсия", new Variance());
        statObjects.put("Размах", new Range());
        statObjects.put("Ковариация", new Covariation());
    }

    public void read(String file, String name) throws IOException {
        ExcelReader excelReader = new ExcelReader();
        Map<String, List<Double>> data  = excelReader.readFromExcel(file, name);
        labels = new ArrayList<>(data.keySet());
        columns = new ArrayList<>(data.values());

        }


    public void calculateAll() {
        for (Map.Entry<String, statystics> entry : statObjects.entrySet()) {
            String name = entry.getKey();
            statystics calc = entry.getValue();
            try {
                calc.calculate(columns);
                allResults.put(name, calc.getResult());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public Map<String, List<?>> getAllResults() {
        return allResults;
    }

    public void write() throws IOException{
            ExcelWriter.write(allResults, labels, "OutputStatistics");



}}
