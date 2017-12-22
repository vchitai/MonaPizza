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
    public static final int USERNAME_ERROR_FORMAT   =   -6;
    public static final int ALREADY_FRIEND          =   -7;
    public static final int FRIEND_NOT_EXIST        =   -8;
    public static final int NOT_SIGN_IN             =   -9;
    public static final int NOT_ENOUGH_MONEY        =   -10;

    public static String getMessage(int idError) {
        switch (idError) {
            case -1:
                return "Format password sai. Đề nghị dùng ít nhât mội kí tự Alphabet và chữ số, độ dài lớn hơn bằng 6";
            case -2:
                return "Username đã tồn tại.";
            case -3:
                return "Password sai.";
            case -4:
                return "Password mới giống password cũ, đề nghị bạn nhập password mới.";
            case -5:
                return "Username không tồn tại.";
            case -6:
                return "Tên username lỗi. Chỉ sử dụng các kí tự a..z và các kí tự số 0..9.";
            case -7:
                return "Đã là bạn bè.";
            case -8:
                return "Người bạn thêm vào danh sách bạn bè không tồn tại.";
            case -9:
                return "Bạn phải đăng nhập để dùng chức năng này.";
            case -10:
                return "Bạn không đủ tiền. Học thêm nhé!!!";

            default:
                return "Lỗi chưa biết.";
        }
    }
}
