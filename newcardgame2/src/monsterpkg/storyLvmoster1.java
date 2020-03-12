package monsterpkg;

import java.util.Scanner;

import thread.storymodeLv1;

public class storyLvmoster1 extends monster {
	public boolean storyloop = true;
	public int power;
	Scanner sc = new Scanner(System.in);

	public void storyset(int i, int atk1) {
		/////////////////////////////////////////////////

		////////////////////////////////////////////////
		if (i == 0) {
			for (int b = 0; b < 80; b++)
				System.out.println("");
			System.out.println("스킵을 원하시면 0번을 눌러주세요");
			storymodeLv1 story = new storymodeLv1();
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
			System.out.println("내 이름은 마리크 구울즈의 리더지 내 구울을 뚫고 나에게 도전해봐라");
			System.out.println("");
			this.atk = 1500;
			this.def = 1500;
			this.name = "리바이벌 슬라임";
			System.out.println("		카드 이름 :" + name + "			공격력" + atk + "			수비력" + def);

		} // 스토리1번 몬스터 1번 진화

		else if (i == 1) {
			System.out.println("내 이름은 마리크 이 순간만을 기다려왔다");
			System.out.println("");
			System.out.println("평생을 파라오의 길잡이로 살아온 일족의 운명을");
			System.out.println("");
			System.out.println("지금 여기서 복수하겠다.");
			System.out.println("");
			System.out.println("내 몬스터는 무덤의 사도");
			System.out.println("");
			this.atk = 1800 + ((atk1 * 2) / 3);
			this.def = 1900 + ((atk1 * 2) / 3);
			this.name = "무덤의 사도";
			System.out.println("		카드 이름 :" + name + "			공격력" + atk + "			수비력" + def);
		} // 스토리1번 몬스터 2번 진화

		else {

		}
	}
	/////////////////////////////// 2번스토리 몬스터//////////////////////////////

	public int storyatk(int atk1, int def1, int i, int storyLv) {

		Loop1: while (true) {
			int rand = ((random.nextInt(5) + 1) % 3) + 1;// 랜덤으로 값을 만들어 공격 수비 스킬중 뭐할지 랜덤으로 생성
			if (rand == 1) {

				int randatk = random.nextInt(4) + 1;
				System.out.println("");
				System.out.println(name + "의 공격력은 " + ((atk1 / randatk)));
				System.out.println("");
				power = atk1 / randatk;

				return power;

			} else if (rand == 2) {

				int randdef = random.nextInt(3) + 1;
				System.out.println("");
				System.out.println(name + "의 수비력은 " + ((def1 / randdef)));
				System.out.println("");
				power = def1 / randdef;
				return power;

			}
//////////////////////////스토리몬스터1번의 공격 수비 출력//////////////

//////////////////////////스킬공격//////////////		

			else if (rand == 3) {

				if (i == storyLv) {
					int rand1 = random.nextInt(3) + 1;
					power = (atk1 * 2) / rand1;
					System.out.println("");
					System.out.println("무덤의 저주" + power);
					System.out.println("");
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
