package bot.deadface5;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;

public class Misc {
	public static final char LIGHT_PINK=(char)127;
	public static final char LIGHT_ORANGE=(char)8364;
	public static final char LIGHT_YELLOW=(char)65533;
	public static final char LIGHT_GREEN=(char)8218;
	public static final char LIGHT_BLUE=(char)402;
	public static final char LIGHT_PURPLE=(char)8222;
	public static final char LIGHT_WHITE=(char)8230;
	public static final char RED=(char)8224;
	public static final char ORANGE=(char)8225;
	public static final char YELLOW=(char)710;
	public static final char GREEN=(char)8240;
	public static final char BLUE=(char)352;
	public static final char PURPLE=(char)8249;
	public static final char GREY=(char)338;
	public static final char DARK_RED=(char)65533;
	public static final char DARK_ORANGE=(char)381;
	public static final char DARK_YELLOW=(char)65533;
	public static final char DARK_GREEN=(char)65533;
	public static final char DARK_BLUE=(char)8216;
	public static final char DARK_PURPLE=(char)8217;
	public static final char DARK_GREY=(char)8220;
	public static final char DARKER_RED=(char)8221;
	public static final char DARKER_ORANGE=(char)8226;
	public static final char DARKER_YELLOW=(char)8211;
	public static final char DARKER_GREEN=(char)121;
	public static final char DARKER_BLUE=(char)732;

	
	
	
	
	public static final int LOCAL=0;
	public static final int PM=1;
	public static final int GM=2;
	public static final int CHANNEL=3;
	public static final int IRC=4;
	public static final int IRCPM=5;
	public static final int EMAIL=6;
	public static final int CONSOLE=7;
	public static void ouputList(Replyer reply,String header, Iterator i){
		String res=header;
		String tmp;
		if(i.hasNext()){
			tmp=i.next().toString();
			if (res.length()+tmp.length()<150){
				res=res+tmp;
			}
			else{
				reply.reply(".");
			}
			while(i.hasNext()){
				tmp=i.next().toString();
				if (res.length()+tmp.length()+reply.getAccount().getName().length()<reply.getMaxMessageSize()){
					res=res+", "+tmp;
				}
				else{
					reply.reply(res+".");
					res=tmp;
				}
			}
			reply.reply(res);	
		}
	}
	public static void outputList(Replyer reply,String header, Enumeration e ){
		String res=header;
		String tmp;
		if(e.hasMoreElements()){
			tmp=e.nextElement().toString();
			if (res.length()+tmp.length()<150){
				res=res+tmp;
			}
			else{
				reply.reply(res+".");
			}
			while(e.hasMoreElements()){
				tmp=e.nextElement().toString();
				if (res.length()+tmp.length()+reply.getAccount().getName().length()<reply.getMaxMessageSize()){
					res=res+", "+tmp;
				}
				else{
					reply.reply(res+".");
					res=tmp;
				}
			}
			reply.reply(res);	
		}
	}
	public static String StripColours(String s){
		String tmp=new String();
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c<(128)){
				tmp=tmp+c;
			}
		}
		return tmp;
	}
	static String timeSince(String header,long time){
		long difference=System.currentTimeMillis()-time;
		Calendar c=Calendar.getInstance();
		//System.out.println(difference+" "+new Date(difference));
		c.setTimeInMillis(difference);
		int day=c.get(Calendar.DAY_OF_YEAR);
		int hour=c.get(Calendar.HOUR_OF_DAY);
		int minute=c.get(Calendar.MINUTE);
		int second=c.get(Calendar.SECOND);		
		if(day!=0){
			return header+" "+day+" Days, "+hour+" Hours, "+minute+" minutes and "+second+" seconds.";
		}
		else if(hour!=0){
			return header+" "+hour+" Hours, "+minute+" minutes and "+second+" seconds.";
		}
		else if(minute!=0){
			return header+" "+minute+" minutes and "+second+" seconds.";
		}
		else{
			return header+" "+second+" seconds.";
		}
	}//118800000
}
