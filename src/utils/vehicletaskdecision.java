package utils;

import java.text.DecimalFormat;

public class vehicletaskdecision {//��Ҫ�ǳ�����ж�ؾ���
    //����������
	static building b1=new building(15,1031,275,1031,275,922,15,922);
	static building b2=new building(15,737,275,737,275,568,15,568);
	static building b3=new building(15,235,275,235,275,15,15,15);
	static building b4=new building(309,885,683,885,683,767,309,767);
	static building b5=new building(320,461,676,461,676,322,320,322);
	
	public static task taskdecide(task ta,vehicle v){//����true�������Լ��㣬false����ж��
		double ecvcpu=1;//�����豸���ǣ�����1��λ���������ĵ�����----����
		double ecucpu=2;//���˻�
		double ecbcpu=0;//��վ
		double esv=20;//���͵�λ���ݺ���----����
		double esu=50;//���˻�
		double esb=0;//��վ
		//�����Ǽ�����־���Ĵ�������������־������
		//�������վ�ľ���
		double v2bdistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		//���˻��ͻ�վ�ľ���
		double u2bdistance=channelgain.getdistance(main.u.getX(), main.u.getY(), main.u.getZ(), basestation.x, basestation.y, basestation.z);
		//���������˻��ľ���
		double v2udistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, main.u.getX(), main.u.getY(), main.u.getZ());
		//�Ƿ������վ֮ǰ���ϰ���
		double havebuilding;
		if(vehicletaskdecision.basedecide(v)){
			havebuilding=0;
		}
		else{
			havebuilding=1;
		}
		ta.v2bdistance=v2bdistance;
		ta.u2bdistance=u2bdistance;
		ta.v2udistance=v2udistance;
		ta.havebuilding=havebuilding;
		//�������Ǵ����书�ʱ��������Ȳ������
		ta.backgroundnoise=transmissionspeed.n0;
		ta.band=transmissionspeed.w;
		ta.transmissionpower=transmissionspeed.pn;
		//�������ŵ�����
		double distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		double chann=channelgain.getchannelgain(distance);
		ta.v2bchannelgain=chann;//�����Ի�վ
		distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, uav.x, uav.y, uav.z);
		chann=channelgain.getchannelgain(distance);
		ta.v2uchannelgain=chann;//���������˻�
		//�������ǵ�λ���ͺ��ܺ͵�λ�������
		ta.vsende=esv;//�������͵�λ����
		ta.usende=esu;
		ta.vcalcue=ecvcpu;
		ta.ucalcue=ecucpu;
		
		DecimalFormat df=new DecimalFormat("#0.000000");
		DecimalFormat df1=new DecimalFormat("#0.0000000000000");
		//����ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��
		double sendtimetobase=(ta.offloadsize)/(transmissionspeed.gettransmissionspeed(ta.origin,0)*1024);
		double calculation=(ta.revolution)/(50*1024)+800*basestationthread.tasklist.size()/(main.b.calculation*1024);//�����Լ���ʱ��ͼ����б���������ʱ��
		double receivetimebase=(ta.receivesize)/(transmissionspeed.gettransmissionspeed(ta.origin, 0)*1024);
		double receivetimebtou=ta.receivesize/(20*1024);
		double totaltimebase=sendtimetobase+calculation+receivetimebase;
		double self=ta.revolution/(v.calculation*1024);
		
		//�����˻�ͨ�ŷ�Χ�ڵ��ǲ��ͻ�վֱ��������1 �����˻��������ʱ�䣬2 �ڻ�վ�������ʱ��
		//1 ���������˻������,�����Ǹ����˻����ǻ�վ�������Ǿ���Դ���ʱ���Ӱ��
		double vtouspeed=transmissionspeed.gettransmissionspeed(ta.origin,1);
		double sendtimetouav=(ta.offloadsize)/(vtouspeed*1024);
		double receivetimeuav=(ta.receivesize)/(vtouspeed*1024);
		double calculationtouav=(ta.revolution)/(main.u.calculation*1024)+(((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*(uavthread.tasklist.size()+1))/(main.u.calculation*1024);
		double totaltouav=sendtimetouav+calculationtouav+receivetimeuav;
		//2����ж��ȥ��վ�����˻��ͻ�վ�Ĵ���������20Mb
		double uavtobase=(ta.offloadsize)/(20*1024);
		double sendtobase=uavtobase+sendtimetouav;
		double calculationtobase=(ta.revolution)/(main.b.calculation*1024)+((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*basestationthread.tasklist.size()/(main.b.calculation*1024);
		double receivetobase=receivetimebtou+receivetimeuav;
		double totaltobase=sendtobase+calculationtobase+receivetobase;
		//System.out.println("�Լ���"+self);
		//System.out.println("���˻���"+totaltouav);
		//System.out.println("��վ��"+totaltobase);
		
		//�����ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ�
		double energycv=(ta.revolution*ecvcpu)/1000;//�����Լ���������ķѵ�����
		double energysv=(ta.offloadsize/1024)*esv;//�������͸�������Ҫ������
		double energycu=(ta.revolution*ecucpu)/1000;//���˻������������Ҫ������
		double energyrsu=(ta.receivesize/1024)*esu;//���˻����ظ�����������ܺ�
		double energysu=(ta.offloadsize/1024)*esu;//���˻����͸�������Ҫ���ܺ�
		double energycb=ta.revolution*ecbcpu;//��վ�����������Ҫ���ܺ�========����Ҫ
		
		//�����Լ�������ܺ�
		double totalv=energycv;
		//���˻�������ܺ�
		double totalvtou=energysv+energycu+energyrsu;
		//��վ������ܺ�
		double totaltobs=energysv;
		//���˻��м̻�վ����ܺ�
		double totaltoutobs=energysv+energysu+energyrsu;
		
		//�ܵ�Ч�ü���
		//�����Լ��������Ч��
		double totalutilityv =main.ke*((totalv-main.mine)/(main.maxe-main.mine))+main.kt*((self-main.mint)/(main.maxt-main.mint));
        //���˻������Ч��
		double totalutilitytou =main.ke*((totalvtou-main.mine)/(main.maxe-main.mine))+main.kt*((totaltouav-main.mint)/(main.maxt-main.mint));
        //��վ�������Ч��
		double totalutilitytobs =main.ke*((totaltobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltimebase-main.mint)/(main.maxt-main.mint));
        //���˻��м̵Ļ�վ�������Ч��
		double totalutilitytoutobs =main.ke*((totaltoutobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltobase-main.mint)/(main.maxt-main.mint));
       
		boolean touav=true;
		boolean tobs=true;
		if(vehicletaskdecision.basedecide(v)){
			tobs=true;
			System.out.println("�������վֱ��ͨ�ţ�������վ��ʱ��Ϊ��"+totaltimebase);
			System.out.println("sendtimetobase:"+df.format(sendtimetobase)+"   calculation:"+df.format(calculation)+"   receivetimebase:"+df.format(receivetimebase));
			//System.out.println("��������վ�Ĵ����ٶȣ�"+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 0)));
		}
		else{
			tobs=false;
			System.out.println("���������վֱ��ͨ��");
		}//�˴����˻�����ά�Ķ����������Ƕ�ά�ģ�������Z����Ĭ��Ϊ0
		if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){
			touav=false;
			System.out.println("�������˻�ͨ�ŷ�Χ");
		}
		else{
			touav=true;
			System.out.println("�����˻�ͨ�ŷ�Χ��ж�ص����˻�ʱ�䣺"+df.format(totaltouav)+"   ͨ�����˻�ж�ص���վʱ��"+df.format(totaltobase));
		}
		
		
//		System.out.println("�����С��"+ta.offloadsize+"  ��Ҫת�٣�"+ta.revolution);
//		System.out.println("���������˻��Ĵ����ٶȣ�"+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 1)));
//		System.out.println("���˻��㣺  �������͸����˻�ʱ�䣺"+df.format(sendtimetouav)+"  ���˻����ظ�����ʱ�䣺"+df.format(receivetimeuav));
//		System.out.println("���˻��м̻�վ�㣺  ���͸����˻���ʱ�䣺"+df.format(sendtimetouav)+"  ���˻�����վ��ʱ�䣺"+df.format(uavtobase)+"   ������ʱ�䣺"+df.format(sendtobase)+"   ��վ���ظ����˻���"+df.format(receivetimebtou)+"   ���˻����ظ���վ"+df.format(receivetimeuav));
		System.out.println("�Լ���ʱ�䣺"+df.format(self)+"  ���˻���ʱ�䣺"+df.format(totaltouav)+"   ͨ�����˻�����վ��ʱ�䣺"+df.format(totaltobase));
		System.out.println("�����Լ�����ܺģ�"+totalv);
		System.out.println("����ж�ص����˻����ܺģ�"+totalvtou);
		System.out.println("����ֱ��ж�ص���վ���ܺģ�"+totaltobs);
		System.out.println("���������˻�Ϊ�м�ж�ص���վ���ܺģ�"+totaltoutobs);
		System.out.println("�����Լ������Ч�ã�"+totalutilityv);
		System.out.println("����ж�ص����˻�����Ч�ã�"+totalutilitytou);
		System.out.println("����ж�ص���վ����Ч�ã�"+totalutilitytobs);
		System.out.println("���������˻�Ϊ�м�ж�ص���վ�����Ч�ã�"+totalutilitytoutobs);
		System.out.println("��ǰ���˻��б��СΪ��"+uavthread.tasklist.size());
		System.out.println("���˻�������ʱ�䣺"+calculationtouav);
		System.out.println("�����ţ�"+ta.taskid);
		
		double[] total=new double[4];
		total[0]=totalutilityv;
		total[1]=totalutilitytou;
		total[2]=totalutilitytobs;
		total[3]=totalutilitytoutobs;
		int[] totalsign=new int[4];
		totalsign[0]=0;
		totalsign[1]=1;
		totalsign[2]=2;
		totalsign[3]=3;
		double[] totalsendtime=new double[4];
		totalsendtime[0]=0;
		totalsendtime[1]=sendtimetouav;
		totalsendtime[2]=sendtimetobase;
		totalsendtime[3]=sendtobase;
		double n;
		int n1;
		for(int i =0;i<3;i++){
			for(int j=0;j<3;j++){
				if(total[j]>total[j+1]){
					
					n=total[j];
					total[j]=total[j+1];
					total[j+1]=n;
					n1=totalsign[j];
					totalsign[j]=totalsign[j+1];
					totalsign[j+1]=n1;
					
				}
				
			}
		}
	    //tobs=false;//ʹ�����ͻ�վ�����˻����޷�����@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//touav=false;//
		ta.cost0=totalutilityv;
		ta.cost1=totalutilitytou;
		ta.cost2=totalutilitytobs;
		ta.cost3=totalutilitytoutobs;
		ta.energyconsumption0=totalv;
		ta.energyconsumption1=totalvtou;
		ta.energyconsumption2=totaltobs;
		ta.energyconsumption3=totaltoutobs;
		if(tobs){//���վ����ֱ��ͨ��
			if(touav){//���������˻�ͨ��
				//for(int i=0;i<4;i++) {//�޸�Ϊ���˻����м�
					//if(totalsign[i]!=3) {
						ta.setDec(new decision(totalsign[0],total[0],totalsendtime[0]));	
						return(ta);
					}
				
			
				//}
				
				
		//	}
			else{//�����������˻�ͨ��
				for(int i=0;i<4;i++){
					if(totalsign[i]!=1&&totalsign[i]!=3){
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}
				}
			}
				}
			
		else{//���������վֱ��ͨ��
			if(touav){//���������˻�ͨ��
				for(int i=0; i<4;i++) {
					if(totalsign[i]!=2) {//�޸�Ϊ���˻������м�
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}
				}
}
			else{//�����������˻�ͨ��
				for(int i=0;i<4;i++){
					if(totalsign[i]!=2&&totalsign[i]!=3&&totalsign[i]!=1){
						//System.out.println(totalsign[i]+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}

				}
			}
		}
		
		System.out.println("********************************���߳���*****************************************************************");
		return(ta);
//		if(vehicletaskdecision.basedecide(v)){//�������վͨ��                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
//			//
//			if(self>totaltimebase){
//				ta.setDec(new decision(2,totaltimebase,sendtimetobase));
//				return(ta);//���ؼ٣�������ж�ص���վ������������ص���վ�Ĵ��������
//			}
//			else{
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);//�����棬�����Լ�����
//		}
//		}
//		else{//���������վͨ�ţ������ж�ж�ص�����
//			//�����ж������˻��Ƿ���ͨ��
//			if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){//�������˻�ͨ�ŷ�Χ�ڣ��Լ���
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);
//			}
//			else{//�����˻�ͨ�ŷ�Χ�ڣ��жϾ���
//				if(self<=totaltouav&&self<=totaltobase){//�Լ�����
//					ta.setDec(new decision(0,self,0.0));
//					return(ta);
//				}
//				else if(totaltouav<=self&&totaltouav<=totaltobase){//ж�ص����˻�
//                    ta.setDec(new decision(1,totaltouav,sendtimetouav));
//					return(ta);
//				}
//				else{//ж�ص���վ
//                    ta.setDec(new decision(3,totaltobase,sendtobase));
//					return(ta);
//				}
//			}
//			
//		}
		
	}
	public static boolean basedecide(vehicle v){//�жϳ����뽨����֮���Ƿ����ϰ���
		if(b1.judge(basestation.x, basestation.y, v.x, v.y)||
				b2.judge(basestation.x, basestation.y, v.x, v.y)||
				b3.judge(basestation.x, basestation.y, v.x, v.y)||
				b4.judge(basestation.x, basestation.y, v.x, v.y)||
				b5.judge(basestation.x, basestation.y, v.x, v.y)
				)
			return(false);//ֻҪ��һ����������ʹ������ϰ���ͷ��ؼٴ����޷�ֱ������
		else
			return(true);//���������ﶼ�ٴ������ཻ������ֱ������
	}
	
}
