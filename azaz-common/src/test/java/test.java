import com.azaz.utils.QiniuOssUtil;

import java.io.UnsupportedEncodingException;

public class test {
    public static void main(String []args) throws UnsupportedEncodingException {
        System.out.println(QiniuOssUtil.upload("Hello World!!!".getBytes("utf-8"), "damn.txt"));
    }
}
