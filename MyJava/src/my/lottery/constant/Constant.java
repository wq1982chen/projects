package my.lottery.constant;

import java.util.ArrayList;
import java.util.HashMap;

import my.lottery.ball.BlueBall;

public class Constant {

	/**
	 * ��������-->�н�����֮��Ĺ�ϵ,ֻ��������
	 */
	public static HashMap<String,String[]> pMap = new HashMap<String,String[]>();
	
	/**
	 * �����н��������н�����֮��Ĺ�ϵ,ֻ����33������
	 */
	public static HashMap<String,ArrayList<String>> nMap = new HashMap<String,ArrayList<String>>();
	
	public static ArrayList<String> periodList = new ArrayList<String>();
	
	/**
	 * �������������������ӳ��
	 */
	public static HashMap<String,BlueBall> bMap = new HashMap<String,BlueBall>();
	
	public static BlueBall getOrCreateIfAbsent(String blueBallNumber){
		
		BlueBall ball = bMap.get(blueBallNumber);
		
		if( ball == null ) {
			
			ball = new BlueBall(blueBallNumber);
			
			bMap.put(blueBallNumber, ball);
		}
		
		return ball;
	}
	
	
	
	public static void putPMap(String period , String[] numbers){
		
		pMap.put(period, numbers);
		
		periodList.add(period);
	}
	
	public static String[] getPMap( String period ){
		
		return pMap.get( period );
	}
	
	public static void putNMap(String number , String period){
		
		ArrayList<String> periods = nMap.get(number);
		
		if( periods == null ) {
			
			periods = new ArrayList<String>();
			
			periods.add(period);
			
			nMap.put(number, periods);
			
		}else{
			
			periods.add(period);
		}
	}
	
	public static ArrayList<String> getNMap( String number ){
		
		return nMap.get( number );
	}
	
	public static ArrayList<String> getPeriods(){
		
		return periodList;
	}
	
}
