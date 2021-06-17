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
        new ReadFromFile().getResult();
        Sources ss = new Sources("data/class_preprocessed3/");
        BugReporter bugs = new BugReporter("data/report_preprocessed3");

//        CreateIndexDaoImpl cidi = new CreateIndexDaoImpl(ss);
//        cidi.createSourceIndex();
        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList3.xls",0);
        bugLocator.bugLocator();
    }
}