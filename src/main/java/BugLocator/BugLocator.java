package BugLocator;

import Lucene_index.Pojo.SourceFile;
import Models.VectorSpaceModel;
import lib.BugReporter;
import lib.Sources;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BugLocator {
    BugReporter bugs;
    Sources ss;

    public BugLocator(BugReporter bugs, Sources ss) {
        this.bugs = bugs;
        this.ss = ss;
    }

    public void bugLocator(){
        File file = new File(bugs.getPath());
        File[] fs = file.listFiles();
        List<String> textfiles = null;
        for(File f:fs){
            if(!f.isDirectory()) {
                textfiles.add(f.getAbsolutePath());
            }
        }
    }
}
