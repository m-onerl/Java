import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Convolution convolution = new Convolution();

        try {
            System.out.println("Podaj rozmiar macierzy wejściowej:");
            System.out.print("Liczba wierszy: ");
            int matrixRows = scanner.nextInt();
            System.out.print("Liczba kolumn: ");
            int matrixCols = scanner.nextInt();

            System.out.println("Podaj rozmiar jądra splotu:");
            System.out.print("Liczba wierszy: ");
            int kernelRows = scanner.nextInt();
            System.out.print("Liczba kolumn: ");
            int kernelCols = scanner.nextInt();

            double[][] kernel = generateRandomMatrix(kernelRows, kernelCols);
            double[][] matrix = generateRandomMatrix(matrixRows, matrixCols);

            long startTime = System.nanoTime();
            double[][] result1 = convolution.nativeConvolution(kernel, matrix);
            long endTime = System.nanoTime();
            long durationNative = endTime - startTime;

            startTime = System.nanoTime();
            double[][] result2 = convolution.normalConvolution(kernel, matrix);
            endTime = System.nanoTime();
            long durationNormal = endTime - startTime;

            System.out.println("Wynik splotu (metoda natywna):");
            printMatrix(result1);
            System.out.println("Wynik splotu (metoda normalna):");
            printMatrix(result2);

            System.out.println("\nCzas wykonania metody natywnej: " + durationNative + " ns");
            System.out.println("Czas wykonania metody normalnej: " + durationNormal + " ns");

        } catch (Exception e) {
            System.out.println("Wprowadzono nieprawidłowe dane: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


    public static double[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        return matrix;
    }


    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

}
