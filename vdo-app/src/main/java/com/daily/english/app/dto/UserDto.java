package com.daily.english.app.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * 实现了UserDetails的User，角色在这里被设置。
 *
 * @author fanggang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    private static final long serialVersionUID = 2396654715019746670L;
    /**
     * 用户名（登录名）
     */
    private String username;

    private String password;
}
