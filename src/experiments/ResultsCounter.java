package experiments;

import Jama.Matrix;

public class ResultsCounter {

    private final Integer x, y;
    private final Matrix results;

    private final Matrix ones;
    private final Matrix zeros;

    public ResultsCounter(Integer iterations, Integer tasks) {
        x = iterations;
        y = tasks;
        results = new Matrix(x, y);
        ones = new Matrix(1, results.getRowDimension(), 1.0);
        zeros = new Matrix(results.getColumnDimension(), 1);
    }

    public Matrix getAverages() {
        Matrix temp = new Matrix(1, y);

        for (int i = 0; i < y; i++) {
            zeros.set(i, 0, 1.0);
            temp.set(0, i, (ones.times(results.times(zeros))).det() / x);

            zeros.set(i, 0, 0.0);
        }
        return temp;
    }

    void addData(Integer iterationCycle, Integer taskNumber, Double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
