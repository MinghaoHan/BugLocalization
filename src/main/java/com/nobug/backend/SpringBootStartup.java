package com.nobug.backend;

import com.nobug.backend.BugLocator.BugLocator;
import com.nobug.backend.lib.BugReporter;
import com.nobug.backend.lib.Sources;
import javafx.util.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import jxl.write.WriteException;

@SpringBootApplication
public class SpringBootStartup
{
    public static void main(String[] args) throws SQLException, IOException, WriteException {

        Sources ss = new Sources("data/class_preprocessed2/");
        BugReporter bugs = new BugReporter("data/report_preprocessed2");

        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList3.xls",0);
//        bugLocator.bugLocator();
        SpringApplication.run(SpringBootStartup.class, args);
    }
}
