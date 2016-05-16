package com.felix.tickets.utils;

public class DateTimeUtils {
	private DateTimeUtils(){}
	
	private static String[] months = {"Січня","Лютого","Березня","Квітня","Травня","Червня","Липня","Серпня","Вересня","Жовтня","Листопада","Грудня"};
	public static int MonthNameToNumber(String monthName){
		monthName = monthName.toLowerCase();
		for(int i=0;i<12;i++){
			if(monthName.equals(months[i])){
				return i+1;
			}
		}
		
		return -1;
	}
}
