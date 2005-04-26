/*
 * Created on 16/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc.gui;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.io.*;
import java.util.*;


import elc.SystemInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MapView extends JComponent implements SystemInterface, MouseListener {
GuiClient gui;
String map;
Image currentMap;
Point cross;
Vector mapItems;
	/**
	 * 
	 */
	public MapView(GuiClient g) {
		super();
		this.gui=g;
		mapItems=new Vector();
		g.getClientConnection().addSystemListener(this);
		this.addMouseListener(this);
		
	}
	public void onMinute(int t) {
	}
	/* (non-Javadoc)
	 * @see elc.SystemInterface#onChangeMap(java.lang.String)
	 */
	public void onChangeMap(String map) {
		this.map=map;
		
		System.out.println(map);
		
		File f=new File(map.substring(0,map.length()-3)+"bmp");
		if(f.exists()&&f.isFile()){
			System.out.println("loading image:"+f);
			try {
				currentMap=ImageIO.read(f);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		File mapInfo=new File(map+".txt");
		if(mapInfo.exists()&&mapInfo.isFile()){
			load(mapInfo);
		}
		
		else{
			System.err.println("no label file image file: "+f+" "+f.exists()+" "+f.isFile());
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){
		if(currentMap!=null){
			g.drawImage(currentMap,0,0,this.getWidth(),this.getHeight(),this);
			//System.out.println(this.getWidth()+" "+this.getY()+" "+currentMap.getHeight(this)+" "+currentMap.getWidth(this));
			
			if(cross!=null){
				g.setColor(Color.BLUE);
				g.drawLine(cross.x-5,cross.y-5,cross.x+5,cross.y+5);
				g.drawLine(cross.x-5,cross.y+5,cross.x+5,cross.y-5);
				
			}
			Iterator itr=mapItems.iterator();
			while(itr.hasNext()){
				MapTag tmp=(MapTag)itr.next();
				g.setColor(Color.GREEN);
				g.drawLine(tmp.point.x-5,tmp.point.y-5,tmp.point.x+5,tmp.point.y+5);
				g.drawLine(tmp.point.x-5,tmp.point.y+5,tmp.point.x+5,tmp.point.y-5);
				g.drawString(tmp.label,tmp.point.x+8,tmp.point.y);
			}
			
		}
		else{
			g.drawString("Image doesn't exist for this map",this.getWidth()/2,this.getHeight()/2);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		cross=e.getPoint();
		repaint();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean load(File f){
		if (f.exists()&&f.isFile()){
			mapItems=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
			     BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			     String tmp=br.readLine();
			     while(tmp!=null){
			     	if(!tmp.equals("")){
			     		System.out.println(":"+tmp);
			     		
			     		mapItems.add(new MapTag(tmp));
			     		
			     		
			     	}
			     	tmp=br.readLine();
			     }
			     return true;
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		else{
			System.err.println("bad file: "+f.getName());
			return false;
		}
	}
	public boolean save(File f){
		try {
			/*FileOutputStream fos=new FileOutputStream(f);
			DataOutputStream out = new DataOutputStream(fos);*/
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=mapItems.elements();
			while(e.hasMoreElements()){
				out.println(e.nextElement());
			}
			out.close();
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	private class MapTag{
		Point point;
		String label;
		MapTag(String s) throws NumberFormatException{
			int tmp=s.indexOf(" ");
			int x=Integer.parseInt(s.substring(0,tmp));
			
			int tmp2=s.indexOf(" ",tmp+1);
			int y=Integer.parseInt(s.substring(tmp+1,tmp2));
			point=new Point(x,y);
			label=s.substring(tmp2,s.length());
			

		}
		
		public String toString(){
			return point.x+" "+point.y+" "+label;
		}
	}
}
