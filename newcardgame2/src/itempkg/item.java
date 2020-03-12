package itempkg;

import thread.Music;

public class item{
	public String name;
	public int def;
	public int atk;
	public int refundmoney;
	public void itemset(int atk,int def,String name)//값 셋팅 코드
{
	this.atk=atk;
	this.def=def;
	this.name=name;
}
	
	public int myitembuy(int mymoney,int num3) {//듀얼디스크 구매 코드
		Music buy=new Music("buy.mp3",false);
		Thread op=new Thread(buy);
		Music nobuy=new Music("nobuy.mp3",false);
		Thread op1=new Thread(nobuy);
	
		if(num3==1) 
		{
		if(mymoney>=1000)//오시리스 레드 듀얼디스크 구매 코드
		{
			
			atk=500;
			def=500;
			name="오시리스 레드 듀얼디스크";
			System.out.println("구매가 완료 되었습니다.");
			op.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("구매가 완료 되었습니다.");
			refundmoney=mymoney-1000;
			return refundmoney;
		}
		else
		{
			op1.start();//구매실패 사운드
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("돈이 부족합니다 돌아가주세요");
			return 5;
		}

		}
		//1번 눌렀을시 구매
		if(num3==2) {//라의 옐로우 듀얼디스크 구매 코드
			if(mymoney>=5000)
			{
				atk=1000;
				def=1000;
				name="라의 옐로우 듀얼디스크";
				refundmoney=mymoney-5000;
				op.start();//아이템 구매 사운드 출력
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				return refundmoney;
			}
			else {
				op1.start();//구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			}
		}//2번구매시종료
		if(num3==3) {//오벨리스크 블루 듀얼디스크 구매 코드
			if(mymoney>=10000)
			{
				
				atk=1500;
				def=1500;
				name="오벨리스크 블루의 듀얼디스크";	
				refundmoney=mymoney-10000;
				op.start();//아이템 구매 사운드 출력
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				return refundmoney;
			}
			else {	
				op1.start();//구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			}
		}//3번구매 종료
		else {
			return 5;
		}
	}
	
}
