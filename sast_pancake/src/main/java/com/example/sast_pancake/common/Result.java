package com.example.sast_pancake.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<D> implements Serializable {

    private boolean success;
    private Integer errCode;
    private String errMsg;
    private Object data;


    public  static Result suc(Object data){
        return result(true,null,null,data);
    }

    public static Result fail(Integer errCode,String errMsg){
        return result(false,errCode,errMsg,null);
    }


    private static Result result( boolean success,Integer errCode,String errMsg , Object data){
        Result result =new Result();
        result.setData(data);
        result.setSuccess(success);
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);
        return  result;
    }

}
