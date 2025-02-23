package br.com.system.core.format.time;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TimeConverter {

  public static Long convertToMillis(String string) {
    long time = 0;

    for (String split : string.split(" ")) {
      char charAt = split.charAt(split.length() - 1);

      int timeInt;
      try {
        timeInt = Integer.parseInt(split.replace(Character.toString(charAt), ""));
      } catch (NumberFormatException exception) {
        return (long) -1;
      }

      for (TimeMultiplier timeMultiplier : TimeMultiplier.values()) {
        if (timeMultiplier.getDiminutive() != charAt) continue;

        time += timeMultiplier.getMultiplier() * timeInt;
      }
    }

    return time;
  }

  @Getter
  @AllArgsConstructor
  public enum TimeMultiplier {

    SECONDS(1000, 's'),
    MINUTES(60 * 1000, 'm'),
    HOURS(60 * 60 * 1000, 'h'),
    DAYS(24 * 60 * 60 * 1000, 'd'),
    WEEKS(7 * 24 * 60 * 60 * 1000, 'w'),
    YEARS((long) 365 * 24 * 60 * 60 * 1000, 'y');

    private final long multiplier;
    private final char diminutive;

  }
}
