package com.monapizza.monapizza.core;

/**
 * Created by Vinh Ton That on 12/2/2017.
 */

public class ErrorList {
    ErrorList() {}

    public static final int PASSWORD_ERROR_FORMAT   =   -1;
    public static final int USERNAME_EXISTED        =   -2;
    public static final int WRONG_PASSWORD          =   -3;
    public static final int SAME_PASSWORD           =   -4;
    public static final int USERNAME_NOT_EXISTED    =   -5;

    public static String getMessage(int idError) {
        switch (idError) {
            case -1:
                return "Format password sai. Đề nghị chỉ dùng các kí tự Alphabet và chữ số.";
            case -2:
                return "Username đã tồn tại.";
            case -3:
                return "Password sai.";
            case -4:
                return "Password mới giống password cũ, đề nghị bạn nhập password mới.";
            case -5:
                return "Username không tồn tại.";
                default:
                    return "Lỗi chưa biết.";
        }
    }
}
