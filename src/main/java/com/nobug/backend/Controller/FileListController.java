package com.nobug.backend.Controller;

import com.nobug.backend.BugLocator.BugLocator;
import com.nobug.backend.lib.BugReporter;
import com.nobug.backend.lib.Sources;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jxl.write.WriteException;

@RestController
public class FileListController {

    @RequestMapping("/getFileList")
    public Vector<Pair<String, List<Map.Entry<String, Double>>>> getName() throws SQLException, IOException, WriteException {
        Sources ss = new Sources("data/class_preprocessed2/");
        BugReporter bugs = new BugReporter("data/report_preprocessed3");
        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList3.xls",0);
        return bugLocator.getFileList();
    }

}
