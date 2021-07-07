package xyz.sgld.sls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.sgld.sls.util.NumberUtil;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUnitTest {
    @Test
    public void numberTo62BitTest(){
        String number=NumberUtil.longTo62Str(0);
        assertEquals("000",number);
        number=NumberUtil.longTo62Str(60);
        assertEquals("00Y",number);
        number=NumberUtil.longTo62Str(62);
        assertEquals("010",number);
        number=NumberUtil.longTo62Str(6200);
        assertEquals("1C0",number);
        number=NumberUtil.longTo62Str(6200000000l);
        assertEquals("6LAze0",number);
        System.out.println(number);

    }

    @Test
    public void numberArgumentErrTest(){
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,()->{
                    String number=NumberUtil.longTo62Str(-3234);
                });
        IllegalArgumentException thrown2=
                assertThrows(IllegalArgumentException.class,()->{
                    long l=NumberUtil.str62ToLong(null);
                });
        thrown=
                assertThrows(IllegalArgumentException.class,()->{
                    int index=NumberUtil.getCharIndexOf('$');
                });
    }

    @Test
    public void check62StrTest(){
        boolean result=NumberUtil.check62Str("asdfa32");
        assertEquals(true,result);
        result=NumberUtil.check62Str("asdfasd@#fasdf");
        assertEquals(false,result);
    }

    @Test
    public void getCharIndexOfTest(){
        int index=NumberUtil.getCharIndexOf('0');
        assertEquals(0,index);
        index =NumberUtil.getCharIndexOf('9');
        assertEquals(9,index);
        index = NumberUtil.getCharIndexOf('a');
        assertEquals(10,index);
        index = NumberUtil.getCharIndexOf('A');
        assertEquals( 36,index);
        index = NumberUtil.getCharIndexOf('Z');
        assertEquals(61,index);
    }

    @Test
    public void str62ToNumberTest(){
        long number=NumberUtil.str62ToLong("001");
        assertEquals(number,1);
        number=NumberUtil.str62ToLong("00Y");
        assertEquals(number,60);
        number=NumberUtil.str62ToLong("6LAze0");
        assertEquals(number,6200000000l);
    }
}
