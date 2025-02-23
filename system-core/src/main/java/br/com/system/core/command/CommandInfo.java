package br.com.system.core.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommandInfo {

  private String key;

  private String name;
  private String description;

  private String usageMessage;

  private List<String> alias;

  private String permission;
  private String permissionMessage;

  private boolean enabled;
}
