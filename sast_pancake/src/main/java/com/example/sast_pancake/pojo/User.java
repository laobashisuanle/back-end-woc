package com.example.sast_pancake.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int role;
    private String token;
}
