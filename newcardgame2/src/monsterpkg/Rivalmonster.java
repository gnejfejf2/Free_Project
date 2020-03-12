package monsterpkg;

import java.util.Random;

public class Rivalmonster extends monster {
	public int power;
	Random random = new Random();

//////////////////////////라이벌몬스터 공격 수비 출력//////////////
	public int rivalatk(int atk1, int def1, int randomno) {
		int rand = ((random.nextInt(5) + 1) % 3) + 1;// 랜덤으로 값을 만들어 공격 수비 스킬중 뭐할지 랜덤으로 생성
		if (rand == 1) {
			skillname = "공격 표시";
			int randatk = random.nextInt(randomno + 3) + 1;
			power = atk1 / randatk;

			return power;

		} else if (rand == 2) {
			skillname = "수비 표시";
			int randdef = random.nextInt(randomno + 1) + 1;
			power = def1 / randdef;
			return power;

		}
//////////////////////////라이벌몬스터 공격 수비 출력//////////////

//////////////////////////스킬공격//////////////		

		else if (rand == 3) {
			int rand2 = random.nextInt(3) + 1;
			// 랜덤으로 값을 만들어 어떤 특수 공격을 할것인지 생성
			if (rand2 == 1) {
				skillname = "특수공격";
				int rand1 = random.nextInt(10) + 1;
				power = (atk1 * 2) / rand1;

				return power;
			} else if (rand2 == 2) {
				skillname = "특수공격";
				power = (atk1 + def1) / 3;

				return power;
			} else if (rand2 == 3) {
				skillname = "특수공격";
				int rand1 = random.nextInt(8) + 1;
				power = (atk1 + def1) / rand1;

				return power;
			} else
				System.out.println("잘못 입력 하셨습니다.");

		} // 라이벌 스킬 선택문

//////////////////////////스킬공격//////////////		
		return power;
	}
	public int randatk(int atk1, int def1, int randomno) {
		int rand = random.nextInt(30) % 2;
		if (rand == 0) {
			skillname = "공격 표시";
			int randatk = random.nextInt(randomno + 3) + 1;
			power = atk1 / randatk;

			return power;

		} else if (rand == 1) {
			skillname = "수비 표시";
			int randdef = random.nextInt(randomno + 1) + 1;
			power = def1 / randdef;
			return power;

		}
		return 0;
	}
}
