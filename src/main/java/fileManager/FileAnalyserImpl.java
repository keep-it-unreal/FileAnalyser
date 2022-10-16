package fileManager;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class FileAnalyserImpl implements FileAnalyser {
    File file;
    private String fileName;
    private int rowsCount, lettersCount;
    private int topNLetters = 3;
    Map<Character, Integer> symbolsStatistics;

    public FileAnalyserImpl(String x) throws IOException{
        this.file = new File(x);
        fileName = file.getName();
        getStatistic();
    }

    public void getStatistic() throws IOException {
        FileReader file = new FileReader(this.file);
        int ch = file.read();
        char myChar;
        if(ch == -1) {
            return;
        }
        rowsCount = 1;
        symbolsStatistics = new HashMap<>();
        while (ch != -1) {
            myChar = (char)ch;
            if(myChar == '\n'){
                ++rowsCount;
            } else {++lettersCount;}
            if(Character.isDigit(myChar)){
                if(symbolsStatistics.containsKey(myChar)){
                    symbolsStatistics.merge(myChar, 1, Integer::sum);
                }else {symbolsStatistics.put(myChar, 1);}
            } else if (Character.isLetter(myChar)) {
                if(symbolsStatistics.containsKey(myChar)){
                    symbolsStatistics.merge(myChar, 1, Integer::sum);
                }else {symbolsStatistics.put(myChar, 1);}
            }
            ch = file.read();
        }
    }

    public void changeTopNLetters(int topNLetters){
        this.topNLetters = topNLetters;
    }
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getRowsCount() {
        return rowsCount;
    }

    @Override
    public int getLettersCount() {
        return lettersCount;
    }

    @Override
    public Map<Character, Integer> getSymbolsStatistics() {
        return symbolsStatistics;
    }

    @Override
    public List<Character> getTopNPopularSymbols(int n) {
        PriorityQueue<Map.Entry<Character, Integer>> prQueue = new PriorityQueue<>(new MyComparator());
        List<Character> topNPopular = new ArrayList<>();
        for (Map.Entry x: symbolsStatistics.entrySet()) {
            prQueue.add(x);
            if (prQueue.size() > n) {
                prQueue.poll();
            }
        }
        for(int i = 0; i < n; ++i){
            topNPopular.add(prQueue.poll().getKey());
        }
        Collections.reverse(topNPopular);
        return topNPopular;
    }

    @Override
    public void saveSummary(String filePath) throws IOException {
        FileWriter file = new FileWriter(filePath);
        file.write("fileName: " + getFileName() + "\n" +
                "rowsCount: " + getRowsCount() + "\n" +
                "totalSymbols: " + getLettersCount() + "\n" +
                "symbolsStatistics: " + getSymbolsStatistics() + "\n" +
                "top" + this.topNLetters + "PopularSymbols: " + getTopNPopularSymbols(this.topNLetters));
        file.close();
    }
    static class MyComparator implements Comparator<Map.Entry<Character, Integer>> {
        @Override
        public int compare(Map.Entry<Character, Integer> firstItem,
                           Map.Entry<Character, Integer> secondItem) {
            if (firstItem.getValue() > secondItem.getValue()) {
                return 1;
            } else if (firstItem.getValue() < secondItem.getValue()) {
                return -1;
            } else {
                if(firstItem.getKey() > secondItem.getKey()){
                    return  1;
                } else { return -1;}
            }
        }
    }
}


