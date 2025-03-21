import java.util.Random;

class MatrixUtils {
    public static double[][] randomMatrix(int size, double minVal, double maxVal) {
        Random rand = new Random();
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = minVal + (maxVal - minVal) * rand.nextDouble();
            }
        }
        return matrix;
    }

    public static double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    public static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    public static boolean areEqual(double[][] A, double[][] B, double epsilon) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.abs(A[i][j] - B[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }
}
