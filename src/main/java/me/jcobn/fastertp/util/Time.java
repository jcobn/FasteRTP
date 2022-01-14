package me.jcobn.fastertp.util;

import me.jcobn.fastertp.file.Messages;

public class Time {
    public static String secondsToHumanReadableTime(int secs) {
        String sHours = Messages.TIME_HOURS.getMessageNoPrefix();
        String sMinutes = Messages.TIME_MINUTES.getMessageNoPrefix();
        String sSeconds = Messages.TIME_SECONDS.getMessageNoPrefix();
        int hours = secs / 3600;
        int minutes = (secs % 3600) / 60;
        int seconds = secs % 60;
        return hours != 0 ? hours + " " + sHours + " " + minutes + " " + sMinutes : minutes != 0 ? minutes + " " + sMinutes + " " + seconds + " " + sSeconds : seconds + " " + sSeconds;
    }
}
