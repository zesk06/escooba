/**
 * 
 */
package booba.skaya.escooba.util;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class CombinatorTest {

	@Test
	public void testCombinate() {
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		for(ArrayList<Integer> t: Combinator.combinations(test)){
			System.out.println(join(",  ", t));
		}
	}
	
	public static String join(String s, Collection<? extends Object> col){
		String joined = "";
		boolean first = true;
		for(Object o : col){
			if(!first){
				joined+=s;
			}
			if(o!=null){
				joined+=o.toString();
			}else{
				joined+="null";
			}
		}
		return joined;
	}
}
