package com.example.server.global.exception.model;

public class GeminiNoImageException extends RuntimeException {
  public GeminiNoImageException(String body) {
    super("Gemini returned no image: " + body);
  }
}