package br.com.system.core.model.user.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class Address {

  private String address;
  private Instant date;

  public long toEpochMilli() {
    return date.toEpochMilli();
  }
}
