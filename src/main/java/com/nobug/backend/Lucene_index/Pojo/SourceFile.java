package com.nobug.backend.Lucene_index.Pojo;

import java.util.Map;

public class SourceFile {

    // 源代码文件id
    private String id;

    //源代码文件名
    private String name;

    //源代码文件地址
    private String path;

    //源代码文件 单词和tf-idf值
    private Map<String,String> word;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String>  getWord() {
        return word;
    }

    public void setWord(Map<String, String> word) {
        this.word = word;
    }
}
