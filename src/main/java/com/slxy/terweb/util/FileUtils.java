package com.slxy.terweb.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program: TeacherWeb
 * @description: 文件操作工具类
 * @author: Mr.Jiang
 * @create: 2019-05-04 13:05
 **/

public class FileUtils {

    /**
     * 文件打包zip
     * @param zipname
     * @param files
     * @return
     * @throws Exception
     */
    public static String doZIP(String zipname, File[] files) throws Exception{
        //doZIP(命名的打包文件名，传递过来的File数组)
        byte[] buffer = new byte[1024];
        String strZipName = zipname;
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
        for(int i=0;i<files.length;i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry(files[i].getName()));
            int len;
            //读入需要下载的文件的内容，打包到zip文件
            while((len = fis.read(buffer))>0) {
                out.write(buffer,0,len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
        return strZipName;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }

        return new FileOutputStream(file, append);
    }
}
