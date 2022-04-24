package com.pedro.assignment.controller.exception;

import lombok.AllArgsConstructor;

public class ItemNotFoundException extends RuntimeException{
  public ItemNotFoundException(String message) {super(message);}
}
