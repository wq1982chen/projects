package my.lottery.analyze;

import java.util.ArrayList;

import my.lottery.constant.Constant;

public class Ruler {

	/**
	 * 
	 * @param period �����ںţ�����2014064
	 * @param p_period ��������
	 * @param number ���жϵ�����
	 * @return
	 */
	public boolean isReasonable(String period,String p_period,String number){
		
		int i = Integer.parseInt(period.substring(6));
		
//		
//		while( p - 1 > 0)
		
		ArrayList<String> periods = Constant.getNMap(number);
		
		ArrayList<String> tmp_periods = new ArrayList<String>();
		
		for( String tmp_p : periods ) {
			
			if( tmp_p.compareTo(p_period) >= 0 ) tmp_periods.add(tmp_p);
		}
		
		if( i > 4 ) {
			
			for( String p : tmp_periods ) {
				
				int a = Integer.parseInt(p.substring(6));
				
			}
			
		}
		
		return false;
	}
	
//	private String getLastPeriod(){
//		
//		
//		
//	}
}
