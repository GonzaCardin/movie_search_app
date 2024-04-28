package com.educacionit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

public class FileManager {
    public byte[] readImage(String path) throws IOException {
        File imageFile = new File(path);
        FileInputStream in = null;
        try  {
            in = new FileInputStream(imageFile);
            byte[] imageBytes = new byte[(int) imageFile.length()];
            in.read(imageBytes);
            return imageBytes;
        } catch (FileNotFoundException e) {
            throw new FileSystemNotFoundException("The image was not found in the specified path: " + path);
        }finally{
            if(in != null){
                in.close();
            }
        }
    }
    public boolean fileExists(String path){
        File image = new File(path);
        return image.exists();
    }
}
