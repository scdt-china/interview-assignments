package xyz.sgld.sls;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.sgld.sls.data.ShortLink;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortLinkControllerTest {
    private Logger logger= LoggerFactory.getLogger(ShortLinkControllerTest.class);
    @Autowired
    private ShortLinkController shortLinkController;

    @Test
    @Order(1)
    public void createShortLinkTest() {
        ShortLinkRes<ShortLink> res = shortLinkController.getShortLink("https://blog.csdn.net/nitu1558/article/details/89478759");
        assertNotEquals(null, res);
        assertEquals(ShortLinkRes.RES_CODE_OK, res.getResCode());
    }

    @Test
    @Order(2)
    public void getOriginByShortLink() {
        ShortLinkRes<ShortLink> res = shortLinkController.getOriginLink("s.cn/001");
        assertNotEquals(null, res);
        assertEquals(ShortLinkRes.RES_CODE_OK, res.getResCode());
        assertNotEquals(null, res.getData().getOriginLink());
        logger.info("short link res info:{}",res);
    }
}
