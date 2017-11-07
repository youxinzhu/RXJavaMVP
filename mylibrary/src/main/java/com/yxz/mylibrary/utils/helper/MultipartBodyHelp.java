package com.yxz.mylibrary.utils.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

/**
 * Created by yxz on 2017/10/26.
 */

public class MultipartBodyHelp {
    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param mediaType 文件类型
     */
    public static List<MultipartBody.Part> files2Part(String key, String[] filePaths, MediaType mediaType){
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths){
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(mediaType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),requestBody);
            parts.add(part);
        }
        return  parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key, String[] filePaths, MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

}
