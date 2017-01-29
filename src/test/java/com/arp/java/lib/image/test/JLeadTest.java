/**
 *
 */
package com.arp.java.lib.image.test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import mockit.Mocked;
import mockit.MockUp;
import mockit.Expectations;

import com.arp.java.lib.image.*;

/**
 * @author haruo
 *
 */
public class JLeadTest {
    @Mocked private JLead jleadMain;

    @Test(expected = IllegalArgumentException.class)
    public void testCreateThumbnailsInputFileNull(){
        // setup
        String inputFile=null;
        String outputFile=null;
        int newHeight=0;
        int scale=0;
        float quality=0;
        String description=null;

        try{
            JLead.createThumbnails(inputFile, outputFile, newHeight, scale, quality, description);
        } catch(JLeadException e){
            e.printStackTrace();
        }
    }
}
