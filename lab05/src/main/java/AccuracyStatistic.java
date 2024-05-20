
import ex.api.DataSet;
import ex.api.AnalysisException;

public class AccuracyStatistic implements Accuracy.AccuracyStatistic {
    private DataSet currentDataSet;

    @Override
    public void setOptions(String[] strings) throws AnalysisException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void submit(DataSet dataSet) {
        this.currentDataSet = dataSet;
    }

    @Override
    public DataSet retrieve(boolean clearAfterRetrieval) throws RuntimeException {
        if (currentDataSet == null) {
            throw new RuntimeException("No data set available");
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

        int correctPredictions = 0;
        int total = 0;
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix[i].length; j++) {
                if (i == j) {
                    correctPredictions += confusionMatrix[i][j];
                }
                total += confusionMatrix[i][j];
            }
        }

        return total > 0 ? (double) correctPredictions / total : 0;
    }
}
