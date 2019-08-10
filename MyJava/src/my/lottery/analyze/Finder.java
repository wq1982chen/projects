package my.lottery.analyze;

import java.util.ArrayList;
import java.util.Collections;

import my.lottery.constant.Constant;
import my.lottery.util.Printer;

public class Finder {

	/**
	 * �ҳ���ָ�������ڳ��ֵ����ּ���
	 * @param p		ָ��������
	 * @param limit ָ������������
	 * @return
	 */
	public static int findAll( int p, int limit){
		
		ArrayList<String> allnums = new ArrayList<String>(); 
		
		int offset = Constant.periodList.size() - 1 - p;
		
		int count = 0;
		
		for( ;  ;  offset--){
			
			if(limit > 0 && limit == count ) break;
				
			String period = Constant.periodList.get(offset);
			
			count++;
			
			String[] numbers = Constant.getPMap(period);
			
			for( int i = 0 ; i < numbers.length - 1 ; i++) {
				
				String t = numbers[i];
				
				if( allnums.contains(t) ) continue;
				
				allnums.add(t);
			}
			
			if(allnums.size() == 33){
				
				System.out.println("ȫ����������" + count + "�ں�ȫ����ʾ��");
				
				break;
			}
			
			if(offset == 0 ) break;
		}
		
		Collections.sort(allnums);
		
		Printer.printArray(allnums.toArray(new String[allnums.size()]));
		
		return count;
		
		
	}
	
//	public static void findAll(){
//		
//		int sum = 0;
//		
//		for( int i = 0 ; i < 50 ; i++ ) {
//			
//			sum += findAll(i,-9);
//		}
//		
//		System.out.println( sum / 50 );
//	}
	
	
	/**
	 * ��ӡ��100�ڵĵ��м����
	 */
	public static void find100Recently(){
		
		int offset = Constant.periodList.size() - 1;
		
		int count = 0;
		
		for( ; count < 100 ;  offset--){
			
			String period = Constant.periodList.get(offset);
			
			count++;
			
			String[] numbers = Constant.getPMap(period);
			
			Printer.printArray(numbers);
		}
	}
	
	/**
	 * ��ӡ����1-33�ֱ�����������Щ������
	 */
	public static void findEachNumLastShow(){
		
		for ( int i = 1 ; i <=33 ; i++ ) {
			
			String keyNum = i <= 9 ? "0" + i : Integer.toString(i);
			
			ArrayList<String> periods = Constant.getNMap(keyNum);
			
			if( periods != null && !periods.isEmpty()){

				//int offset = periods.size()-1;
				
				System.out.print("��"+keyNum+"���ڡ�");
				
				
				Printer.formatPrint(periods.size(),periods,33);
				
				System.out.println("����");
			
			}
			
		}
		
	}
}
