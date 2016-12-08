package com.hdmb.ireader;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.hdmb.ireader.ui.activity.CrashActivity;
import com.hdmb.ireader.utils.CrashHandler;
import com.hdmb.ireader.utils.Log;
import com.hdmb.ireader.utils.Utils;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 11:19
 * 文件    TbReader
 * 描述
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static final String APATCH_PATH = "/out.apatch";
    private static final String DIR = "apatch";//补丁文件夹
    public boolean httpLog = true;

    public static String VSERSION_NAME = "1.0";
    public static String PATCH_VERSION_NAME;

    private static MyApplication instance;

    public static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }

    /**
     * patch manager
     */
    private static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(this);

        VSERSION_NAME = Utils.getVersionName(this);
        PATCH_VERSION_NAME = Utils.getPatchVersionName(this);
        initializeAndFix();
    }

    public PatchManager getPatchManager() {
        return mPatchManager;
    }

    /**
     * 初始化AndFix
     */
    private void initializeAndFix() {
        // initialize
        mPatchManager = new PatchManager(this);
        mPatchManager.init(VSERSION_NAME);
        Log.e(Log.TAG, "inited.");

        // load patch
//        mPatchManager.loadPatch();
//        Log.e(Log.TAG, "apatch loaded.");
        // add patch at runtime
//        try {
//            // .apatch file path
//            String patchFileString = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + APATCH_PATH;
//            mPatchManager.addPatch(patchFileString);
//            Log.e("apatch:" + patchFileString + " added.");
//            //这里我加了个方法，复制加载补丁成功后，删除sdcard的补丁，避免每次进入程序都重新加载一次
//            File f = new File(this.getFilesDir(), DIR + APATCH_PATH);
//            if (f.exists()) {
//                boolean result = new File(patchFileString).delete();
//                if (!result)
//                    Log.e(patchFileString + " delete fail");
//            }
//        } catch (IOException e) {
//            Log.e("", e);
//        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        CrashActivity.start(this, throwable);
        System.exit(1);
    }
}
