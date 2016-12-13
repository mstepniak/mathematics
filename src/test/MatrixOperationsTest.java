package test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static main.MatrixOperations.matrixMultiply;
import static main.MatrixOperations.matrixMultiply1D;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class MatrixOperationsTest {

    @Test
    public void matrixMultiply1DTest() {
        //given
        String expectedResult = "[[14.0], [14.0], [14.0]]";
        Double[][] A = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}};
        Double[] B = {1.0, 2.0, 3.0};

        //when
        Double[][] result = matrixMultiply1D(A, B);
        String actualResult = Arrays.deepToString(result);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void matrixMultiplyTest() {
        //given
        String expectedResult = "[[6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0], [6.0, 12.0, 18.0]]";
        Double[][] A = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}};
        Double[][] B = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}};

        //when
        Double[][] result = matrixMultiply(A, B);
        String actualResult = Arrays.deepToString(result);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }
}
