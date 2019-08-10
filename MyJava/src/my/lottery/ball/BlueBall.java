package my.lottery.ball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class BlueBall implements Comparable<BlueBall> {
	
	private String ballValue = null;
	
	public BlueBall(String ballValue){
		
		this.ballValue = ballValue;
	}
	
	ArrayList<String> periods = new ArrayList<String>();
	
	public void addPeriod(String period ){
		
		periods.add(period);
	}
	
	private HashMap<BlueBall,Integer> nextBalls = new HashMap<BlueBall,Integer>();
	
	public void addNext(BlueBall bb){
		
		Integer i = nextBalls.get(bb);
		
		if( i == null ) {
			
			nextBalls.put(bb, 1);
			
		}else{
			
			nextBalls.put(bb, i + 1 );
		}
	}
	
	
	public void printNexts(){
		
		
		Iterator<BlueBall> it = nextBalls.keySet().iterator();
		
		ArrayList<BlueBall> balls = new ArrayList<BlueBall>();
		
		while(it.hasNext()) {
			
			balls.add( it.next());
		}
		
		Collections.sort(balls);
		
//		BlueBall[] tmp = nextBalls.toArray( new BlueBall[nextBalls.size()]);
//		
//		Arrays.sort(tmp);
//		
		for(BlueBall b : balls ) {
			
			Integer i = nextBalls.get( b );
			
			System.out.println( b.toString() + "---------> " + i);
		}
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if( !(obj instanceof BlueBall)) return false;
		
		return this.ballValue.equals(((BlueBall)obj).ballValue);
	}
	
	@Override
	public int hashCode() {
		
		return ballValue.hashCode();
	}
	
	@Override
	public String toString() {
		
		return this.ballValue;
	}

	public int compareTo(BlueBall o) {
		
		return this.ballValue.compareTo(o.ballValue);
	}

}
