package com.mentiaz.mentiaz.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<T> accepted(T body) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
    }

    public static <T> ResponseEntity<T> badRequest(T body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static <T> ResponseEntity<T> notFound(T body) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}
