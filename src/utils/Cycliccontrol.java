package utils;

public class Cycliccontrol {

	public static int idsign; 
	public static boolean cycliccontrol(int id){
		if(idsign==id){
			return(true);
		}
		else{
			return(false);
		}
	}
	public void runtimeslot(){
		clock.runtimeslot();
	}
	public static void addsign(int id){
		//System.out.println("�豸IDΪ:"+id);
		for(int i=0;i<main.num+2;i++){
			if(allthreadstate.threadstate[(id+i+1)%(main.num+2)]==true){
				
				idsign=(id+i+1)%(main.num+2);
				//System.out.println("��һ��ִ�е��豸IDΪ:"+idsign);
				break;
			}
	}
	}
}
