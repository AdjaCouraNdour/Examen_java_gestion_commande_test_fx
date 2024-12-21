package com.ism.core.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class ViewImpl<T> implements View<T>  {
    
    @Override
    public void afficher(List<T> datas) {
       for (T data : datas) {
        System.err.println(data);
       }
    }


  public static LocalDate formatDate(String date){
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  return LocalDate.parse(date,formatter);
  } 
  public static LocalTime formatHeure(String heure){
   DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("HH:mm");
  return  LocalTime.parse(heure,formatter);
}
}
