package test;

import main.TaskOne;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class TaskOneTest {

    @Test
    public void testConcatenate() {
        TaskOne.MyUnit myUnit = new TaskOne.MyUnit();

        String result = myUnit.concatenate("one", "two");

        assertEquals("onetwo", result);



    }


}