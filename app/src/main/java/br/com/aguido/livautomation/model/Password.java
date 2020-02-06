package br.com.aguido.livautomation.model;

import android.content.Context;
import android.text.TextUtils;

import br.com.aguido.livautomation.R;

/**
 * Created by selem.gomes on 27/04/16.
 */
public class Password {
    private final int QTDE_CHARACTERS_SEQUENTIAL = 3;
    private final int QTDE_CHARACTERS_REPEAT = 4;

    private String confirmPassword;
    private String newPassword;
    private String oldPassword;
    private boolean isValid;
    private String msg;

    public Password(String confirmPassword, String newPassword, String oldPassword) {
        this.confirmPassword = confirmPassword;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Password checkIfPasswordIsValid(Context context) {
        if (!getNewPassword().equals(getConfirmPassword())) {
            setValid(false);
            setMsg(context.getString(R.string.change_password_different_passwords));
            return this;
        }

        if (!TextUtils.isEmpty(getOldPassword())
                && getNewPassword().equals(getOldPassword())) {
            setValid(false);
            setMsg(context.getString(R.string.change_password_old_equal));
            return this;
        }

        if (!checkPasswordHules()) {
            setValid(false);
            String msg = TextUtils.isEmpty(getOldPassword()) ? context.getString(R.string.change_password_invalid_register) : context.getString(R.string.change_password_invalid);
            setMsg(msg);
            return this;
        }

        setValid(true);
        return this;
    }

    private boolean checkPasswordHules() {
        if (!checkIfPassIsSequential(getNewPassword())) {
            return false;
        }

        if (!checkQtdeCaractersRepeat(getNewPassword())) {
            return false;
        }

        if (!checkCaracterGroup(getNewPassword())) {
            return false;
        }

        return true;
    }

    private boolean checkIfPassIsSequential(String newPassword) {
        for (int i = 0; i < newPassword.length() - 1; i++) {
            int current = Integer.parseInt(String.valueOf(newPassword.charAt(i)));
            int next = Integer.parseInt(String.valueOf(newPassword.charAt(i + 1)));

            int currentMin = current - 1;
            current++;
            if (current != next
                    && currentMin != next) {
                return true;
            }
        }
        return false;
    }

    private boolean checkQtdeCaractersRepeat(String newPassword) {
        int passSize = newPassword.length() - 1;
        for (int i = 0; i < passSize; i++) {
            int current = Integer.parseInt(String.valueOf(newPassword.charAt(i)));

            int qtdeSeq = 1;
            boolean itensSeq = true;
            for (int j = i + 1; j <= passSize; j++) {
                int next = Integer.parseInt(String.valueOf(newPassword.charAt(j)));

                if (next == current) {
                    qtdeSeq++;
                } else {
                    itensSeq = false;
                }

                if (qtdeSeq == QTDE_CHARACTERS_SEQUENTIAL
                        && itensSeq) {
                    return false;
                }

                if (qtdeSeq == QTDE_CHARACTERS_REPEAT) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkCaracterGroup(String newPassword) {
        int firtGroup = Integer.parseInt(newPassword.substring(0, 1));
        int secondGroup = Integer.parseInt(newPassword.substring(2, 3));
        int lastGroup = Integer.parseInt(newPassword.substring(4, 5));

        if (firtGroup == secondGroup
                && firtGroup == lastGroup) {
            return false;
        }

        return true;
    }
}
