package com.example.controllers;

import com.example.models.ExchangeRate;
import com.example.models.HistoryConversion;
import com.example.repo.ExchangeRatesRepository;
import com.example.repo.HistoryConversionRepository;
import com.example.utils.ExchangeRateUtils;
import com.example.utils.HistoryConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/*
    - Сделать сохранение валют в полях после обновления страницы
    - Сделать поиск через sql запрос
 */

@Controller
public class HomeController {

    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;

    @Autowired
    private HistoryConversionRepository historyConversionRepository;

    @GetMapping("/")
    public String home(Model model) {

        if(exchangeRatesRepository.count() == 0){
            ExchangeRateUtils.addTables(exchangeRatesRepository);
        }

        Iterable<ExchangeRate> exchangeRatesIterable = exchangeRatesRepository.findAll();
        model.addAttribute("exRatList",exchangeRatesIterable);

        Iterable<HistoryConversion> historyConversionIterable = historyConversionRepository.findAll();
        model.addAttribute("hConList",historyConversionIterable);

        model.addAttribute("nameOutput"," = Вывод");
        return "home";
    }

    @PostMapping(value="/", params = "action")
    public String pushButton(@RequestParam String concurrency1,
                             @RequestParam String concurrency2,
                             @RequestParam String input,
                             @RequestParam String action,
                             @RequestParam String keyword,
                             Model model){

        Iterable<ExchangeRate> exchangeRatesIterable = exchangeRatesRepository.findAll();
        model.addAttribute("exRatList",exchangeRatesIterable);

        if(action.equals("convert")){
            if(!input.isEmpty() && !concurrency1.equals(concurrency2)){
                if(ExchangeRateUtils.getActualDate().after(new Date())){
                    ExchangeRateUtils.updateTable(exchangeRatesRepository);
                }
                String result = ExchangeRateUtils.converter(concurrency1, concurrency2, input, exchangeRatesRepository);
                model.addAttribute("nameOutput", result);
                historyConversionRepository.save(new HistoryConversion(ExchangeRateUtils.getDateTimeNow(), input, concurrency1, result, concurrency2));
            }
            model.addAttribute("hConList",historyConversionRepository.findAll());
        }

        if(action.equals("clean")){
            model.addAttribute("nameOutput"," = Вывод");
            historyConversionRepository.deleteAll();
        }

        if(action.equals("search")){
            model.addAttribute("nameOutput"," = Вывод");
            model.addAttribute("hConList", HistoryConversionUtils.search(historyConversionRepository, keyword));
        }
        return "home";
    }
}
