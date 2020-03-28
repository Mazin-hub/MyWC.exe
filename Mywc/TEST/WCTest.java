import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class WCTest extends TestCase {
    @Test
    public void testNormal(){
        File f = new File("e:/develop/theme/Mywc/TEST");
        String[] p = WC.Path(f);
        assertEquals("e:\\develop\\theme\\Mywc\\TEST\\test.java",p[0]);
        assertEquals("e:\\develop\\theme\\Mywc\\TEST\\WCTest.java",p[1]);
    }
}
