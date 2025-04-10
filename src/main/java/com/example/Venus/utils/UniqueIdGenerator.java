package com.example.Venus.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
/*
    @created 30/08/2024 5:39 PM
    @project electro-point
    @author biplaw.chaudhary
*/
public class UniqueIdGenerator {
    private static final AtomicInteger sequence = new AtomicInteger(100000);
    private static final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
    private static final DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("DDD");
    private static final DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
    private static final DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
    private static final Random random = new Random();

    public static long generateUniqueId() {
        LocalDateTime now = LocalDateTime.now();
        
        String year = now.format(yearFormatter);
        String dayOfYear = now.format(dayOfYearFormatter);
        String hour = now.format(hourFormatter);
        String minute = now.format(minuteFormatter);
        
        int nextSequence = sequence.incrementAndGet();
        if (nextSequence > 999999) {
            sequence.set(100001);
            nextSequence = 100001;
        }
        
        String uniqueIdString = String.format("%s%s%s%s%06d", 
            year, 
            padLeft(dayOfYear, 3), 
            padLeft(hour, 2), 
            padLeft(minute, 2), 
            nextSequence);
        
        return Long.parseLong(uniqueIdString);
    }

    public static String generateOTP() {
        LocalDateTime now = LocalDateTime.now();

        String dayOfYear = now.format(dayOfYearFormatter);
        String hour = now.format(hourFormatter);
        String minute = now.format(minuteFormatter);

        // Combine day of year, hour, and minute
        long timeComponent = Long.parseLong(dayOfYear + hour + minute);

        // Get current time in milliseconds and use last 5 digits
        long currentTimeMs = System.currentTimeMillis() % 100000;

        // Combine time component and current time in ms
        long seed = timeComponent * 100000 + currentTimeMs;

        // Use the seed to generate a random 6-digit number
        random.setSeed(seed);
        int otp = 100000 + random.nextInt(900000);

        return String.format("%06d", otp);
    }

    private static String padLeft(String input, int length) {
        return String.format("%0" + length + "d", Integer.parseInt(input));
    }

}