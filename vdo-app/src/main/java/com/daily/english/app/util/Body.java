package com.daily.english.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * Response Body Content
 *
 * @param <T> data
 * @author fanggang
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "返回的JSON内容")
@JsonInclude(Include.NON_NULL)
public class Body<T> {

    /**
     * 时间戳
     */
    long timestamp;

    /**
     * 返回状态码，与 Http status code 具有相同的值。
     */
    @ApiModelProperty(value = "状态码）", required = true, notes = "与 Http status code 具有相同的值")
    Integer status;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "消息", required = true)
    String message;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "消息英文", required = true)
    String messageEn;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "消息繁体", required = true)
    String messageHk;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "具体数据")
    T data;

    private static BodyBuilder create(HttpStatus status) {
        return Body.builder()
                .timestamp(System.currentTimeMillis()).status(status.value()).data("").message(status.getReasonPhrase());
    }

    public static final Body of(HttpStatus status) {
        return Body.create(status).data("").build();
    }

    public static final Body of(HttpStatus status, String message) {
        return Body.create(status).message(message).data("").build();
    }

    public static final Body of(HttpStatus status, String message, String messageEn, String messageHk) {
        return Body.create(status).message(message).messageEn(messageEn).messageHk(messageHk).data("").build();
    }

    public static final Body of(HttpStatus status, String message, String messageEn, String messageHk,Object data) {
        if(status.value() != 200 && data == null){
            data = "";
        }
        return Body.create(status).message(message).messageEn(messageEn).messageHk(messageHk).data(data).build();
    }

    public static final Body ok() {
        return ok(null);
    }

    public static final Body ok(Object data) {
        return Body.create(HttpStatus.OK).data(data).build();
    }

    public static final Body badRequest() {
        return Body.create(HttpStatus.BAD_REQUEST).build();
    }

    public static final Body badRequest(String message) {
        return Body.create(HttpStatus.BAD_REQUEST).message(message).data("").build();
    }

    public static final Body unauthorized() {
        return Body.create(HttpStatus.UNAUTHORIZED).build();
    }

    public static final Body unauthorized(String message) {
        return Body.create(HttpStatus.UNAUTHORIZED).message(message).build();
    }

    public static final Body forbidden() {
        return Body.create(HttpStatus.FORBIDDEN).build();
    }

    public static final Body forbidden(String message) {
        return Body.create(HttpStatus.FORBIDDEN).message(message).build();
    }

    public static final Body notFound() {
        return Body.create(HttpStatus.NOT_FOUND).build();
    }

    public static final Body notFound(String message) {
        return Body.create(HttpStatus.NOT_FOUND).message(message).build();
    }

    public static final Body serverError() {
        return Body.create(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public static final Body serverError(String message) {
        return Body.create(HttpStatus.INTERNAL_SERVER_ERROR).message(message).build();
    }
}
