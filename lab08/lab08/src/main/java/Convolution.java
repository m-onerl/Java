public class Convolution {
    static {
        System.load("C:\\Users\\moner\\Documents\\Projekty\\Java\\sebwol_javatz_2024\\lab08\\lab08\\native\\convolution.dll");
    }

    public native double[][] nativeConvolution(double[][] kernel, double[][] matrix);


    public double[][] normalConvolution(double[][] kernel, double[][] matrix) {
        int kernelRows = kernel.length;
        int kernelCols = kernel[0].length;
        int matrixRows = matrix.length;
        int matrixCols = matrix[0].length;


        if (kernelRows > matrixRows || kernelCols > matrixCols) {
            throw new IllegalArgumentException("Kernel dimensions must be smaller than the matrix.");
        }


        int paddingRows = kernelRows / 2;
        int paddingCols = kernelCols / 2;
        double[][] paddedMatrix = padMatrix(matrix, paddingRows, paddingCols);
        int resultRows = matrixRows;
        int resultCols = matrixCols;
        double[][] resultMatrix = new double[resultRows][resultCols];


        for (int i = 0; i < resultRows; i++) {
            for (int j = 0; j < resultCols; j++) {
                double sum = 0;
                for (int x = 0; x < kernelRows; x++) {
                    for (int y = 0; y < kernelCols; y++) {
                        int rowIndex = i + x;
                        int colIndex = j + y;
                        sum += kernel[x][y] * paddedMatrix[rowIndex][colIndex];
                    }
                }
                resultMatrix[i][j] = sum;
            }
        }

        return resultMatrix;
    }

    private double[][] padMatrix(double[][] matrix, int paddingRows, int paddingCols) {
        int paddedRows = matrix.length + 2 * paddingRows;
        int paddedCols = matrix[0].length + 2 * paddingCols;
        double[][] paddedMatrix = new double[paddedRows][paddedCols];

        for (int i = 0; i < paddedRows; i++) {
            for (int j = 0; j < paddedCols; j++) {
                int originalRow = i - paddingRows;
                int originalCol = j - paddingCols;
                if (originalRow >= 0 && originalRow < matrix.length &&
                        originalCol >= 0 && originalCol < matrix[0].length) {
                    paddedMatrix[i][j] = matrix[originalRow][originalCol];
                } else {
                    paddedMatrix[i][j] = 0;
                }
            }
        }
        return paddedMatrix;
    }
}
