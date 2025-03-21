class StrassenBinetMultiplication {
    public static long operationCount = 0;
    private static int threshold;

    public static double[][] multiply(double[][] A, double[][] B, int l) {
        operationCount = 0;
        threshold = l;
        return hybrid(A, B);
    }

    private static double[][] hybrid(double[][] A, double[][] B) {
        int n = A.length;
        if (n <= threshold) {
            double[][] result =  BinetMultiplication.multiply(A, B);
            operationCount+=BinetMultiplication.operationCount;
            return result;
        }

        int newSize = n / 2;
        double[][] A11 = new double[newSize][newSize];
        double[][] A12 = new double[newSize][newSize];
        double[][] A21 = new double[newSize][newSize];
        double[][] A22 = new double[newSize][newSize];
        double[][] B11 = new double[newSize][newSize];
        double[][] B12 = new double[newSize][newSize];
        double[][] B21 = new double[newSize][newSize];
        double[][] B22 = new double[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + newSize];
                A21[i][j] = A[i + newSize][j];
                A22[i][j] = A[i + newSize][j + newSize];
                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + newSize];
                B21[i][j] = B[i + newSize][j];
                B22[i][j] = B[i + newSize][j + newSize];
            }
        }

        double[][] P1 = hybrid(MatrixUtils.add(A11, A22), MatrixUtils.add(B11, B22));
        double[][] P2 = hybrid(MatrixUtils.add(A21, A22), B11);
        double[][] P3 = hybrid(A11, MatrixUtils.subtract(B12, B22));
        double[][] P4 = hybrid(A22, MatrixUtils.subtract(B21, B11));
        double[][] P5 = hybrid(MatrixUtils.add(A11, A12), B22);
        double[][] P6 = hybrid(MatrixUtils.subtract(A21, A11), MatrixUtils.add(B11, B12));
        double[][] P7 = hybrid(MatrixUtils.subtract(A12, A22), MatrixUtils.add(B21, B22));

        double[][] C11 = MatrixUtils.add(MatrixUtils.subtract(MatrixUtils.add(P1, P4), P5), P7);
        double[][] C12 = MatrixUtils.add(P3, P5);
        double[][] C21 = MatrixUtils.add(P2, P4);
        double[][] C22 = MatrixUtils.add(MatrixUtils.subtract(MatrixUtils.add(P1, P3), P2), P6);

        operationCount+= 18* P1.length * P1.length;

        double[][] result = new double[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = C11[i][j];
                result[i][j + newSize] = C12[i][j];
                result[i + newSize][j] = C21[i][j];
                result[i + newSize][j + newSize] = C22[i][j];
            }
        }
        return result;
    }
}
