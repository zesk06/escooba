package booba.skaya.escooba.util;

import java.util.Collection;

public class Strings {
	public static String join(String s, Collection<? extends Object> col){
		String joined = "";
		boolean first = true;
		for(Object o : col){
			if(!first){
				joined+=s;
			}else{
				first = false;
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
