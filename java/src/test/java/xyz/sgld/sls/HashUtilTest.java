package xyz.sgld.sls;

import org.junit.jupiter.api.Test;
import xyz.sgld.sls.util.HashUtil;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
public class HashUtilTest {
    @Test
    public void sha256Test() throws NoSuchAlgorithmException {
        String hasStr= HashUtil.sha256("ewew");
        assertNotEquals(hasStr,null);
    }
}
