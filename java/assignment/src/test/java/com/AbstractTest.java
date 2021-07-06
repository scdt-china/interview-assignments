package com;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class AbstractTest {

    @Before
    public void setUpRoot() {
        initMocks(this);
        this.setUp();
    }

    public void setUp() {

    }
}
