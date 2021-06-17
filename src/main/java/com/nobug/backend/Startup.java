package com.nobug.backend;

import com.nobug.backend.BugLocator.BugLocator;
import com.nobug.backend.Lucene_index.IndexCreate.CreateIndexDaoImpl;
import com.nobug.backend.Preprocess.ReadFromFile;
import jxl.write.WriteException;
import com.nobug.backend.lib.BugReporter;
import com.nobug.backend.lib.Sources;

import java.io.IOException;
import java.sql.SQLException;

public class Startup {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, WriteException {
        /** 预处理 **/
//        new ReadFromFile().getResult();

        /** 修改源代码 和 bug报告 的地址 **/
        Sources ss = new Sources("data/class_preprocessed3/");
        BugReporter bugs = new BugReporter("data/report_preprocessed3");

        /** 源代码写入数据库，若未运行，即数据库中现有结果 **/
//        CreateIndexDaoImpl cidi = new CreateIndexDaoImpl(ss);
//        cidi.createSourceIndex();

        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList2.xls",0);
        bugLocator.bugLocator();
    }
}