package com.vovsn.g26appserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.DatatypeConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.sun.management.OperatingSystemMXBean;


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
        OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long cpuTimeStart = mxbean.getProcessCpuTime();

        long milliSecCounter = 0;
        long cycles = 0;

        StringBuilder dummy = new StringBuilder();

        SecureRandom random = SecureRandom.getInstanceStrong();

        while (milliSecCounter < msec) {
            byte[] randomBytes = new byte[4096];
            random.nextBytes(randomBytes);

            MessageDigest  messageDigest =  MessageDigest.getInstance("MD5");
            messageDigest.update(randomBytes);

            byte[] digest = messageDigest.digest();
            String hash = DatatypeConverter.printHexBinary(digest);

            int substrStart = random.nextInt(28);
            int substrLength = random.nextInt(2);
            dummy.append(hash, substrStart, substrStart + 1 + substrLength);


            milliSecCounter = (System.nanoTime() - timeStart) / 1_000_000;
            cycles += 1;
        }
        long hashLength = dummy.length();

        milliSecCounter = (System.nanoTime() - timeStart) / 1_000_000;

        long cpuTime = (mxbean.getProcessCpuTime() - cpuTimeStart) / 1_000_000;

        long totalTime = (System.nanoTime() - timeStart) / 1_000_000;

        return "MD5 cycles over 1K-string: " + cycles + " \n"
                + "StopWatch: predicted CPU time: " + milliSecCounter + ", timer total: " + totalTime + "\n"
                + "Process: " + cpuTime + "\n"
                + hashLength + dummy.charAt((int)hashLength - 1) + "\n";
    }

    @GetMapping(value = "/delay-device/{msec}")
    public String delayDevice(@PathVariable int msec) throws NoSuchAlgorithmException {
        long timeStart = System.nanoTime();

        long milliSecCounter = 0;
        long cycles = 0;

        StringBuilder dummy = new StringBuilder();

        try (FileInputStream fis = new FileInputStream("/dev/urandom")) {

            while (milliSecCounter < msec) {
                byte[] randomBytes = new byte[4096];
                fis.read(randomBytes);

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] md5Bytes = md.digest(randomBytes);

                String md5Hex = bytesToHex(md5Bytes);

                dummy.append(md5Hex, 25, 28);

                milliSecCounter = (System.nanoTime() - timeStart) / 1_000_000;
                cycles += 1;
            }
            long hashLength = dummy.length();

            milliSecCounter = (System.nanoTime() - timeStart) / 1_000_000;


            return "MD5 cycles over 1K-string: " + cycles + " \n"
                    + "StopWatch: predicted CPU time: " + milliSecCounter + "\n"
                    + hashLength + dummy.charAt((int) hashLength - 1) + "\n";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
