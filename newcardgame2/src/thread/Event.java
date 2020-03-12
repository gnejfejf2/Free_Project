package thread;

import java.util.Random;
import thread.newevent;

public class Event implements Runnable {
	public int power;
	public boolean isloop;
	public int rvLife;
	Random random = new Random();
	

	public Event(int power, boolean isloop, int rvLife) {
		super();
		this.power = power;
		this.isloop = isloop;
		this.rvLife = rvLife;
	}

	@Override
	public void run() {
		while (isloop) {
			
			battle();
	
			isloop=false;
			
			
		}

	}

	public void battle() {
		System.out.println();
		System.out.println();
		System.out.println("==========내 몬스터의 공격 차례입니다.==========");
		System.out.println();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("내 몬스터의 데미지는 "+power+"입니다!");
		System.out.println();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("상대방에게 직접 공격!");
		System.out.println();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		rvLife = rvLife - power;
		System.out.println();
		System.out.println("상대방의 남은 라이프 포인트는 "+rvLife+" 입니다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
