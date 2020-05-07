package com.daily.english.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Http响应工具类
 *
 * @author fanggang
 */
public class ResponseUtil {
    public static <T> ResponseEntity<Body<T>> of(HttpStatus status) {
        return new ResponseEntity(Body.of(status), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> of(HttpStatus status, String message) {
        return new ResponseEntity(Body.of(status, message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> of(HttpStatus status, String message, String messageEn, String messageHk) {
        return new ResponseEntity(Body.of(status, message, messageEn, messageHk), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> ofAndData(HttpStatus status, String message, String messageEn, String messageHk,Object data) {
        return new ResponseEntity(Body.of(status, message, messageEn, messageHk,data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> ok() {
        return ResponseEntity.ok(Body.ok());
    }

    public static <T> ResponseEntity<Body<T>> ok(T data) {
        return ResponseEntity.ok(Body.ok(data));
    }

    public static <T> ResponseEntity<Body<T>> badRequest() {
        return new ResponseEntity(Body.badRequest(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> badRequest(String message) {
        return new ResponseEntity(Body.badRequest(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> unauthorized() {
        return new ResponseEntity(Body.unauthorized(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> unauthorized(String message) {
        return new ResponseEntity(Body.unauthorized(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> forbidden() {
        return new ResponseEntity(Body.forbidden(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> forbidden(String message) {
        return new ResponseEntity(Body.forbidden(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> notFound() {
        return new ResponseEntity(Body.notFound(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> notFound(String message) {
        return new ResponseEntity(Body.notFound(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> serverError() {
        return new ResponseEntity(Body.serverError(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Body<T>> serverError(String message) {
        return new ResponseEntity(Body.serverError(message), HttpStatus.OK);
    }
}
