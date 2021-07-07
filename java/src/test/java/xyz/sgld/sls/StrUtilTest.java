package xyz.sgld.sls;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import xyz.sgld.sls.util.StrUtil;

public class StrUtilTest {
    @Test
    public void strIsEmptyTest(){
        boolean empty= StrUtil.isEmpty("");
        assertEquals(empty,true);
        empty = StrUtil.isEmpty(null);
        assertEquals(empty,true);
        empty = StrUtil.isEmpty("  ");
        assertEquals(empty,true);
        empty= StrUtil.isEmpty("  as");
        assertEquals(empty,false);
    }
}
