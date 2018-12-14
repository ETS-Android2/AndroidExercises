package com.example.learning.basicmaths;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FileManage {

    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private File file;
    private String fileName;
    private int size = 0;

    private void setFileName() {
        fileName = "results";
    }

    private boolean checkFile(Context context) {
        setFileName();
        file = context.getFileStreamPath(fileName);
        if (file.exists()) return true;
        else return false;
    }

    private void createFile(Context context) {
        try {
            fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFile(Context context) {
        try {
            //fileIn = context.openFileInput(fileName);
            //fileOut = new FileOutputStream(fileName, true);
            fileOut = context.openFileOutput(fileName,Context.MODE_APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void deleteFile(Context context){
        try {
            setFileName();
            context.deleteFile(fileName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setSize(Context context){
        setFileName();
        try{
            String data = null;
            fileIn = context.openFileInput(fileName);
            InputStreamReader iSR = new InputStreamReader(fileIn);
            BufferedReader bR = new BufferedReader(iSR);
            size = 0;
            while((data = bR.readLine()) != null ) size++;
            fileIn.close();iSR.close();bR.close();
        }catch (FileNotFoundException e){
            Log.d(FileManage.class.getName(), e.getMessage());
        }catch (IOException e){
            Log.d(FileManage.class.getName(), e.getMessage());
        }
    }

    protected void writeF(Context context, String [] data) {
        setFileName();
        if (!checkFile(context)) createFile(context);
        openFile(context);
        try {
            setSize(context);
            if(size == 50) {
                String [] pFile = getDataFile(context);
                for(int i = 0; i < pFile.length; i++){
                    pFile[i] = pFile[i].concat("\n");
                }
                //delete file
                deleteFile(context);
                //create file
                if (!checkFile(context)) createFile(context);
                openFile(context);
                //i = 5 because it would be the next "data user"
                //each data user has 5 data lines
                for(int i = 5; i < pFile.length; i++){ //set all the data except the first one
                    fileOut.write(pFile[i].getBytes());
                }
            }
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].concat("\n");
                fileOut.write(data[i].getBytes());
            }
            //String sn = Integer.toString(n);
            //sn = sn.concat(" \n");
            //fileOut.write(sn.getBytes());
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String [] getDataFile(Context context){
        setFileName();
        String [] data = new String[0]; //to get at least empty results
        try{
            fileIn = context.openFileInput(fileName);
            InputStreamReader iSR = new InputStreamReader(fileIn);
            BufferedReader bR = new BufferedReader(iSR);
            int i = 0;
            data = new String[50];
            while((data[i] = bR.readLine()) != null && i < 49) i++;
            this.size = i;
            fileIn.close();
            iSR.close();
            bR.close();
        }catch (FileNotFoundException e){
            Log.d(FileManage.class.getName(), e.getMessage());
        }catch (IOException e){
            Log.d(FileManage.class.getName(), e.getMessage());
        }
        return data;
    }

}