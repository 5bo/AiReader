package com.hdmb.ireader.txtreader.bean;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 15:41
 * 文件    TbReader
 * 描述
 */
public class TxtError {
    private int txtErrorCode;
    private String txtErrorMsg;

    public int getTxtErrorCode() {
        return txtErrorCode;
    }

    public void setTxtErrorCode(int txtErrorCode) {
        this.txtErrorCode = txtErrorCode;
    }

    public String getTxtErrorMsg() {
        return txtErrorMsg;
    }

    public void setTxtErrorMsg(String txtErrorMsg) {
        this.txtErrorMsg = txtErrorMsg;
    }

    @Override
    public String toString() {
        return "TxtError{" +
                "txtErrorCode=" + txtErrorCode +
                ", txtErrorMsg=" + txtErrorMsg +
                '}';
    }
}
