class BinetMultiplication {
    public static int operationCount = 0;

    public static double[][] multiply(double[][] A, double[][] B) {
        operationCount = 0;
        return binet(A, B);
    }

    private static double[][] binet(double[][] A, double[][] B) {
        int n = A.length;
        if (n != B.length) {
            throw new IllegalArgumentException("ERROR: Wrong matrix dimensions!");
        }
        if (n == 2) {
            double[][] C = new double[2][2];
            C[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
            C[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
            C[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
            C[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];
            operationCount += 12;
            return C;
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

        double[][] C11 = MatrixUtils.add(binet(A11, B11), binet(A12, B21));
        double[][] C12 = MatrixUtils.add(binet(A11, B12), binet(A12, B22));
        double[][] C21 = MatrixUtils.add(binet(A21, B11), binet(A22, B21));
        double[][] C22 = MatrixUtils.add(binet(A21, B12), binet(A22, B22));

        operationCount += 4 * newSize * newSize;

        double[][] C = new double[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C[i][j] = C11[i][j];
                C[i][j + newSize] = C12[i][j];
                C[i + newSize][j] = C21[i][j];
                C[i + newSize][j + newSize] = C22[i][j];
            }
        }
        return C;
    }
}
