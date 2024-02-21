package com.example.sast_pancake.pojo;

import lombok.Data;

@Data
public class Pancake {
    private int id;
    private String title;
    private String createTime;
    private String ddl;
    private  int isDone;
}
