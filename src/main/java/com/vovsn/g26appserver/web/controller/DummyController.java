package com.vovsn.g26appserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


@RestController
@RequestMapping(value = "/dummy")
public class DummyController {

    @Autowired
    public DummyController() {

    }

    @GetMapping(value = "/static")
    public String fixedValue() {
        return "Success\n";
    }


    @GetMapping(value = "/delay/{msec}")
    public String delay(@PathVariable int msec) throws NoSuchAlgorithmException {
        long timeStart = System.nanoTime();
        ThreadMXBean mxbean = ManagementFactory.getThreadMXBean();

        if (!mxbean.isCurrentThreadCpuTimeSupported()) {
            throw new RuntimeException("Thread time is not supported");
        }
        long cpuTimeStart = mxbean.getCurrentThreadCpuTime();

        long cpuThreadTimeCounter = 0;
        long cycles = 0;

        StringBuilder dummy = new StringBuilder();

        SecureRandom random = SecureRandom.getInstanceStrong();

        double number = random.nextDouble();

        while (cpuThreadTimeCounter < msec * 1_000_000L) {
            number = number * sqrt(sin(number * cos(number)));
            number = number / sqrt(cos(number + cycles + 1));

            dummy.append(number);

            cpuThreadTimeCounter = mxbean.getCurrentThreadCpuTime() - cpuTimeStart;
            cycles += 1;
        }
        long hashLength = dummy.length();

        long totalTime = (System.nanoTime() - timeStart) / 1_000_000;

        return "Math cycles : " + cycles + " \n"
                + "Real timer total : " + totalTime + "\n"
                + "Thread CPU : " + cpuThreadTimeCounter / 1_000_000 + "\n"
                + hashLength + dummy.charAt((int)hashLength - 1) + "\n";
    }
}
