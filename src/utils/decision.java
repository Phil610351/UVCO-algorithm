package utils;

public class decision {

	int de;//�����������ߣ����ڳ������ߣ�0�������Լ���  1��ж�ص����˻���   2 ��ж�ص���վ��
	double time;//�������չ��ص����˻������Լ�������ʱ����
	double sendtime;//����ʱ��
	public decision(int de,double time,double sendtime){
		this.de=de;
		this.time=time;
		this.sendtime=sendtime;
	}
	public int getDe() {
		return de;
	}
	public void setDe(int de) {
		this.de = de;
	}
	public double getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getSendtime() {
		return sendtime;
	}
	public void setSendtime() {
		this.sendtime=sendtime;
	}
	
}
