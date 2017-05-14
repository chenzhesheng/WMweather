package com.example.jikeu.base.utils;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by ChenZheSheng on 2016/3/5.
 * 说明：录音工具类
 */
public class MedioRecorderUtils {
    private static MediaRecorder mediaRecorder;
    private static File recAudioFile;
    private static File dir;

    /**
     * 开始录制
     */
    public static void record() {
        mediaRecorder = new MediaRecorder();
        // 录音源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        // 位率
        mediaRecorder.setAudioEncodingBitRate(256);
        // 采样率
        mediaRecorder.setAudioSamplingRate(112025);
        // 编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        dir = new File("/mnt/sdcard", "yanxing");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        recAudioFile = new File(dir, "yanxing.mp3");

        mediaRecorder.setOutputFile(recAudioFile.getAbsolutePath());
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "开始录音");
    }

    public static File Stop() {
        if (recAudioFile != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
        }
        return recAudioFile;
    }


    public static void delete() {
        if (recAudioFile != null) {
            recAudioFile.delete();
        }
    }
}
