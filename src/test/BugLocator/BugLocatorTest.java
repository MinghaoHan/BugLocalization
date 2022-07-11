package BugLocator;

import com.nobug.backend.Lucene_index.Pojo.SourceFile;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nobug.backend.BugLocator.BugLocator;


public class BugLocatorTest {

    @Test
    public void vsmTest(){
        File file = new File("data/class_preprocessed/");
        File[] bugFiles = file.listFiles();
        List<String> files = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for(File f:bugFiles){
            if(!f.isDirectory()) {
                files.add(f.getAbsolutePath());
                names.add(f.getName());
            }
        }

        //遍历每一个bug文件
        for(int i=0;i<files.size();i++) {
            String name = names.get(i);
            String path = files.get(i);

            System.out.println("-------------------------------------------------");
            System.out.println("BugFile Name:"+name);

            System.out.println("-------------------------------------------------");
        }
    }
}
