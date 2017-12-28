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
        switch (idError) {
            case PASSWORD_ERROR_FORMAT:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_PasswordErrorFormat);
            case USERNAME_EXISTED:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_UsernameExisted);
            case WRONG_PASSWORD:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_WrongPassword);
            case SAME_PASSWORD:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_SamePassword);
            case USERNAME_NOT_EXISTED:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_UsernameNotExisted);
            case USERNAME_ERROR_FORMAT:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_UsernameErrorFormat);
            case ALREADY_FRIEND:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_AlreadyFriend);
            case FRIEND_NOT_EXIST:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_FriendNotExist);
            case NOT_SIGN_IN:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_NotSignedIn);
            case NOT_ENOUGH_MONEY:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_NotEnoughMoney);
            case TOO_MANY_WRONGS:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_TooManyWrongs);

            default:
                return MonaPizza.getAppContext().getResources().getString(R.string.EM_NotKnowingError);
        }
    }

    public static void setExitCode(int e) {
        m_exitcode = e;
    }

    public static int getExitCode() {
        return m_exitcode;
    }
}
