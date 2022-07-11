package com.nobug.backend.Controller;

import javafx.util.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nobug.backend.Service.FileListService;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Vector;
import jxl.write.WriteException;

@RestController
public class FileListController {

    @CrossOrigin
    @RequestMapping("/getFileList")
    public Vector<Pair<String, Vector<Pair<String, Float>>>> getName() throws SQLException, IOException, WriteException {
        return FileListService.getNameImpl();
    }

}
