package br.com.system.core.manager;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractManager<E> {

  @Getter
  private final List<E> entities = new ArrayList<>();

  public void addEntity(E entity) {
    entities.add(entity);
  }

  public void addEntities(Collection<E> collection) {
    entities.addAll(collection);
  }

  public void removeEntity(E entity) {
    entities.remove(entity);
  }

  public Stream<E> stream() {
    return entities.stream();
  }

  public Optional<E> optional(Predicate<? super E> predicate) {
    return stream().filter(predicate).findAny();
  }

  public boolean anyMatch(Predicate<?super E> predicate) {
    return stream().anyMatch(predicate);
  }

  public int size() {
    return entities.size();
  }
}
