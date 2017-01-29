/**
 *
 */
package com.arp.java.lib.image.test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import mockit.Mocked;
import mockit.Expectations;

import com.arp.java.lib.image.*;

/**
 * @author haruo
 *
 */
public class JLeadExceptionTest {

    @Test
    public void testConstoructorString(){
        String message = "test" ;
        JLeadException exception = new JLeadException(message);

        // verfiy
        assertThat(exception, is(notNullValue()));
    }

    @Test
    public void testGetMessage(){
        String message = "test" ;
        JLeadException exception = new JLeadException(message);

        // exercise
        String actual = exception.getMessage();

        // verfiy
        assertThat(actual, is(message));
    }

    @Test
    public void testNullGetMessage(){
        JLeadException exception = new JLeadException(null);

        // exercise
        String actual = exception.getMessage();

        // verfiy
        assertThat(actual, is(nullValue()));
    }


    @Test
    public void testConstoructorStringIntInt(){
        String message = "test" ;
        int category = 100;
        int status = 200;

        JLeadException exception = new JLeadException(message,category,status);

        // verfiy
        assertThat(exception, is(notNullValue()));
    }

    @Test
    public void testGetCategory(){
        String message = "test" ;
        int category = 100;
        int status = 200;

        JLeadException exception = new JLeadException(message,category,status);
        
        // exercise
        int actual = exception.getCategory();

        // verfiy
        assertThat(actual, is(category));
    }

    @Test
    public void testGetStatus(){
        String message = "test" ;
        int category = 100;
        int status = 200;

        JLeadException exception = new JLeadException(message,category,status);
        
        // exercise
        int actual = exception.getStatus();

        // verfiy
        assertThat(actual, is(status));
    }
}
