package com.example.utils;

import com.example.models.HistoryConversion;
import com.example.repo.HistoryConversionRepository;

import java.util.Iterator;

public class HistoryConversionUtils {

    public static Iterable<HistoryConversion> search(HistoryConversionRepository historyConversionRepository, String keyword){
        Iterable<HistoryConversion> historyConversionIterable = historyConversionRepository.findAll();
        if(!keyword.isEmpty()){
            Iterator<HistoryConversion> historyConversionIterator = historyConversionIterable.iterator();
            while(historyConversionIterator.hasNext()){
                HistoryConversion historyConversion = historyConversionIterator.next();
                String value = historyConversion.getHistoryRec();
                if(!value.contains(keyword)){
                    historyConversionIterator.remove();
                }
            }
        }
       return historyConversionIterable;
    }
}
