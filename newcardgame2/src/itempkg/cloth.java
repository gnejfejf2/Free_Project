package itempkg;

import thread.Music;

public class cloth extends item{

	public int skipppointup;

public int clothbuy(int mymoney,int num4){
	Music clothbuy=new Music("cloth.mp3",false);
	Thread op=new Thread(clothbuy);
	Music nobuy=new Music("nobuy.mp3",false);
	Thread op1=new Thread(nobuy);
	if(num4==1) 
	{

		if(mymoney>=1000)//오시리스 레드 옷 구매코드
		{	
			atk=300;
			def=300;
			name="오시리스 레드 교복";
			op.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("구매가 완료 되었습니다.");
			refundmoney=mymoney-1000;
			skipppointup=0;
			return refundmoney;
		}//오시리스 레드 옷 구매코드
		else
		{
			op1.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("돈이 부족합니다 돌아가주세요");
			return 5;
		}//오시리스 레드 옷 구매코드

	}//1번 눌렀을시 구매
	
	else if(num4==2) {//라의 옐로우 듀얼디스크 구매 코드
		if(mymoney>=5000)
		{
			atk=500;
			def=500;
			name="라의 옐로우 교복";
			op.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("구매가 완료 되었습니다.");
			refundmoney=mymoney-5000;
			skipppointup=0;
			return refundmoney;
		}
		else {
			op1.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("돈이 부족합니다 돌아가주세요");
			return 5;
		}
	}//2번구매시종료
	else if(num4==3) {//오벨리스크 블루 듀얼디스크 구매 코드
		if(mymoney>=10000)
	{
			
			atk=1000;
			def=1000;
			name="오벨리스크 블루의 교복";
			op.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("구매가 완료 되었습니다.");
			skipppointup=0;
			refundmoney=mymoney-10000;
			return refundmoney;
		}
		else {	
			op1.start();//아이템 구매 사운드 출력
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("돈이 부족합니다 돌아가주세요");
			return 5;
		}
	}	
	else if(num4==4) {//유희의 옷
			if(mymoney>=30000)
			{
				
				atk=1500;
				def=1500;
				name="유희의 옷";	
				op.start();//아이템 구매 사운드 출력
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				skipppointup=1;//스킬포인트 1증가  가장 큰차이
				refundmoney=mymoney-30000;
				return refundmoney;
			}
			else {	
				op1.start();//아이템 구매 사운드 출력
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

