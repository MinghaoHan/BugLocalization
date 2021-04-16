import BugLocator.BugLocator;
import PreProcess.ReadFromFile;
import jxl.write.WriteException;
import lib.BugReporter;
import lib.Sources;

import java.io.IOException;
import java.sql.SQLException;

public class Startup {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, WriteException {
        ReadFromFile.getResult();
        Sources ss = new Sources("data/class_preprocessed2/");
        BugReporter bugs = new BugReporter("data/report_preprocessed2/");

//        CreateIndexDaoImpl cidi = new CreateIndexDaoImpl(ss);
//        cidi.createSourceIndex();
        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList2.xls");
        bugLocator.bugLocator();
    }
}
