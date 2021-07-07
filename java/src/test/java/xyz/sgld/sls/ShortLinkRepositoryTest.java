package xyz.sgld.sls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.sgld.sls.data.ShortLink;
import xyz.sgld.sls.data.ShortLinkRepository;

@SpringBootTest()

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortLinkRepositoryTest {
    JdbcTemplate jdbc;

    public static final String TEST_SHORT_LINK="testShortLink";
    public static final String TEST_ORIGIN_LINE="testOriginLink";

    @Autowired ShortLinkRepository shortLinkRepository;
    @Test
    @Order(1)
    public void createSaveShortLink(){
        ShortLink shortLink=new ShortLink();
        shortLink.setHash(TEST_SHORT_LINK);
        shortLink.setOriginLink(TEST_ORIGIN_LINE);
        shortLink=shortLinkRepository.createShortLink(shortLink);
        assertNotEquals(0,shortLink.getId());
    }

    @Test
    @Order(2)
    public void getOriginLinkByShortLinkTest(){
        ShortLink shortLink=shortLinkRepository.getOriginLinkByHash(TEST_SHORT_LINK);
        assertEquals(TEST_ORIGIN_LINE,shortLink.getOriginLink());
    }

    @Order(3)
    @Test void getShortLinkByOriginLinkTest(){
        ShortLink shortLink=shortLinkRepository.getShortLinkByOriginLink(TEST_ORIGIN_LINE);
        assertEquals(TEST_SHORT_LINK,shortLink.getHash());
    }

    @Order(4)
    @Test void getShortLinkByIdTest(){
        ShortLink shortLink=shortLinkRepository.getShortLinkById(1);
        assertNotEquals(null,shortLink);


    }

    @Test
    public void exceptionPathTest(){
        Exception thrown=
                assertThrows(Exception.class,()->{
                    shortLinkRepository.createShortLink(null);
                });

    }

    void before(){
        createSaveShortLink();
    }

}
