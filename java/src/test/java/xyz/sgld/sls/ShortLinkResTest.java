package xyz.sgld.sls;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static xyz.sgld.sls.ShortLinkRes.*;

public class ShortLinkResTest {
    @Test
    public void createEmptyResTest(){
        ShortLinkRes<String> res=ShortLinkRes.createOkRes();
        assertEquals(res.getResCode(),ShortLinkRes.RES_CODE_OK);
        assertEquals(res.getDes(),ShortLinkRes.RES_DES_OK);

        res=ShortLinkRes.createTimeoutRes();
        assertEquals(res.getResCode(),RES_CODE_TIMEOUT_ERROR);
        assertEquals(res.getDes(),RES_DES_TIMEOUT_ERROR);

        res=ShortLinkRes.createUnknownRes();
        assertEquals(res.getResCode(),RES_CODE_UNKNOWN_ERROR);
        assertEquals(res.getDes(),RES_DES_UNKNOWN_ERROR);

        res=ShortLinkRes.createMissParamsRes();
        assertEquals(res.getResCode(),RES_CODE_MISSION_PARAMS);
        assertEquals(res.getDes(),RES_DES_MISSION_PARAMS);

        res=ShortLinkRes.createArgsErrorRes();
        assertEquals(res.getResCode(),RES_CODE_ARGUMENT_ERROR);
        assertEquals(res.getDes(),RES_DES_ARGUMENT_ERROR);

        res=ShortLinkRes.createNotFoundRes();
        assertEquals(RES_CODE_NOT_FOUND_ERROR,res.getResCode());
        assertEquals(RES_DES_NOT_FOUND_ERROR,res.getDes());
        res.setError(null);
        assertEquals(null,res.getError());
        res.setData(null);
        assertEquals(null,res.getData());
        res.setResCode(1);
        assertEquals(1,res.getResCode());
        res.setDes("a");
        assertEquals("a",res.getDes());
    }
}
