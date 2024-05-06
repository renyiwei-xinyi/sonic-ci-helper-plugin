package io.jenkins.plugins.sonic.utils;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.okhttps.MsgConvertor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class GsonMsgConvertor implements MsgConvertor {

    private final Gson gson;

    public GsonMsgConvertor() {
        this.gson = new Gson();
    }

    @Override
    public Mapper toMapper(InputStream inputStream, Charset charset) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, charset));
        return gson.fromJson(reader, type);
    }

    @Override
    public Mapper toMapper(String in) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        return gson.fromJson(in, type);
    }

    @Override
    public Array toArray(InputStream inputStream, Charset charset) {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, charset));
        return gson.fromJson(reader, Array.class);
    }

    @Override
    public Array toArray(String in) {
        return gson.fromJson(in, Array.class);
    }

    @Override
    public byte[] serialize(Object o, Charset charset) {
        // 首先，使用Gson将对象转换为JSON字符串
        String json = gson.toJson(o);
        // 然后，将JSON字符串转换为字节数组，使用指定的字符集进行编码
        return json.getBytes(charset);
    }

    @Override
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T toBean(Type type, InputStream inputStream, Charset charset) {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, charset));
        return gson.fromJson(reader, type);
    }

    @Override
    public <T> T toBean(Type type, String in) {
        return gson.fromJson(in, type);
    }

    @Override
    public <T> List<T> toList(Class<T> aClass, InputStream inputStream, Charset charset) {
        Type type = TypeToken.getParameterized(List.class, aClass).getType();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, charset));
        return gson.fromJson(reader, type);
    }

    @Override
    public <T> List<T> toList(Class<T> type, String in) {
        // 由于Gson处理泛型集合时的限制，需要通过TypeToken来获取具体的集合类型
        Type ty = TypeToken.getParameterized(List.class, type).getType();
        return gson.fromJson(in, ty);
    }

    @Override
    public String mediaType() {
        return "application/json";
    }
}
