package humanpkg;



public class human extends nomalchar{
public int storyLv;

public int Lvpoint;


	public void set(String name,int storyLv,int money,int health)
		{
			this.name=name;
			this.storyLv=storyLv;
			this.money=money;
			this.health=health;
		}
	
	public void myhealing(int num3,int health1,int money1,int healing)//힐링코드
	{
		if(num3==1)
		{
			if(money>=500)
			{
				health=health1+1;
				money=money1-500;
				
			}//1번 힐링
			else
			{
				System.out.println("거지는 가랏");
			}
			
		}//1번 힐링
		else if(num3==2)
		{
			if(money>=1500)
			{
				health=health1+3;
				money=money1-1500;
				
			}
			else
			{
				System.out.println("거지는 가랏");
			}
		}//2번힐링
		else if(num3==3) {
			System.out.println("힐링의 샘의 물을 마셨습니다.");
			health=health1+healing;
		}
			
		else
		{
			System.out.println("거지는 가랏 잘못눌렀다!");
		}
		
	}
	
}
