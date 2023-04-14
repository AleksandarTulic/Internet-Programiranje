package com.example.eshop.services.utilities;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private static final String FILE_SYSTEM_STORAGE = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "ESHOP" + File.separator + "users";

    static {
        File f = new File(FILE_SYSTEM_STORAGE);
        FileService.createFolders(f.getPath());
    }

    public boolean saveFile(String username, MultipartFile file){
        File f = new File(FILE_SYSTEM_STORAGE + File.separator + username);
        FileService.createFolders(f.getPath());

        boolean flag = false;

        try{
            InputStream initialStream = file.getInputStream();

            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File(f.getPath() + File.separator + file.getOriginalFilename());

            targetFile.createNewFile();
            try(OutputStream out = new FileOutputStream(targetFile)){
                out.write(buffer);
                flag = true;
            }

            initialStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    public byte[] getFile(String path){
        File f = new File(FILE_SYSTEM_STORAGE + File.separator + path);

        if (f.exists()){
            try {
                InputStream tStream = new FileInputStream(f);
                byte[] bytes = StreamUtils.copyToByteArray(tStream);

                return bytes;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deleteAllContent(String path){
        File f = new File(FILE_SYSTEM_STORAGE + File.separator + path);

        if (f.exists()){
            File []arr = f.listFiles();
            for (File i : arr) {
                System.out.println(i.getPath());
                if (i.exists())
                    i.delete();
            }
        }
    }

    private static void createFolders(String path){
        File f = new File(path);
        if (!f.exists()) {
            File tmp = f.getParentFile();

            List<String> arr = new ArrayList<>();
            arr.add(f.getPath());
            while(!tmp.exists()) {
                arr.add(tmp.getPath());
                tmp = tmp.getParentFile();
            }


            for (int i=arr.size()-1;i>=0;i--) {
                tmp = new File(arr.get(i));
                tmp.mkdir();
            }
        }
    }
}
