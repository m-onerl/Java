package com.common;

import ex.api.AnalysisService;
import ex.api.AnalysisException;
import ex.api.DataSet;

public interface AccuracyStatistic extends AnalysisService {

    void submit(DataSet dataSet);
    DataSet retrieve(boolean clearAfterRetrieval) throws AnalysisException;


    double analyze(DataSet data) throws AnalysisException;
}
