package com.example.shortUrl;

import com.example.shortUrl.util.IdConverter;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class IdConverterTest {
    @Test
    public void testTen2SixtyTwo() {
        assertEquals("k", IdConverter.ten2SixtyTwo(10));
    }
}
