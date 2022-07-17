import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class test_1 {
    @Test
    public void test1(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "X4Zf91xE9S0qG88e62H3TgPunkaTF4oLR0oDwvZr";
        String secretKey = "MeSAbm5ZPN6zajQfGc3j5Sj-_K-UhWSI4Yf7DFf0";
        String bucket = "itcasthealth-3";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\暑期项目\\资料-传智健康项目\\day04\\资源\\图片资源\\t1.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "b1.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @Test
    public void Test2(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        String accessKey = "X4Zf91xE9S0qG88e62H3TgPunkaTF4oLR0oDwvZr";
        String secretKey = "MeSAbm5ZPN6zajQfGc3j5Sj-_K-UhWSI4Yf7DFf0";

        String bucket = "itcasthealth-3";
        String key = "FuM1Sa5TtL_ekLsdkYWcf5pyjKGu";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

}
