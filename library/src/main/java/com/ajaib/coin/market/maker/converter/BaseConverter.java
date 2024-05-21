package com.ajaib.coin.market.maker.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<E, D> {

  public abstract E of(D object);

  public abstract D to(E object);

  public List<D> to(List<E> lists) {
    return lists.stream()
        .map(this::to)
        .collect(Collectors.toList());
  }

  public List<E> of(List<D> lists) {
    return lists.stream()
        .map(this::of)
        .collect(Collectors.toList());
  }

}
