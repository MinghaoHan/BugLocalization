import BugLocator.BugLocator;
import Lucene_index.IndexCreate.CreateIndexDaoImpl;
import lib.BugReporter;
import lib.Sources;

import java.sql.SQLException;

public class Startup {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Sources ss = new Sources("data/class_preprocessed/");
        BugReporter bugs = new BugReporter("data/report_preprocessed/");
        CreateIndexDaoImpl cidi = new CreateIndexDaoImpl(ss);
        cidi.createSourceIndex();
        BugLocator bugLocator = new BugLocator(bugs,ss);
    }
}
