package my.lottery.analyze;

import java.util.ArrayList;
import java.util.HashSet;

import my.lottery.constant.Constant;
import my.lottery.data.LoadHistoryData;
import my.lottery.util.Printer;

public class MyLottery {
	
	
	
	
	/**
	 * 验证给出的彩票号码是否在曾经出现过
	 * @param numbers
	 */
	public void match(String... numbers){
		
		double match_percent = 0;
		
//		int max_match_num = 0;
		
		//int count_equal_match = 0;
		
//		String out_period = "";
		
		HashSet<String> count_equal_match_set = new HashSet<String>();
		
		for(String number : numbers ) {
			
			ArrayList<String> periods = Constant.getNMap(number);
			
			if( periods == null ) {
				
				System.out.println("数字：【"+number+"】从未出现过..");
			}
			
			for(String period : periods ) { // 找出历次包含此号码的中奖号码
				
				String[] t_numbers = Constant.getPMap(period);
				
				double match_num = 0.0;
				
				int t = 0;
				
				for( String t_number : t_numbers ){
					
					for(String s_number : numbers ) {
						
						if(t_number.equals(s_number)){
							
							match_num+=1;
							
							break;
						}
					}
					
					t++;
					
					if( t == 6 ) break;
				}
				
				double t_percent = match_num / 6.0;
				
				if( t_percent > match_percent ) {
					
					match_percent = t_percent;
					
//					out_period = period;
					
//					max_match_num = (int)match_num;
					
					//count_equal_match = 1;
					
					count_equal_match_set.clear();
					
					count_equal_match_set.add(period);
					
				}else if (t_percent == match_percent){
					
					//count_equal_match +=1;
					count_equal_match_set.add(period);
				}
			   
			}
		}
		
		System.out.print("最高的匹配度为:" + (int)(match_percent * 100) + "%,相同匹配次数:" + count_equal_match_set.size());
		System.out.println(",以下为最高匹配结果:");
		
		for(String p : count_equal_match_set) {
			
			System.out.print(p + " : ");
			
			Printer.printArray(Constant.getPMap(p));
		}
	}
	
	
	/**
	 * 打印相同期次的号码
	 * @param period 三位数字的期次号,例如"052"
	 */
	public void printHistoryOfPeriod(String period){
		
		String[] allnumbers = new String[33];
		
		for( int a = 0 ; a < allnumbers.length ; a++ ){
			
			allnumbers[a]="XX";
		}
		
		for( int i = 2003; i <=2014; i++ ) {
			
			String numbers[] = Constant.getPMap( i + period );
			
			if(numbers == null) continue;
			
			System.out.print( i + period + "【" + getEvenDesc(numbers) + "】 : ");
			
			Printer.printArray(numbers);
			
			for( int t = 0 ; t < numbers.length ; t++) {
				
				allnumbers[Integer.parseInt(numbers[t])-1] = numbers[t];
			}
		}
		
		System.out.println( "期号【"+ period + "】所有出现过的数字有: ");
		
		Printer.printArray(allnumbers);
		
	}
	
	
	
	public void printNumofPeriodIInfo( String keyNum,String thisPeriod){
		
		ArrayList<String> periods = Constant.getNMap(keyNum);
		System.out.println("===================================================");
		System.out.print( "数字【"+keyNum + "】最后一次出现在:" + periods.get(periods.size()-1));
		
		System.out.print( ",其中总共出现了:" + periods.size() + "次" );
		//printArray(periods.toArray( new String[ periods.size()]));
		String period_num = thisPeriod.substring(6, 7);
		
		System.out.print( ",在相同期次号时曾出现，包括:");
		
		for( String tmp_period : periods) {
			
			if(tmp_period.endsWith(period_num)) {
				
				System.out.print( tmp_period + "，" );
			}
		}
	}
	
	/**
	 * 判断数据的奇偶性,并返回奇偶数据描述
	 * @return 
	 * =0 奇偶数组<br>
	 * +1 偶数组<br>
	 * -1 奇数组<br>
	 */
	private String getEvenDesc(String []numbers){
		
		int even_cnt = 0;
		int non_even_cnt = 0;
		
		for( String number : numbers) {
			
			if(Integer.parseInt(number) % 2 == 0 ) {
				even_cnt++;
			}else{
				non_even_cnt++;
			}
		}
		
		if( even_cnt == non_even_cnt )  return "奇偶组=0";
		
		return even_cnt > non_even_cnt ? "偶数组+1" : "奇数组-1";
	}
	
	public void printEvenDesc(){
		
		int l = Constant.getPeriods().size();
		
		String out_even  = "";
		String out_equal = "";
		String out_odd	 = "";
		
		String space = "一一一一";
		
		for( int f = l - 12 ; f < l - 1 ; f++ ){
			
			String[] numbers = Constant.getPMap( Constant.getPeriods().get(f));
			
			String desc = getEvenDesc(numbers);
			
			if( desc.endsWith("-1")){
				
				out_odd = out_odd + desc;
				
			}else {
				
				out_odd = out_odd + space;
			}
			
			if ( desc.endsWith("+1")){
				
				out_even = out_even + desc;
				
			}else {
				
				out_even = out_even + space;
			}
			
			if( desc.endsWith("0")){
				
				out_equal = out_equal + desc;
				
			}else {
				
				out_equal = out_equal + space;
			}
		}
		System.out.println( out_even );
		System.out.println();
		System.out.println( out_equal );
		System.out.println();
		System.out.println( out_odd );
	}
	
	
	public static void main(String args[]){
		
		LoadHistoryData.load();
		
		MyLottery m = new MyLottery();
		
		//m.print100Recently();
		//m.findAll(0,12);

//		m.match("03","08","17","21","22","31"); //2014063
//		m.match("06","09","15","24","25","26"); //2014062 09
//		m.match("02","14","17","27","28","31"); //2014061 08
//		m.match("03","05","14","18","25","33"); //2014060 06
//		m.match("05","08","12","13","23","25"); //2014059
//		m.match("02","03","12","13","14","25"); //2014058
//		m.match("02","04","12","18","23","31"); //2014057
//		m.match("01","11","18","20","28","29"); //2014056
//		m.match("02","09","14","19","21","30"); //2014055
//		m.match("01","04","05","11","29","30"); //2014054
//		2014052 09 13 15 28 30 33 08 
//		2014053 14 17 19 22 26 31 02 
		
		
//		m.match("06","07","12","19","23","26");//小陈的,蓝球8
//		m.match("06","08","19","24","26","33"); 
//		m.match("04","10","15","21","27","32"); 
//		m.match("03","11","16","20","28","30"); 
//		m.match("02","09","13","18","24","31");
//		m.match("04","10","15","22","27","32");
//		m.match("06","07","16","21","26","31");
//		m.match("05","11","14","20","23","29");
//		m.match("02","09","13","18","24","31");
//		m.match("05","08","12","13","23","25");
//		m.match("04","07","15","16","20","24");
//		m.match("04","07","15","22","24","29");
//		m.match("07","11","12","21","22","33");
//		m.match("01","07","10","16","22","32");
//		m.match("04","11","16","19","21","29");
//		m.match("04","07","11","15","21","22");
//		m.match("04","10","15","16","20","29");
//		m.match("01","04","10","16","19","29");
//		m.match("04","07","16","19","28","29");
//		m.match("02","06","08","14","20","28");
//		m.match("01","10","13","19","23","28");
//		m.match("01","10","18","21","23","28");
//		m.match("02","06","08","09","20","28");
//		m.match("03","04","05","08","10","22");
//		m.match("06","11","12","13","19","28");
//		m.match("02","06","08","09","20","28");
		// 02在04x出现3次
		// 03在03x出现3次----
		// 12在04x出现3次
		// 13 - 出现频率并不高
		// 14-------
		// 25平均每期出现2次
		Finder.findEachNumLastShow();
		
		m.printHistoryOfPeriod("068");
		
		m.printEvenDesc();
		
		
		
		Constant.getOrCreateIfAbsent("14").printNexts();
		//m.printNumofPeriodIInfo("27","2014058");
		m.match("03","10","11","22","25","28");
		
		

	}
}
