package com.hdmb.ireader.api.progress;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:19
 * 文件    TbReader
 * 描述
 */
public class ProgressHandler {

    protected void sendMessage(Progress progress) {
    }

    protected void handleMessage(Message message) {
    }

    protected void onProgress(long progress, long total, boolean done) {
    }

    protected static class ResponseHandler extends Handler {

        private ProgressHandler mProgressHandler;

        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }
}
