package br.com.system.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Identifier {

  private UUID id;
  private String name;
}
