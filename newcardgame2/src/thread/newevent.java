package thread;

import java.util.Random;

public class newevent implements Runnable {

	public int power;
	public boolean isloop2;
	public int myLife;
	Random random = new Random();



	public newevent(int power,boolean isloop2,int myLife) {

		super();
		this.power = power;
		this.isloop2 = isloop2;
		this.myLife = myLife;
	}

	@Override
	public void run() {
		while(isloop2==false) {
			battle();
			
			isloop2=true;
			
		}
	}
	
	
	public void battle() {
		System.out.println();
		System.out.println();
		System.out.println("==========상대방 몬스터의 공격 차례입니다.==========");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.print("                              ");
		System.out.println("상대방 몬스터의 데미지는 "+power+"입니다!");
		System.out.println();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print("                              ");
		System.out.println("상대방 몬스터가  플레이어를 직접 공격!");
		System.out.println();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		myLife = myLife - power;
		System.out.print("                              ");
		System.out.println("내 남은 라이프 포인트는 "+myLife+" 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
