package com.dlut.justeda.classnote.justpublic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.util.Log;
public class Zip {
	
	private static boolean flag;
	private Zip(){};
	
	/*
	public static boolean toZip(String path){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd-HH");
        String fileName = format.format(date); 
		boolean flag = false;  
        File sourceFile = new File(path);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            Log.e("zip","not exist");
        }else{  
            try {  
                File zipFile = new File(path + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                	Log.e("zip","already exist");
                    return false;
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        Log.e("zip","dir null");
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //caeate zip ,put in 
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //read all ,put in zip
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                try {  //remember to close stream
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
	}
	*/
	/** 
     * ����ZIP�ļ� 
     * @param sourcePath �ļ����ļ���·�� 
     * @param zipPath ���ɵ�zip�ļ�����·���������ļ����� 
     */  
    public static boolean createZip(String sourcePath) { 
    	flag = false;  
    	Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd-HH");
        String fileName = format.format(date); 
    	String zipPath = sourcePath + "/" + fileName +".zip";
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        try {  
            fos = new FileOutputStream(zipPath);  
            zos = new ZipOutputStream(fos);   
            writeZip(new File(sourcePath), "", zos);
            
        } catch (FileNotFoundException e) {  
            
        } finally {  
            try {  
                if (zos != null) {  
                    zos.close();  
                }  
            } catch (IOException e) {  
               
            }  
        }  
        return flag;
    }  
  
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {  
        if(file.exists()){  
            if(file.isDirectory()){//�����ļ���  
                parentPath+=file.getName()+File.separator;  
                File [] files=file.listFiles();  
                if(files.length != 0)  {  
                    for(File f:files){  
                        writeZip(f, parentPath, zos);  
                    }  
                }  
                else{       //��Ŀ¼�򴴽���ǰĿ¼  
                        try {  
                            zos.putNextEntry(new ZipEntry(parentPath));  
                        } catch (IOException e) {  
                            // TODO Auto-generated catch block  
                            e.printStackTrace();  
                        }  
                }  
            }else{  
                FileInputStream fis=null;  
                try {  
                    fis=new FileInputStream(file);  
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());  
                    zos.putNextEntry(ze);  
                    byte [] content=new byte[1024];  
                    int len;  
                    while((len=fis.read(content))!=-1){  
                        zos.write(content,0,len);  
                        zos.flush();  
                    }  
                    flag = true;
                } catch (FileNotFoundException e) {  
                  
                } catch (IOException e) {  
                   
                }finally{  
                    try {  
                        if(fis!=null){  
                            fis.close();  
                        }  
                    }catch(IOException e){  
                       
                    }  
                }  
            }  
        }  
    }   
}
