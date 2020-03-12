package monsterpkg;

import java.util.Scanner;

import thread.storymodeLv2;

public class storyLvmoster2 extends monster {
	public boolean storyloop = true;
	public int power;
	Scanner sc = new Scanner(System.in);

	public void storyset(int i, int atk1) {

		if (i == 0) {
			for (int b = 0; b < 80; b++)
				System.out.println("");
			System.out.println("스킵을 원하시면 0번을 눌러주세요");
			storymodeLv2 story = new storymodeLv2();
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
			//////////////////////////// 스토리 출력/////////////
			System.out.println("너의 신의 카드를 노리기위해 여기까지 왔다!");
			System.out.println("");
			this.atk = 1200;
			this.def = 1800;
			this.name = "그림속에 숨어있는자";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);

		} // 스토리2번 몬스터 1번 진화

		else if (i == 1) {
			System.out.println("하하하하하");
			System.out.println("");
			System.out.println("넌 내함정에 빠진거다!!");
			System.out.println("");
			System.out.println("내 오컬트 카드에는 비밀이 있지");
			System.out.println("");
			System.out.println("다크네크로피아 소환!");
			System.out.println("");
			this.atk = 2200;
			this.def = 2800;
			this.name = "다크네크로피아";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		} // 스토리2번 몬스터 2번 진화
		else if (i == 2) {
			System.out.println("이것마저 격파하다니 내 마지막 수단이다...");
			System.out.println("");
			System.out.println("지옥의 염화");
			System.out.println("");
			System.out.println("용암마신 라바골렘을 소환!");
			System.out.println("");
			System.out.println("내 마지막 수단이다!");
			System.out.println("");
			System.out.println("내몬스터의 수비력은 너의 공격력만큼흡수해가지");
			System.out.println("");
			this.atk = 3000 + (atk1 / 2);
			this.def = 2500 + (atk1);
			this.name = "용암마신 라바골렘";
			System.out.println("		카드 이름 :"+name+"			공격력"+atk+"			수비력"+def);
		} // 스토리2번 몬스터 3번 진화

	}
	/////////////////////////////// 2번스토리 몬스터//////////////////////////////

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
//////////////////////////스토리몬스터2번의 공격 수비 출력//////////////

//////////////////////////스킬공격//////////////		

			else if (rand == 3) {

				if (i == storyLv) {
					int rand1 = random.nextInt(3) + 1;
					power = (atk1 * 2) / rand1;
					System.out.println("다크 네크로피아" + power);
					return power;
				} else {
					continue Loop1;
				}
			} // 라이벌 스킬

//////////////////////////스킬공격//////////////		
			return power;
		}
	}

}
