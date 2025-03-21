import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Integer> getLs(int size){
        ArrayList<Integer> ls = new ArrayList<>();

        int start = 2;
        ls.add(start);
        while (start + 1 < size){
            start*=2;
            ls.add(start);
        }
        return ls;
    }
    public static void main(String[] args) {
        int[] sizes = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2040, 4096};

        String outputFile = "multiplication_results.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Size,l,StandardTime,StandardOps,BinetTime,BinetOps,StrassenTime, StrassenOps, HybridTime,HybridOps");
            writer.newLine();

            for (int size : sizes) {
                ArrayList<Integer> ls = getLs(size);
                System.out.println(size);
                for (int l : ls) {
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
                    System.out.println("Standard: " + standardOps + " " + standardTime + " ");

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

                    writer.write(size + "," + l + "," + standardTime + "," + standardOps + "," +
                            binetTime + "," + binetOps + "," + strassenTime + "," + strassenOps + "," + hybridTime + "," + hybridOps);
                    writer.newLine();
                }


            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
}