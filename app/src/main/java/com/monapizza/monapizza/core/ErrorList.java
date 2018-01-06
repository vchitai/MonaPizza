package com.monapizza.monapizza.core;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;

/**
 * Created by Vinh Ton That on 12/2/2017.
 */

public class ErrorList {

    private static int m_exitcode;

    ErrorList() {
        m_exitcode = 0;
    }

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
    public static final int TOO_MANY_WRONGS         =   -11;

    public static String getMessage(int idError) {
        String mess = null;
        switch (idError) {
            case PASSWORD_ERROR_FORMAT: {
                mess = MonaPizza.getResourceString(R.string.EM_PasswordErrorFormat);
                break;
            }
            case USERNAME_EXISTED: {
                mess = MonaPizza.getResourceString(R.string.EM_UsernameExisted);
                break;
            }
            case WRONG_PASSWORD: {
                mess = MonaPizza.getResourceString(R.string.EM_WrongPassword);
                break;
            }
            case SAME_PASSWORD: {
                mess = MonaPizza.getResourceString(R.string.EM_SamePassword);
                break;
            }
            case USERNAME_NOT_EXISTED: {
                mess = MonaPizza.getResourceString(R.string.EM_UsernameNotExisted);
                break;
            }
            case USERNAME_ERROR_FORMAT: {
                mess = MonaPizza.getResourceString(R.string.EM_UsernameErrorFormat);
                break;
            }
            case ALREADY_FRIEND: {
                mess = MonaPizza.getResourceString(R.string.EM_AlreadyFriend);
                break;
            }
            case FRIEND_NOT_EXIST: {
                mess = MonaPizza.getResourceString(R.string.EM_FriendNotExist);
                break;
            }
            case NOT_SIGN_IN: {
                mess = MonaPizza.getResourceString(R.string.EM_NotSignedIn);
                break;
            }
            case NOT_ENOUGH_MONEY: {
                mess = MonaPizza.getResourceString(R.string.EM_NotEnoughMoney);
                break;
            }
            case TOO_MANY_WRONGS: {
                mess = MonaPizza.getResourceString(R.string.EM_TooManyWrongs);
                break;
            }

            default: {
                mess = MonaPizza.getResourceString(R.string.EM_NotKnowingError);
                break;
            }
        }

        m_exitcode = 0;
        return mess;
    }

    public static void setExitCode(int e) {
        m_exitcode = e;
    }

    public static int getExitCode() {
        return m_exitcode;
    }
}
