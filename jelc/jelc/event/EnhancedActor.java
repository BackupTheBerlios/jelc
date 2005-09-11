/*
 * Created on 17/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.event;

import java.io.DataInputStream;
import java.io.IOException;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnhancedActor extends Actor {

	/**
	 * 
	 */
	public EnhancedActor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param p
	 * @throws IOException 
	 */
	/*public EnhancedActor(Packet p) {
		super(p);		
		byte[] tmp=p.data.array();
		char[] chars=new char[30];
		int count=30;
		for (int i=0;i<30;i++){
			if(tmp[i+28]!=0){
				chars[i]=(char)tmp[i+28];
			}
			else{
				chars[i]='\0';
				count=i;
				break;
			}
		}
		
		
		this.actor_name = new String(chars,0,count);
		/*this.que = "";
		
		this.actor_id =p.data.getShort();
		this.x_pos = p.data.getShort(2);
		this.y_pos = p.data.getShort(4);
		this.z_pos = p.data.getShort(6);
		this.z_rot = p.data.getShort(8);
		this.actor_type = p.data.getShort(10);
		this.skin = p.data.get(12);
		this.hair = p.data.get(13);
		this.shirt = p.data.get(14);
		this.pants = p.data.get(15);
		this.boots = p.data.get(16);
		this.max_health = p.data.getShort(18);
		this.cur_health = p.data.getShort(20);
		this.kind_of_actor = p.data.get(22);
	}*/
	public EnhancedActor(int length,DataInputStream in) throws IOException{
		this.actor_id =in.readShort();
		this.x_pos=in.readShort();
		this.y_pos=in.readShort();
		this.z_pos=in.readShort();
		this.z_rot =in.readShort();
		this.actor_type =in.readShort();
		this.skin =in.readShort();
		this.hair =in.readByte();
		this.shirt = in.readByte();
		this.pants = in.readByte();
		this.boots = in.readByte();
		this.max_health = in.readShort();
		this.cur_health = in.readShort();
		this.kind_of_actor =in.readShort();
		
        this.actor_name=new String();
        for(int i=28;i<length;i++){
        	actor_name=actor_name+in.readChar();
        }
	}
	public boolean isEnhanced(){
		return true;
	}

}