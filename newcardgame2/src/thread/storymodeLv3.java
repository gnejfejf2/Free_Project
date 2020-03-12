package thread;

public class storymodeLv3 implements Runnable{

	@Override
	public void run() {
		System.out.println("바쿠라 : 너희들의 왕에게 전해라 쿨 에르나의 도적왕 바쿠라가 만나러왔다고");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("바쿠라 : 자 이몸이 방금 전에 파헤친 아크나무카논의 피라미드에서 가져온 것이다.");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("바쿠라 : 더불어 관에있는 이 미라도 가지고 왔지");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("신관들 : 저놈이 감히 왕의 무덤을");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("신관들  : 가만두지 않겠다");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("바쿠라 : 너네정도의 실력을 가지고 나를 무찌를 수 있을까");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
