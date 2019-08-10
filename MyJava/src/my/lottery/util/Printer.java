package my.lottery.util;

import java.util.ArrayList;


public class Printer {

	public static void printArray(String... numbers){
		
		for(String number : numbers) {
			
			System.out.print(number + " ");
		}
		
		System.out.println();
	}
	
	public static void formatPrint( int offset , ArrayList<String> values, int count){
		
		offset -=1;
		
		//if( values.get(offset).compareTo("2014050") < 0 ) return;
		
		if( offset >= 0 ) {
			
			//if( values.get(offset).compareTo("2014024") >= 0 )
				System.out.print( values.get(offset) + "," );
			//else if(values.get(offset).endsWith("9"))
				//System.out.print( values.get(offset) + "," );
		}
		
		count--;
		
		if(count >0 ) {
			
			formatPrint(offset,values,count);
		}
		
	}
	
}
