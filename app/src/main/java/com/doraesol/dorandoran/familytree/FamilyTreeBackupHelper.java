package com.doraesol.dorandoran.familytree;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YOONGOO on 2017-05-21.
 */

public class FamilyTreeBackupHelper {
    final String LOG_TAG = FamilyTreeBackupHelper.class.getSimpleName();
    Context context;

    public FamilyTreeBackupHelper(Context paramContext){
        this.context = paramContext;
    }

    public void getFileNameList(){
        String dirPath = context.getFilesDir().getAbsolutePath() + "/FamilyTreeBackUp/";
        File backupDir = new File(dirPath);
        File[] fileList = backupDir.listFiles();

        registerToMediaScanner(backupDir);

        if(backupDir.exists()) {

            for(int i=0; i<fileList.length; i++) {
                registerToMediaScanner(fileList[i]);
                Log.d(LOG_TAG, fileList[i].getPath());
            }

        }
        else {
            Log.d(LOG_TAG, "디렉토리를 먼저 생성해야 합니다.");


        }
    }

    public boolean saveBackupFile(String paramBackupData){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateTimeString = sdfNow.format(date);
        String dirPath = context.getFilesDir().getAbsolutePath() + "/FamilyTreeBackUp5/";
        String backupFilePath = dirPath + currentDateTimeString.trim() +".bak";
        File backupDir = new File(dirPath);


        if(!backupDir.exists()) {
            if (backupDir.mkdirs()) {
                Log.d(LOG_TAG, "디렉토리 생성 성공");
            } else {
                Log.d(LOG_TAG, "디렉토리 생성 실패");
            }
        }
        else
        {
            Log.d(LOG_TAG, "디렉토리가 이미 존재합니다.");
        }


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(backupFilePath , true));
            writer.write(paramBackupData);
            writer.write("\n");
            writer.flush();
            writer.close();

            Log.d(LOG_TAG, "filepath : " + backupFilePath);

            return true;
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString());

            return false;
        }
    }

    private void registerToMediaScanner( File newFile ){
        MediaScannerConnection.scanFile(context, new String[]{ newFile.getPath() },null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                // TODO
            }
        });
    }


}
