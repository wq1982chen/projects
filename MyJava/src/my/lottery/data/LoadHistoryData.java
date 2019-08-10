package my.lottery.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import my.lottery.ball.BlueBall;
import my.lottery.constant.Constant;

public class LoadHistoryData {

	
	public static void load(){
		
		try {
			
			File f = new File("src\\my\\lottery\\data\\his_lottery_data.dat");

			FileInputStream is = new FileInputStream(f);
		
			BufferedReader  br = new BufferedReader(new InputStreamReader(is));
			
			String line = null;
			
			String prev_blueball = null;
			
			while( (line = br.readLine()) != null ){
				
				String v_numbers = line.trim();
				
				if(v_numbers.startsWith("#")) continue;
				
				String values[] = v_numbers.split(" ");
				
				String p_value = values[0];
				//长度要减去第1个元素的日期,和最后一个元素的蓝球,因此长度为减2
				String[] tmpValue = new String[values.length - 2];
				
				for( int i = 1 ; i < values.length - 1 ; i++ ){
					
					Constant.putNMap(values[i], p_value);
					
					tmpValue[i - 1] = values[i];
				}
				
				String blueBallNumber = values[values.length - 1];
				
				BlueBall bb = Constant.getOrCreateIfAbsent(blueBallNumber);
				
				if( prev_blueball != null ) {
					
					bb.addNext( Constant.getOrCreateIfAbsent(prev_blueball));
				}
				
				bb.addPeriod(p_value);
				
				prev_blueball = blueBallNumber;
				
				Constant.putPMap(p_value, tmpValue);
			}
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		
		LoadHistoryData.load();
	}
}
