package com.example.server.global.exception.model;

public class GeminiRateLimitException extends RuntimeException {
  public GeminiRateLimitException(String body) {
    super("Gemini rate limited(429): " + body);
  }
}