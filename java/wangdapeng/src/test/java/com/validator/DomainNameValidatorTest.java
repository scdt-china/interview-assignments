package com.validator;

import com.AbstractTest;
import com.TestData;

import org.junit.Test;
import org.mockito.InjectMocks;

public class DomainNameValidatorTest extends AbstractTest {

    @InjectMocks
    private DomainNameValidator domainNameValidator;

    @Test
    public void testValidateGetLongUrlSuccessPath() {
        domainNameValidator.validateGetLongUrl(TestData.SHORT_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetLongUrlNullFailedPath() {
        domainNameValidator.validateGetLongUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetLongUrlEmptyUrlFailedPath() {
        domainNameValidator.validateGetLongUrl(TestData.EMPTY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlIllegalShortUrlWithTooManySpliterCharFailedPath() {
        domainNameValidator.validateGetLongUrl(TestData.SHORT_URL_ILLEGAL2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlIllegalShortUrlNotBaseFailedPath() {
        domainNameValidator.validateGetLongUrl(TestData.SHORT_URL_ILLEGAL);
    }


    @Test
    public void testValidateGetShortUrlSuccessPath() {
        domainNameValidator.validateGetShortUrl(TestData.LONG_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlNullFailedPath() {
        domainNameValidator.validateGetShortUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlEmptyUrlFailedPath() {
        domainNameValidator.validateGetShortUrl(TestData.EMPTY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateGetShortUrlTooLongUrlFailedPath() {
        domainNameValidator.validateGetShortUrl(TestData.TOO_LONG_URL);
    }


}
