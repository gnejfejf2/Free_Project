package monsterpkg;

import java.util.Random;

import thread.timeattack;

public class boss0 extends monster {
	public int HP;

	Random rd = new Random();

	public int attack(int power, int HP1, int atk, int Lv, double weatherpoint,boolean atkchance) {

		if (atkchance == true) {

			if (HP >= 0) {

				int rand = random.nextInt(8) + 1;// 보스몬스터 랜덤으로 스킬사용하게 만들려고
				if (rand == 10)// 랜드값하고 겹쳐지면 스킬 발동
				{

					HP = (Lv * 3000) + 5000;
					System.out.println("하하하  공격을 무효로 하고 힐링이다");

					return HP;
				} else if (rand == 1)// 랜드값하고 겹쳐지면 스킬 발동
				{

					HP = HP1 - ((int) (power * weatherpoint) / 2);
					System.out.println("하하하 공격 약화!");
					System.out.println("내 원래 데미지는 " + power);
					System.out.println("하지만 보스의 스킬에 의해 데미지가 "+(power/2)+"로 줄어들었습니다.");
					if (weatherpoint == 0.5 || weatherpoint == 2) {
						System.out.println("하지만 날씨의 영향으로 " + ((int) (power * weatherpoint)/2) + "데미지 만큼 적용됩니다.");
					}
					System.out.println("보스의 남은체력은 " + HP + "입니다");


					return HP;
				} else {

					HP = HP1 - (int) (power * weatherpoint);
					System.out.println("내 데미지는 " + power);
					if (weatherpoint == 0.5 || weatherpoint == 2) {
						System.out.println("하지만 날씨의 영향으로 " + (int) (power * weatherpoint) + "데미지 만큼 적용됩니다.");
					}
					System.out.println("보스의 남은체력은 " + HP + "입니다");
					return HP;
				}
			} else {
				
				return HP1;
			}
		}
		
		else if(atkchance == false){
			System.out.println("시간이 지나 공격기회를 상실하였습니다 .");
		
			return HP1;
		}
		else
			return HP1;
	}

}
