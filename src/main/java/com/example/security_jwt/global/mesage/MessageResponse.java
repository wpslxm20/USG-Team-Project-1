package com.example.security_jwt.global.mesage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse<T>{
    private T data;
    private String message;

    public MessageResponse(String message) { this.message = message;}
    public MessageResponse(T data, String message) {this.data = data; this.message = message;}
}
