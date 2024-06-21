package calculatestatistics;

import java.util.List;

public interface statystics {
    void calculate(List<List<Double>> columns);
    List<?> getResult();
}
