
import ex.api.DataSet;

public class KappaStatistic implements Kappa.KappaStatistic {
    private DataSet currentDataSet;

    @Override
    public String getName() {
        return "Kappa Statistic";
    }

    @Override
    public void setOptions(String[] options) {
    }

    @Override
    public void submit(DataSet dataSet) {
        this.currentDataSet = dataSet;
    }

    @Override
    public DataSet retrieve(boolean clearAfterRetrieval) {
        if (currentDataSet == null) {
            throw new IllegalStateException("No data set available");
        }
        DataSet dataToReturn = currentDataSet;
        if (clearAfterRetrieval) {
            currentDataSet = null;
        }
        return dataToReturn;
    }

    @Override
    public double analyze(DataSet data) throws RuntimeException {
        Object rawData = data.getData();
        if (!(rawData instanceof String[][])) {
            throw new RuntimeException("Data must be of type String[][]");
        }

        String[][] stringMatrix = (String[][]) rawData;
        int[][] confusionMatrix = new int[stringMatrix.length][stringMatrix[0].length];

        try {
            for (int i = 0; i < stringMatrix.length; i++) {
                for (int j = 0; j < stringMatrix[i].length; j++) {
                    confusionMatrix[i][j] = Integer.parseInt(stringMatrix[i][j]);
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Data conversion error: " + e.getMessage(), e);
        }

        int sumObserved = 0, sumExpected = 0, total = 0;

        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix[i].length; j++) {
                total += confusionMatrix[i][j];
                if (i == j) {
                    sumObserved += confusionMatrix[i][j];
                }
            }
        }

        for (int i = 0; i < confusionMatrix.length; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < confusionMatrix.length; j++) {
                rowSum += confusionMatrix[i][j];
                colSum += confusionMatrix[j][i];
            }
            sumExpected += (rowSum * colSum);
        }

        double expected = sumExpected / (double) total;
        return total == expected ? 0.0 : (sumObserved - expected) / (total - expected);
    }
}