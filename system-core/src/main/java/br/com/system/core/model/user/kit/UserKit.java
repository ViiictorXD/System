package br.com.system.core.model.user.kit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserKit {

  private String kitName;

  private int pickupCount;
  private long lastPickup;

  public long getNextPickup(long kitDelay) {
    return lastPickup + kitDelay;
  }
}
