package monsterpkg;

import java.util.Scanner;

import thread.storymodeLv3;

public class storyLvmoster3 extends monster {
	public boolean storyloop = true;
	public int power;
	Scanner sc = new Scanner(System.in);

	public void storyset(int i, int atk1) {

		if (i == 0) {
			/////////////////////////////////////////////////
			for (int b = 0; b < 80; b++)
				System.out.println("");
			System.out.println("스킵을 원하시면 0번을 눌러주세요");
			storymodeLv3 story = new storymodeLv3();
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
			try {//////////////////// 위에 쓰레드가 끝난다면 바로대전 실행시키기 위하여
				op.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			////////////////////////////////////////////////
			System.out.println("내 이름은 대도 바쿠라 천년 아이템을 가지기 위해 나타났다");
			System.out.println("");
			this.atk = 1900;
			this.def = 1200;
			this.name = "바쿠라의 사악한 영혼";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);

		} // 스토리3번 몬스터 1번 진화

		else if (i == 1) {
			System.out.println("하하하하하");
			System.out.println("");
			System.out.println("이 정도냐 겨우");
			System.out.println("");
			System.out.println("내 영혼은 이정도에서 끝이아니다");
			System.out.println("");
			System.out.println("나와라 고대의 석판 다크");
			System.out.println("");
			this.atk = 2900;
			this.def = 3500;
			this.name = "다크";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		} // 스토리3번 몬스터 2번 진화

		else if (i == 2) {
			System.out.println("크아.... 당해낼수없다..");
			System.out.println("");
			System.out.println("이 소리는...");
			System.out.println("");
			System.out.println("내 이름은 사신 조크 이 자의 사악한 마음을");
			System.out.println("");
			System.out.println("제물로삼아 나타났지 ");
			System.out.println("");
			System.out.println("파라오여 너의 삼환신은 두렵지않다");
			System.out.println("");
			this.atk = 3000 + (atk1 / 3);
			this.def = 2500 + (atk1 / 2);
			this.name = "사신 조크";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		} // 스토리3번 몬스터 3번 진화
		else if (i == 3) {
			System.out.println("크아....");
			System.out.println("");
			System.out.println("무엇이냐 이 빛은");
			System.out.println("");
			System.out.println("질수없다 질수없어!!");
			System.out.println("");
			System.out.println("사신조크 =>대사신 조크 네크로파데스 ");
			System.out.println("");
			this.atk = 4000 + (atk1 / 2);
			this.def = 4000 + (atk1 / 2);
			this.name = "대사신 조크 네크로파데스";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		} // 스토리3번 몬스터 4번 진화

	}
	/////////////////////////////// 3번스토리 몬스터//////////////////////////////

	public int storyatk(int atk1, int def1, int i, int storyLv) {

		Loop1: while (true) {
			int rand = ((random.nextInt(5) + 1) % 3) + 1;// 랜덤으로 값을 만들어 공격 수비 스킬중 뭐할지 랜덤으로 생성
			if (rand == 1) {

				int randatk = random.nextInt(4) + 1;
				System.out.println(name + "의 공격력은 " + ((atk1 / randatk)));
				power = atk1 / randatk;

				return power;

			} else if (rand == 2) {

				int randdef = random.nextInt(3) + 1;
				System.out.println(name + "의 수비력은 " + ((def1 / randdef)));
				power = def1 / randdef;
				return power;

			}
//////////////////////////스토리몬스터3번의 공격 수비 출력//////////////

//////////////////////////스킬공격//////////////		

			else if (rand == 3) {

				if (i == storyLv) {
					int rand1 = random.nextInt(2) + 1;
					power = (atk1 * 3) / rand1;
					System.out.println("사신의 어둠" + power);
					return power;
				} else {
					continue Loop1;
				}
			} // 라이벌 스킬

//////////////////////////스킬공격//////////////		
			return power;
		}
	}

	///////////////////////// 공격코드끝
}
