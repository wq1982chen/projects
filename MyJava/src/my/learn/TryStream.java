package my.learn;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import my.learn.annotation.Child;

public class TryStream {

		private static List<Child> list = null;
		
		static {
			
			Child A = new Child("Liu De Hua");
			Child B = new Child("Zhang Xue You");
			Child C = new Child("Guo Fu Cheng");
			Child D = new Child("Li Ming");
			
			list = Arrays.asList(A,B,C,D);
		}
		
		public static List<Child> getData(){
			return list;
		}
		
		public static void main(String[] args) {
			List<Child> data = TryStream.getData();
			// ------------- Filter ---------
			List<Child> collect1 = data.stream().filter( p -> {
				if("Liu De Hua".equals(p.name()) || "Li Ming".equals(p.name())) {
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			System.out.println("collect1:"+collect1);
			
			// ------------- Map1 ---------
			List<String> map1 = data.stream().map( p -> p.name()).collect(Collectors.toList());
			System.out.println("map1:" + map1);
			// ------------- Map2 ---------
			List<String> map2 = data.stream().map( Child::name).collect(Collectors.toList());
			System.out.println("map2:" +map2);
			// ------------- Map3 ---------
			List<String> map3 = data.stream().map( p -> { 
				return p.name();
			}).collect(Collectors.toList());
			System.out.println("map3:" +map3);
			
			// ------------- FlatMap1 ---------
			List<String> flatMap1 = data.stream().flatMap( p -> Arrays.stream(p.name().split(" ")))
					.collect(Collectors.toList());
			System.out.println("flatMap1:" + flatMap1);
			//  flatmapµÄmapÊµÏÖ
			List<String> flatMap2 = data.stream().map( p -> p.name().split(" "))
					.flatMap(Arrays::stream).collect(Collectors.toList());
			System.out.println("flatMap2:" + flatMap2);
			
			// ---------------reduce ------------
			Integer reduce = Stream.of(1,2,3,4).reduce(10, (count,item) -> {
				System.out.println("count:" + count + ",item:" + item);
				return count + item;
			});
			System.out.println("reduce:" + reduce);
			
			Integer reduce1 = Stream.of(1,2,3,4).reduce(10, (x,y) ->x + y);
			System.out.println("reduce1:" + reduce1);
			
			String reduce2 = Stream.of("1","2","3","4").reduce("0", (x,y) ->x + "," +  y);
			System.out.println("reduce2:" + reduce2);
		}
}
