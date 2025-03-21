import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int[] sizes = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2040, 4096};

        String outputFile = "multiplication_benchmark.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Size,StandardTime,StandardOps,BinetTime,BinetOps,HybridTime,HybridOps");
            writer.newLine();

            for (int size : sizes) {
                System.out.println(size);
                double[][] A = MatrixUtils.randomMatrix(size, 0, 10);
                double[][] B = MatrixUtils.randomMatrix(size, 0, 10);

                long startTime, endTime;
                long standardOps, binetOps, strassenOps, hybridOps;

                startTime = System.nanoTime();
                StandardMultiplication.operationCount = 0;
                double[][] standardResult = StandardMultiplication.multiply(A, B);
                endTime = System.nanoTime();
                standardOps = StandardMultiplication.operationCount;
                long standardTime = (endTime - startTime) / 1000000;
                System.out.println("Standard: " + + standardOps + " " + standardTime + " ");

                startTime = System.nanoTime();
                BinetMultiplication.operationCount = 0;
                double[][] binetResult = BinetMultiplication.multiply(A, B);
                endTime = System.nanoTime();
                binetOps = BinetMultiplication.operationCount;
                long binetTime = (endTime - startTime) / 1000000;
                boolean binetCorrect = MatrixUtils.areEqual(standardResult, binetResult, 1e-6);
                System.out.println("Binet: " + binetOps + " " + binetTime + " " + binetCorrect);

                startTime = System.nanoTime();
                StrassenMultiplication.operationCount = 0;
                double[][] strassenResult = StrassenMultiplication.multiply(A, B);
                endTime = System.nanoTime();
                strassenOps = StrassenMultiplication.operationCount;
                long strassenTime = (endTime - startTime) / 1000000;
                boolean strassenCorrect = MatrixUtils.areEqual(standardResult, strassenResult, 1e-6);
                System.out.println("Strassen: " + strassenOps + " " + strassenTime + " " + strassenCorrect);

                startTime = System.nanoTime();
                StrassenBinetMultiplication.operationCount = 0;
                double[][] hybridResult = StrassenBinetMultiplication.multiply(A, B, 4);
                endTime = System.nanoTime();
                hybridOps = StrassenBinetMultiplication.operationCount;
                long hybridTime = (endTime - startTime) / 1000000;
                boolean hybridCorrect = MatrixUtils.areEqual(standardResult, hybridResult, 1e-6);
                System.out.println("Hybrid: " + hybridOps + " " + hybridTime + " " + hybridCorrect);

                writer.write(size + "," + standardTime + "," + standardOps + "," +
                        binetTime + "," + binetOps + "," + strassenTime + "," + strassenOps + "," + hybridTime + "," + hybridOps);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
}