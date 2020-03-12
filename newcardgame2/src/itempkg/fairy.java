package itempkg;

import thread.Music;

public class fairy extends item {
	public int randomno;

	public void randset(int randomno) {
		this.randomno = randomno;
	}



	public int fairybuy(int mymoney, int num4) {
		Music fairybuy = new Music("fairybuy.mp3", false);
		Thread op = new Thread(fairybuy);
		Music nobuy = new Music("nobuy.mp3", false);
		Thread op1 = new Thread(nobuy);
		if (num4 == 1) {
			if (mymoney >= 1000)// 방해꾼옐로
			{
				atk = 500;
				def = 500;
				name = "방해꾼 옐로";
				op.start();// 아이템 구매 사운드 출력
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				refundmoney = mymoney - 1000;
				randomno = 5;// 랜덤넘버 일반 공격시 넘버 좌우
				return refundmoney;
			} // 오시리스 레드 옷 구매코드
			else {
				op1.start();// 구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			} // 방해꾼옐로

		} // 1번 눌렀을시 구매

		else if (num4 == 2) {// 루비 카방클
			if (mymoney >= 10000) {
				atk = 100;
				def = 100;
				name = "보옥수 루비 카방클";
				op.start();// 아이템 구매 사운드 출력
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				refundmoney = mymoney - 10000;
				randomno = 4;// 랜덤넘버 일반 공격시 넘버 좌우
				return refundmoney;
			} else {
				op1.start();// 구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			}
		} // 2번구매시종료
		else if (num4 == 3) {// 하늘크리보
			if (mymoney >= 30000) {

				atk = 300;
				def = 300;
				name = "하늘크리보";
			
				op.start();// 아이템 구매 사운드 출력
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				randomno = 3;// 랜덤넘버 일반 공격시 넘버 좌우
				refundmoney = mymoney - 30000;
				return refundmoney;
			} else {
				op1.start();// 구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			}
		} else if (num4 == 4) {// 유벨
			if (mymoney >= 100000) {

				atk = 500;
				def = 500;
				name = "유벨";
				op.start();// 아이템 구매 사운드 출력
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("구매가 완료 되었습니다.");
				randomno = 2;// 랜덤넘버 일반 공격시 넘버 좌우
				refundmoney = mymoney - 100000;
				return refundmoney;
			} else {
				op1.start();// 구매실패 사운드
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("돈이 부족합니다 돌아가주세요");
				return 5;
			}
		} // 3번구매 종료
		else {
			return 5;
		}

	}

}
