package monsterpkg;

import java.util.Random;
import java.util.Scanner;

public class joymonster extends monster {
	public int skillatk;
	public int brave;
	public int brain;
	public int lucky;

	Scanner sc = new Scanner(System.in);

	Random random = new Random();

	public void stanset(int brave, int brain, int lucky) {
		this.brave = brave;
		this.brain = brain;
		this.lucky = lucky;

	}

	public void statbuy(int num) {// 조이스탯코드 행운만 쓸모있고 나머지는별 의미가없다
		if (num == 1) {
			brave = brave + 5;
			System.out.println("용감하지만 무식하다 공격표시 수비표시 데미지 상승");
		} else if (num == 2) {
			brain = brain + 5;
			System.out.println("0+1은 1일뿐이다");
		} else if (num == 3) {
			lucky = lucky + 5;
			System.out.println("타고난 도박사 역시 조이...!");
		}

	}

	public int power(int atk1, int def1, int skillpoint, int randomno, int allitematk, int allitemdef)// 조이 스킬 구성
	{
		System.out.println("공격과 수비중 어떤 공격을 하시겠습니까.");
		System.out.println("신의 공격 가능 횟수" + skillpoint);
		System.out.println("1.공격 2.수비력 3.신의공격(최종단계만 공격 가능)");
		int num2 = sc.nextInt();
		if (num2 == 1) {
			skillname = "공격표시";
			int randatk = random.nextInt(randomno) + 1;
			skillatk = (((atk1 / randatk) + allitematk) * brave / 100);

			return skillatk;

		} else if (num2 == 2) {
			skillname = "수비표시";
			int randdef = random.nextInt(randomno - 1) + 1;
			skillatk = (((atk1 / randdef) + allitemdef) * brave / 100);
			return skillatk;

		} else if (num2 == 3) {

			if (skillpoint > 0) {
				System.out.println("1.갓피닉스 2.신의 화염 3.프레임샷");
				int num1 = sc.nextInt();
				if (num1 == 1) {
					skillname = "갓피닉스";
					sppoint = skillpoint - 1;
					int rand = random.nextInt(8) + 1;
					skillatk = (((atk1 * 4) / rand) * lucky) / 100;

					return skillatk;
				} else if (num1 == 2) {
					skillname = "신의 화염";
					sppoint = skillpoint - 1;
					skillatk = (int) ((atk1 + (atk1 * (0.5))) * lucky) / 100;

					return skillatk;
				} else if (num1 == 3) {
					skillname = "프레임샷";
					int rand = random.nextInt(2) + 1;
					sppoint = skillpoint - 1;
					skillatk = (((atk1 + def1) / rand) * lucky / 100);

					return skillatk;
				} else
					System.out.println("잘못 입력 하셨습니다.");

			} else {
				System.out.println("스킬포인트가 부족합니다");
				System.out.println("");
				return 0;
			}
		} // 조이의 스킬 공격 3가지선택지

		return 0;
	}// 조이 공격루트

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
	}// 조이 공격루트

}
