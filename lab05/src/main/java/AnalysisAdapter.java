import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class AnalysisAdapter implements AnalysisService {
    private AnalysisService delegate;
    private DataSet resultCache;

    public AnalysisAdapter(AnalysisService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setOptions(String[] options) {
        try {
            delegate.setOptions(options);
        } catch (AnalysisException e) {
            throw new RuntimeException("Błąd przy ustawianiu opcji: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public void submit(DataSet ds) {
        try {
            delegate.submit(ds);
        } catch (AnalysisException e) {
            throw new RuntimeException("Błąd przy przekazywaniu danych do analizy: " + e.getMessage());
        }
    }

    @Override
    public DataSet retrieve(boolean clear) {
        try {
            resultCache = delegate.retrieve(clear);
            return resultCache;
        } catch (AnalysisException e) {
            throw new RuntimeException("Błąd przy pobieraniu wyników: " + e.getMessage());
        }
    }

    public double analyze() {
        if (resultCache == null) {
            throw new RuntimeException("Brak danych do analizy.");
        }

        if (delegate instanceof AccuracyStatistic) {
            return ((AccuracyStatistic) delegate).analyze(resultCache);
        } else if (delegate instanceof KappaStatistic) {
            return ((KappaStatistic) delegate).analyze(resultCache);
        } else {
            throw new RuntimeException("Nieznany typ algorytmu analizy.");
        }
    }
}

