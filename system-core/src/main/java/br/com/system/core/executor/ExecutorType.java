package br.com.system.core.executor;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExecutorType {

  COMMAND("[command]"),
  MESSAGE("[chat]", "[action]", "[actions]", "[title]", "[broadcast]"),
  NONE;

  private final String[] identifiers;

  ExecutorType(String... identifiers) {
    this.identifiers = identifiers;
  }

  public String stripIdentifiers(String subject) {
    for (String identifier : identifiers) {
      subject = subject.replace(identifier, "");
    }
    return subject;
  }

  public static ExecutorType resolve(String subject) {
    String lowerCaseSubject = subject.toLowerCase();

    return Arrays.stream(values())
     .filter(type -> Arrays.stream(type.getIdentifiers()).anyMatch(lowerCaseSubject::startsWith))
     .findAny()
     .orElse(MESSAGE);
  }
}
