package com.nobug.backend.lib;

import com.nobug.backend.Lucene_index.Pojo.SourceFile;

import java.util.List;

public class Sources {
    private String path;

    List<SourceFile> SF;

    public Sources(String path) {
        this.path = path;
    }

    public List<SourceFile> getSF() {
        return SF;
    }

    public void setSF(List<SourceFile> SF) {
        this.SF = SF;
    }

    public String getPath() {
        return path;
    }


}
