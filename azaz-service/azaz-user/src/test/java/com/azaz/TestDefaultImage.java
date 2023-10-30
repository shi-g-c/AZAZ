package com.azaz;

import com.azaz.utils.QiniuOssUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 测试默认头像
 */
public class TestDefaultImage {

    @Test
    public void test01() throws IOException {
        //1. 创建头像文件夹
        File files = new File("E:\\下载\\avatar");
        //2. 获取文件夹下所有文件
        File[] listFiles = files.listFiles();
        //3. 遍历所有文件
        for (int i = 0; i < listFiles.length; i++) {
            File file = listFiles[i];
            //4. 获取文件名
            String fileName = file.getName();
            String newName = "azaz_default_image_" + fileName;
            //7. 把file转换成byte[]
            byte[] bFile = Files.readAllBytes(file.toPath());
            //8. 上传到七牛云
            String upload = QiniuOssUtil.upload(bFile, newName);
            System.out.println("public static final String DEFAULT_USER_IMAGE_" + i + " = \"" + upload + "\";");
        }
    }
}
