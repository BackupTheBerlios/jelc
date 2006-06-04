package bot.deadface6.tools;


import bot.deadface6.Account;
import bot.deadface6.AccountManager;
public class Test {

	public Test() {
		super();
		/*System.out.println("SDF@#%#$@TSDGDSSDFdfssdf");
		AccountManager.getAccountManger();
		List l=AccountManager.getAccountManger().getGuild("lnx");
		System.out.println("GUILD: ");
		for(Iterator itr=l.iterator();itr.hasNext();){
			System.out.print(itr.next()+", ");
		}
		System.out.flush();*/
		
		/*
		List l=new Vector();
		l.add("dns");
		l.add("iknow");
		l.add("isdffsdfsdfdfs");
		
		
		
		System.out.println("ONline: ");
		//PlayersOnline.getInstance().parse();
		PlayersOnline online=PlayersOnline.getInstance();
		System.out.println("needs update: "+online.needsUpdate());
		
		List on=online.getOnline(l);
		for(Iterator itr=on.iterator();itr.hasNext();){
			System.out.print(itr.next()+", ");
		}
		System.out.println("needs update: "+online.needsUpdate());
		System.out.flush();*/
		Account tmp=AccountManager.getAccountManger().getAccount("dns");
		System.out.println(tmp.dump());
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test();
	}

}
