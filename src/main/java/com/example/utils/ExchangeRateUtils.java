package com.example.utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.*;

import com.example.models.ExchangeRate;
import com.example.repo.ExchangeRatesRepository;
import org.w3c.dom.*;


public class ExchangeRateUtils {

    public static void addTables(ExchangeRatesRepository exchangeRatesRepository) {

        exchangeRatesRepository.save(new ExchangeRate("Российский рубль", getActualDateString(),"1", "1"));

        NodeList elementsList = getElementAll().getElementsByTagName("Valute");

        for (int i = 0; i < elementsList.getLength(); i++) {
            Element element = (Element) elementsList.item(i);

            exchangeRatesRepository.save(new ExchangeRate(
                    getTagValueString(element,"Name"),
                    getActualDateString(),
                    getTagValueString(element,"Nominal"),
                    getTagValueString(element,"Value")));
        }
    }

    public static void updateTable(ExchangeRatesRepository exchangeRatesRepository){
        ExchangeRate exchangeRateRub = exchangeRatesRepository.findById("Российский рубль").orElseThrow(IllegalStateException::new);
        exchangeRateRub.setDate(getActualDateString());

        NodeList elementsList = getElementAll().getElementsByTagName("Valute");

        for(int i = 0; i < elementsList.getLength(); i++){
            Element element = (Element) elementsList.item(i);
            ExchangeRate exchangeRate = exchangeRatesRepository.findById(getTagValueString(element, "Name")).orElseThrow(IllegalStateException::new);
            exchangeRate.setDate(getActualDateString());
            exchangeRate.setValue(getTagValueString(element,"Value"));
            exchangeRatesRepository.save(exchangeRate);
        }
    }

    public static String converter(String concurrency1, String concurrency2, String input, ExchangeRatesRepository exchangeRatesRepository) {

        double value1 = 0, value2 = 0, nominal1 = 0, nominal2 = 0;
        double inputD = Double.valueOf(input);

        ExchangeRate exchangeRate1 = exchangeRatesRepository.findById(concurrency1).orElseThrow(IllegalStateException::new);
        ExchangeRate exchangeRate2 = exchangeRatesRepository.findById(concurrency2).orElseThrow(IllegalStateException::new);

        value1 = Double.valueOf(exchangeRate1.getValue().replace(',', '.'));
        nominal1 = Double.valueOf(exchangeRate1.getNominal().replace(',', '.'));
        value2 = Double.valueOf(exchangeRate2.getValue().replace(',', '.'));
        nominal2 = Double.valueOf(exchangeRate2.getNominal().replace(',', '.'));

        return new DecimalFormat("#.##").format(inputD * ((value1 * nominal2) / (value2 * nominal1)));
    }

    public static Element getElementAll() {

        Element element = null;

        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            element = doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    public static String getTagValueString(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
        return nodeList.item(0).getNodeValue();
    }

    public static String getDateTimeNow(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static Date getActualDate(){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(getElementAll().getAttribute("Date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getActualDateString(){
        return getElementAll().getAttribute("Date");
    }
}