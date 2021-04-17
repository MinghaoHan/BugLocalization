import BugLocator.BugLocator;
import Lucene_index.IndexCreate.CreateIndexDaoImpl;
import Preprocess.ReadFromFile;
import jxl.write.WriteException;
import lib.BugReporter;
import lib.Sources;

import java.io.IOException;
import java.sql.SQLException;

public class Startup {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, WriteException {
//        ReadFromFile.getResult();
        Sources ss = new Sources("data/class_preprocessed2/");
        BugReporter bugs = new BugReporter("data/report_preprocessed3");

//        CreateIndexDaoImpl cidi = new CreateIndexDaoImpl(ss);
//        cidi.createSourceIndex();
        BugLocator bugLocator = new BugLocator(bugs,ss,"data/RankList3.xls",0);
        bugLocator.bugLocator();
    }
}
