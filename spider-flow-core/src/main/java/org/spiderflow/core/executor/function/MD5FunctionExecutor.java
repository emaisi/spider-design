package org.spiderflow.core.executor.function;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Comment("MD5常用方法")
public class MD5FunctionExecutor implements FunctionExecutor {

    @Override
    public String getFunctionPrefix() {
        return "md5";
    }

    @Comment("md5加密")
    @Example("${md5.string(resp.html)}")
    public static String string(String str){
        return   SecureUtil.md5(str);
//        return DigestUtils.md5Hex(str);
    }

    @Comment("md5加密")
    @Example("${md5.string(resp.bytes)}")
    public static String string(byte[] bytes){
//        return DigestUtils.md5Hex(bytes);
        return   SecureUtil.md5(StrUtil.utf8Str(bytes));

    }

    @Comment("md5加密")
    @Example("${md5.string(resp.stream)}")
    public static String string(InputStream stream) throws IOException {
        return SecureUtil.md5(stream);
    }

    @Comment("md5加密")
    @Example("${md5.bytes(resp.html)}")
    public static byte[] bytes(String str){
        return SecureUtil.md5(str).getBytes();
    }

    @Comment("md5加密")
    @Example("${md5.bytes(resp.bytes)}")
    public static byte[] bytes(byte[] bytes){
        return SecureUtil.md5(StrUtil.utf8Str(bytes)).getBytes();
    }

    @Comment("md5加密")
    @Example("${md5.bytes(resp.stream)}")
    public static byte[] bytes(InputStream stream) throws IOException {
        return SecureUtil.md5(stream).getBytes();
    }
}
