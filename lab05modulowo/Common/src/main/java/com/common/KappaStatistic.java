package com.common;

import ex.api.AnalysisService;
import ex.api.AnalysisException;
import ex.api.DataSet;

public interface KappaStatistic extends AnalysisService {

    String getName();
    void setOptions(String[] options);
    void submit(DataSet dataSet);
    DataSet retrieve(boolean clearAfterRetrieval);


    double analyze(DataSet data) throws AnalysisException;
}
