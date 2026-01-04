package com.example.server.domain.news.client;

public interface ObjectStorageClient {
    String uploadPng(String key, byte[] pngBytes);
}