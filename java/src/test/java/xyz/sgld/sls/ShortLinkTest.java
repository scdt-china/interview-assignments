package xyz.sgld.sls;

import org.junit.jupiter.api.Test;
import xyz.sgld.sls.data.ShortLink;

import static org.junit.jupiter.api.Assertions.*;
public class ShortLinkTest {
    @Test
    public void startLinkTest(){
        ShortLink shortLink=new ShortLink();
        assertNotEquals(null,shortLink);
        assertEquals(0,shortLink.getId());
        shortLink.setId(1);
        assertEquals(1,shortLink.getId());
        shortLink.setShortLink("a");
        assertEquals("a",shortLink.getShortLink());
        shortLink.setHash("b");
        assertEquals("b",shortLink.getHash());
        shortLink.setOriginLink("c");
        assertEquals("c",shortLink.getOriginLink());
        assertNotEquals(0,shortLink.hashCode());
        assertNotEquals(false,shortLink.equals(shortLink));
        assertNotEquals(true,shortLink.equals(null));
        assertNotEquals(true,shortLink.equals(new ShortLink()));
        assertNotEquals(true,shortLink.equals(""));
        Object obj=new ShortLink();


        assertNotEquals(true,shortLink.equals(obj));
        ((ShortLink)obj).setId(1);
        ((ShortLink)obj).setShortLink("a");
        ((ShortLink)obj).setOriginLink("c");
        ((ShortLink)obj).setHash("b");
        assertEquals(true,shortLink.equals(obj));
    }
}
