package br.com.system.core.format.time;

import java.util.concurrent.TimeUnit;

public class TimeFormatter {



  public static String format(long time) {
    long day = TimeUnit.MILLISECONDS.toDays(time);
    long hours = TimeUnit.MILLISECONDS.toHours(time) - day * 24L;
    long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.MILLISECONDS.toHours(time) * 60L;
    long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MILLISECONDS.toMinutes(time) * 60L;

    StringBuilder stringBuilder = new StringBuilder();
    if (time <= 0) return "0s";

    if (day > 0L) stringBuilder.append(day).append("d");
    if (hours > 0L) stringBuilder.append(hours).append("h");
    if (minutes > 0L) stringBuilder.append(minutes).append("m");
    if (seconds > 0L) stringBuilder.append(seconds).append("s");

    return stringBuilder.toString();
  }

}