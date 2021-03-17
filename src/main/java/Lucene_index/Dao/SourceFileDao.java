package Lucene_index.Dao;

import Lucene_index.Pojo.SourceFile;

import java.util.List;

public interface SourceFileDao {
    /**
     * 查询所有的源代码文件信息
     * return List<SourceFile>
     */

    public List<SourceFile> querySFList() throws Exception;
}
