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
import xyz.sgld.sls.data.ShortLinkRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortLinkServiceTest {
    Logger logger = LoggerFactory.getLogger(ShortLinkServiceTest.class);
    private static final String TEST_LINK = "testlink";
    @Autowired
    ShortLinkService shortLinkService;

    @Test
    @Order(1)
    public void createShortLinkTest() {
        ShortLinkRes<ShortLink> shortLinkRes = shortLinkService.createShortLink(TEST_LINK);
        assertNotEquals(null, shortLinkRes);
        logger.info("short link info:{}", shortLinkRes);
        long id = shortLinkRes.getData().getId();
        shortLinkRes = shortLinkService.createShortLink(TEST_LINK);
        assertNotEquals(null, shortLinkRes);
        logger.info("short link info:{}", shortLinkRes);
        assertEquals(id, shortLinkRes.getData().getId());
    }

    @Test
    @Order(2)
    public void getShortLinkByShortLinkTest() {
        ShortLinkRes<ShortLink> shortLinkRes = shortLinkService.getOriginLinkByShortLink("s.cn/001");
        logger.info("short link :{}", shortLinkRes);
        assertNotEquals(null, shortLinkRes);
        assertNotEquals(null, shortLinkRes.getData());

        shortLinkRes = shortLinkService.getOriginLinkByShortLink("s.cn/004");
        assertNotEquals(null, shortLinkRes);
        assertEquals(ShortLinkRes.RES_CODE_NOT_FOUND_ERROR, shortLinkRes.getResCode());

        shortLinkRes = shortLinkService.getOriginLinkByShortLink("");
        assertNotEquals(null, shortLinkRes);
    }

    @Test
    public void exceptionPathTest() {

        ShortLinkRes<ShortLink> shortLinkRes = shortLinkService.createShortLink("");
        assertNotEquals(null, shortLinkRes);

    }
}
