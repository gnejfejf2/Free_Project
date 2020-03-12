package monsterpkg;

import java.util.Scanner;

import thread.storymodeLv0;

public class storyLvmoster0 extends monster{
	public boolean storyloop = true;
	public int power;
	Scanner sc = new Scanner(System.in);

	public void storyset(int i,int atk1) 
	{
		if(i==0)
		{
			///////////////////////////////////////////////
			for (int b = 0; b < 80; b++)
				System.out.println("");
			System.out.println("스킵을 원하시면 0번을 눌러주세요");
			storymodeLv0 story = new storymodeLv0();
			Thread op = new Thread(story);
			op.start();
	
			while(storyloop) {
			int skipnum=sc.nextInt();
			if(skipnum==0)
			{
				for (int j = 0; j < 80; j++)
				      System.out.println("");
				System.out.println("스킵되었습니다");
				op.stop();
				storyloop=false;
			}
			else {
				
			}	
			///////////////////0단계 스토리 출력////////////////
			}
			try {
				op.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("여기까지 잘 올라오셨어요 유희군 저를 이기면 할아버지의 영혼을 돌려드리죠");
			System.out.println("");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("제가 가지고있는 몬스터는 사우전드 아이즈 세크리파이스 당신의 몬스터의 공격력을 흡수하죠");
			System.out.println("");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.atk=1500+(atk1/2);
				
			this.def=1000+(atk1/2);
				
			this.name="사우전드 아이즈 세크리파이스";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
	
				
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//스토리 1번

	}	
		
		
		
		
		
		
		
		
		
		
		
		
	public int storyatk(int atk1,int def1,int i,int storyLv)
	{
		
		Loop1:while(true) {
			int rand=((random.nextInt(5)+1)%3)+1;//랜덤으로 값을 만들어 공격 수비 스킬중 뭐할지 랜덤으로 생성
		if(rand==1) {
			
			int randatk=random.nextInt(4) + 1;
			System.out.println("");
			System.out.println(name+"의 공격력은 "+((atk1/randatk)));
			System.out.println("");
			power=atk1/randatk;
			
			return power;
			
		}
		else if(rand==2) {
			
			int randdef=random.nextInt(3) + 1;
			System.out.println("");
			System.out.println(name+"의 수비력은 "+((def1/randdef)));
			System.out.println("");
			power=def1/randdef;
			return power;
			
			
		}
//////////////////////////스토리몬스터1번의 공격 수비 출력//////////////
		
		
		
//////////////////////////스킬공격//////////////		
		
		else if(rand==3) {
		
			if(i==storyLv) {
					int rand1=random.nextInt(3) + 1;
					power=(atk1*2)/rand1;
					System.out.println("");
					System.out.println("천개의 눈!!!"+power);
					System.out.println("");
					return power;
			}
			else {
				continue Loop1;
			}
		}//라이벌 스킬
		
//////////////////////////스킬공격//////////////		
		return power;
	}
	}
}
