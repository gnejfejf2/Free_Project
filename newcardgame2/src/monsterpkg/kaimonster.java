package monsterpkg;

import java.util.Random;
import java.util.Scanner;


public class kaimonster extends monster {
	public int skillatk;
	public int brave;
	public int brain;
	public int lucky;
	Scanner sc=new Scanner(System.in);
	
	Random random = new Random();
	public void stanset(int brave,int brain,int lucky) {
		this.brave=brave;
		this.brain=brain;
		this.lucky=lucky;
		
	}
	public void statbuy(int num) {//스텟 구매
		if(num==1)
		{
			brave=brave+5;
			System.out.println("카이바의 스킬 공격력이 소폭 상승!");
		}
		else if(num==2)
		{
			brain=brain+5;
			System.out.println("공격표시 수비표시 최종값 상승 소폭... ");
		}
		else if(num==3)
		{
			lucky=lucky+5;
			System.out.println("카이바한테 무슨의미가 있을까 이게...!");
		}
		
	}


public int power(int atk1,int def1,int skillpoint,int randomno,int allitematk,int allitemdef)//스킬구성
{
	System.out.println("공격과 수비중 어떤 공격을 하시겠습니까.");
	System.out.println("신의 공격 가능 횟수"+skillpoint);
	System.out.println("1.공격 2.수비력 3.신의공격(최종단계만 공격 가능)");
	int num2=sc.nextInt();
	if(num2==1) {
		skillname="공격표시";
		int randatk=random.nextInt(randomno) + 1;
		skillatk=(((atk1/randatk)+allitematk)*brain/100);
		return skillatk;
	
	}
	else if(num2==2) {
		skillname="수비표시";
		int randdef=random.nextInt(randomno-1) + 1;
		skillatk=(((atk1/randdef)+allitemdef)*brain/100);
		return skillatk;
	
	}
	
	else if(num2==3) {
	
		if(sppoint>0) {
			System.out.println("1.신의 주먹 2.멸망의 주먹 3.대표효");
			int num1=sc.nextInt();
			if(num1==1) 
			{
					skillname="신의 주먹";
					int rand=random.nextInt(4) + 1;
					sppoint=skillpoint-1;
					skillatk=(((def*3)/rand)*brave)/100;
					return skillatk;
			}
			else if(num1==2) {
					skillname="멸망의 주먹";
					sppoint=skillpoint-1;
					skillatk=(int)(((atk1+def1)*0.5)*brave)/100;
					return skillatk;
			}
			else if(num1==3) {
					skillname="대표효";
					sppoint=skillpoint-1;
					int rand=random.nextInt(5) + 1;
					skillatk=(int)((((atk1+def1)*2)/(rand-2))*brave)/100;
					return skillatk;
			}
			else
				System.out.println("잘못 입력 하셨습니다.");
				System.out.println("잘못된 선택으로 공격기회를 잃었습니다");
				return 0;
			}
			else {
			System.out.println("스킬포인트가 부족합니다");
			System.out.println("잘못된 선택으로 공격기회를 잃었습니다");
			return 0;
		}
	}//카이바의 스킬 공격 3가지선택지

return 0;
	
}//카이바 공격 종료	
public int randpower(int atk1, int def1, int randomno, int allitematk, int allitemdef)//랜덤공격 루트
{
	int rand=random.nextInt(30)%2;
	if (rand == 0) {
		skillname = "공격표시";
		int randatk = random.nextInt(randomno) + 1;
		skillatk = (((atk1 / randatk) + allitematk) * brave / 100);

		return skillatk;

	} else if (rand == 1) {
		skillname = "수비표시";
		int randdef = random.nextInt(randomno - 1) + 1;
		skillatk = (((atk1 / randdef) + allitemdef) * brave / 100);
		return skillatk;

	}

	return 0;
}//랜덤공격 루트
}
