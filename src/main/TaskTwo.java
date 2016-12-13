package main;

import java.util.Arrays;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class TaskTwo {

    public static void main(String[] args) {
        System.out.println("Task 2");

        double eps = Math.pow(10.0, -6.0);
        int[] hArray = {5, 10, 10, 2};

        Double[][] arrayW0 = GenerateMatrix.listTwoDim(hArray[1], hArray[0]);
        Double[][] arrayW1 = GenerateMatrix.listTwoDim(hArray[2], hArray[1]);
        Double[][] arrayW2 = GenerateMatrix.listTwoDim(hArray[3], hArray[2]);

        Double[] x0 = GenerateMatrix.listOneDim(5);
        Double[] x0PlusEps = MatrixOperations.addOrSubtractScalar(x0, eps, 0);
        Double[] x0MinusEps = MatrixOperations.addOrSubtractScalar(x0, eps, 1);

        System.out.println("x0: " + Arrays.deepToString(x0));

        Double[][] functionPlus = MatrixOperations.calculateFunction(arrayW0, arrayW1, arrayW2, x0PlusEps);
        Double[][] functionMinus = MatrixOperations.calculateFunction(arrayW0, arrayW1, arrayW2, x0MinusEps);

        Double[][] z1 = MatrixOperations.matrixMultiply1D(arrayW0, x0);
        Double[][] sigma1 = MatrixOperations.sigmaEquation(z1);
        Double[][] x1 = MatrixOperations.matrixMultiply(arrayW1, sigma1);
        Double[][] sigma2 = MatrixOperations.sigmaEquation(x1);
        Double[][] function = MatrixOperations.matrixMultiply(arrayW2, sigma2);

        System.out.println("Value of function is " + Arrays.deepToString(function));

        Double[][] hadamardX1 = MatrixOperations.hadamardProduct(sigma1, MatrixOperations.scalarMinusMatrix(sigma1, 1.0));
        Double[][] hadamardX2 = MatrixOperations.hadamardProduct(sigma2, MatrixOperations.scalarMinusMatrix(sigma2, 1.0));

        Double[][] dx1dz1 = MatrixOperations.diagonalMatrix(hadamardX1);
        Double[][] dx2dz2 = MatrixOperations.diagonalMatrix(hadamardX2);

        Double[][] temp1 = MatrixOperations.matrixMultiply(dx1dz1, arrayW0);
        Double[][] temp2 = MatrixOperations.matrixMultiply(arrayW1, temp1);
        Double[][] temp3 = MatrixOperations.matrixMultiply(dx2dz2, temp2);
        Double[][] finalResult = MatrixOperations.matrixMultiply(arrayW2, temp3);

        System.out.println("Value of df/dx0 is " + Arrays.deepToString(finalResult));
        Double[][] finiteDiffenceAprox = MatrixOperations.finiteDiffAprox(functionPlus, functionMinus);
        System.out.println("Value of approximation is " + Arrays.deepToString(finiteDiffenceAprox));

        double sumOfFirstRow = MatrixOperations.sumElementsOfRow(finalResult, 0);
        double sumOfSecondRow = MatrixOperations.sumElementsOfRow(finalResult, 1);
        Double[][] derivative = {{sumOfFirstRow},{sumOfSecondRow}};
        System.out.println("Value of derivative is    " + Arrays.deepToString(derivative));

        if (sumOfFirstRow - finiteDiffenceAprox[0][0] < 0.0004 && sumOfSecondRow - finiteDiffenceAprox[1][0] < 0.0004) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}