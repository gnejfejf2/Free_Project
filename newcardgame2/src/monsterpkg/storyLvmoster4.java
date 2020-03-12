package monsterpkg;

import java.util.Scanner;

import thread.storymodeLv4;

public class storyLvmoster4 extends monster{
	public boolean storyloop = true;
	public int power;
	Scanner sc = new Scanner(System.in);

	public void storyset(int i,int atk1) {

		if(i==0)
		{
			/////////////////////////////////////////////////
			for (int b = 0; b < 80; b++)
				System.out.println("");
			System.out.println("스킵을 원하시면 0번을 눌러주세요");
			storymodeLv4 story = new storymodeLv4();
			Thread op = new Thread(story);
			op.start();
			/////////////////////// 1단계 스토리 출력
			while (storyloop) {
				int skipnum = sc.nextInt();
				if (skipnum == 0) {
					for (int j = 0; j < 80; j++)
						System.out.println("");
					System.out.println("스킵되었습니다");
					op.stop();
					storyloop = false;
				} else {

				}

			}
			try {////////////////////위에 쓰레드가 끝난다면 바로대전 실행시키기 위하여
				op.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			////////////////////////////////////////////////
			System.out.println("파트너 이 결투를 받아들여줘서 고마워..");
			System.out.println("");
			System.out.println("난 듀얼리스트 전력을다해서 듀얼하겠어 그게내 프라이드다");
			System.out.println("");
			System.out.println("The 트릭키");
			System.out.println("");
				this.atk=4000;
				this.def=4000;
				this.name="The 트릭키";
				System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
				
		
		}//스토리4번 몬스터 1번 진화



		else if(i==1){
			System.out.println("아니...");
			System.out.println("");
			System.out.println("멋진걸 ");
			System.out.println("");
			System.out.println("파트너");
			System.out.println("");
			System.out.println("하지만 The 트릭키를 제물로 나와라");
			System.out.println("");
			System.out.println("오벨리스크의 거신병");
			System.out.println("");
			this.atk=4000;
			this.def=4000;
			this.name="오벨리스크의 거신병";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		}//스토리4번 몬스터  2번 진화

		else if(i==2){
			System.out.println("파트너");
			System.out.println("");
			System.out.println("나도 전력을 다해 듀얼하겠어");
			System.out.println("");
			System.out.println("하지만 너가 나를 이겼을때");
			System.out.println("");
			System.out.println("나의 영혼을 가차없이  내 혼을 배어줘 ");
			System.out.println("");
			System.out.println("블랙매지션 소환 거기에 ");
			System.out.println("");
			System.out.println("리버스카드 오픈 성스러운 방어막 거울의힘");
			System.out.println("");
			this.atk=2500+atk1;
			this.def=2000+atk1;
			this.name="블랙매지션(미러포스)";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		}//스토리4번 몬스터  3번 진화
		else if(i==3){
			System.out.println("서로의 몬스터는 한장 남은패는 한장씩");
			System.out.println("");
			System.out.println("나는 블랙매지션걸을 특수소환");
			System.out.println("");
			System.out.println("마법카드 매지션즈 크로스 발동");
			System.out.println("");
			System.out.println("블랙매지션과 블랙매지션걸이 합동공격을 한다 ");
			System.out.println("");
			this.atk=5500+(atk1/2);
			this.def=4000+(atk1/2);
			this.name="매지션즈 크로스";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		}//스토리4번 몬스터  4번 진화
		else if(i==4){
			System.out.println("이거 까지 견뎌내다니 파트너 대단한걸");
			System.out.println("");
			System.out.println("자이제 내 마지막수야");
			System.out.println("");
			System.out.println("마법카드 죽은자의 소생 발동");
			System.out.println("");
			System.out.println("오시리스의 천공룡을 특수소환");
			System.out.println("");
			this.atk=4000+atk1;
			this.def=4000+atk1;
			this.name="오시리스의 천공룡";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		}//스토리4번 몬스터  5번 진화
		
		
	}	
	///////////////////////////////4번스토리 몬스터//////////////////////////////
	
	public int storyatk(int atk1,int def1,int i,int storyLv)
	{
		
		Loop1:while(true) {
			int rand=((random.nextInt(5)+1)%3)+1;//랜덤으로 값을 만들어 공격 수비 스킬중 뭐할지 랜덤으로 생성
		if(rand==1) {
			
			int randatk=random.nextInt(4) + 1;
			System.out.println(name+"의 공격력은 "+((atk1/randatk)));
			power=atk1/randatk;
			
			return power;
			
		}
		else if(rand==2) {
			
			int randdef=random.nextInt(3) + 1;
			System.out.println(name+"의 수비력은 "+((def1/randdef)));
			power=def1/randdef;
			return power;
			
			
		}
//////////////////////////스토리몬스터4번의 공격 수비 출력//////////////
		
		
		
//////////////////////////스킬공격//////////////		
		
		else if(rand==3) {
		
			if(i==storyLv) {
					int rand1=random.nextInt(2) + 1;
					power=(atk1*3)/rand1;
					System.out.println("소뢰탄!"+power);
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
	//////////////////////공격코드 마무리
}
