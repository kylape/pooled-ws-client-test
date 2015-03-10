package com.redhat.gss.ws;

public class ProxyWrapper<T> {
  private final T item;

  public ProxyWrapper(T item) {
    this.item = item;
  }

  public T getItem() {
    return item;
  }
}
