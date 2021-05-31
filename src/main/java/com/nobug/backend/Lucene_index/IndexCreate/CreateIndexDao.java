package com.nobug.backend.Lucene_index.IndexCreate;

import java.sql.SQLException;

public interface CreateIndexDao {
    public void createSourceIndex() throws ClassNotFoundException, SQLException;
}
