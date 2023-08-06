package com.dsadara.realestatebatchservice.aop;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomStopwatchTest {
    @Autowired
    private CustomStopwatch customStopwatch;

    @Test
    public void CustomStopWatch_general() throws Exception {
        //given
        customStopwatch.start("A");
        Thread.sleep(500);
        customStopwatch.stop("A");

        customStopwatch.start("B");
        Thread.sleep(400);
        customStopwatch.stop("B");

        customStopwatch.start("C");
        Thread.sleep(100);
        customStopwatch.stop("C");
        //when
        //then
        assertEquals("A", customStopwatch.getStopWatches().get("A").getId());
        assertEquals("B", customStopwatch.getStopWatches().get("B").getId());
        assertEquals("C", customStopwatch.getStopWatches().get("C").getId());
        assertEquals(3, customStopwatch.getStopWatches().size());
    }

    @Test
    @Ignore
    public void CustomStopWatch_PrettyPrint() throws Exception {
        //given
        //when
        customStopwatch.start("A");
        Thread.sleep(500);
        customStopwatch.stop("A");

        customStopwatch.start("B");
        Thread.sleep(400);
        customStopwatch.stop("B");

        customStopwatch.start("C");
        Thread.sleep(100);
        customStopwatch.stop("C");
        //then
        System.out.println(customStopwatch.prettyPrint());
    }

    @Test
    public void CustomStopWatch_reset() throws Exception {
        //given
        customStopwatch.start("A");
        Thread.sleep(500);
        customStopwatch.stop("A");

        customStopwatch.start("B");
        Thread.sleep(400);
        customStopwatch.stop("B");

        customStopwatch.start("C");
        Thread.sleep(100);
        customStopwatch.stop("C");
        //when
        customStopwatch.reset();
        //then
        assertEquals(0, customStopwatch.getStopWatches().size());
        assertEquals(0, customStopwatch.getTotalStopWatch().getTotalTimeNanos());
    }
}