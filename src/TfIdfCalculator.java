import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdfCalculator {

    private List<String> allTerms;
    private List<Map<String, Double>> tfVectors;
    private Map<String, Double> idfValues;
    private int totalDocuments;

    public TfIdfCalculator(List<ArrayList<String>> documents) {
        allTerms = getAllTerms(documents);
        totalDocuments = documents.size();
        tfVectors = new ArrayList<Map<String, Double>>(totalDocuments);
        idfValues = new HashMap<String, Double>();
        for (List<String> document : documents) {
            Map<String, Double> tfVector = getTfVector(document);
            tfVectors.add(tfVector);
        }
        for (String term : allTerms) {
            int documentsWithTerm = 0;
            for (Map<String, Double> tfVector : tfVectors) {
                if (tfVector.containsKey(term)) {
                    documentsWithTerm++;
                }
            }
            idfValues.put(term, Math.log((double) totalDocuments / documentsWithTerm));
        }
    }

    private List<String> getAllTerms(List<ArrayList<String>> documents) {
        List<String> allTerms = new ArrayList<String>();
        for (List<String> document : documents) {
            for (String term : document) {
                if (!allTerms.contains(term)) {
                    allTerms.add(term);
                }
            }
        }
        return allTerms;
    }

    private Map<String, Double> getTfVector(List<String> document) {
        Map<String, Double> tfVector = new HashMap<String, Double>();
        int documentLength = document.size();
        for (String term : document) {
            Double value = tfVector.get(term);
            if (value == null) {
                tfVector.put(term, 1.0 / documentLength);
            } else {
                tfVector.put(term, value + 1.0 / documentLength);
            }
        }
        return tfVector;
    }

    public double getTfIdf(ArrayList<String> document, String term) {
        double tf = getTf(document, term);
        double idf = getIdf(term);
        return tf * idf;
    }

    public double getTf(ArrayList<String> document, String term) {
        double result = 0.0;
        int documentLength = document.size();
        for (String word : document) {
            if (word.equalsIgnoreCase(term)) {
                result++;
            }
        }
        return result / documentLength;
    }

    public double getIdf(String term) {
        Double idf = idfValues.get(term);
        if (idf == null) {
            return 0.0;
        } else {
            return idf;
        }
    }
}