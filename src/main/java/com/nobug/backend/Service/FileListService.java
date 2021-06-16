package com.nobug.backend.Service;

import com.nobug.backend.BugLocator.BugLocator;
import com.nobug.backend.lib.BugReporter;
import com.nobug.backend.lib.Sources;
import javafx.util.Pair;
import jxl.write.WriteException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class FileListService {
    public static Vector<Pair<String, Vector<Pair<String, Float>>>> getNameImpl() throws SQLException, IOException, WriteException {
        System.out.println("getting filelist");
        Sources ss = new Sources("data/class_preprocessed2/");
        BugReporter bugs = new BugReporter("data/report_preprocessed3");
        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList3.xls",0);
        return bugLocator.getFileList();
    }
}
