package thread;

public class Buffer implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			Thread.sleep(3000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 80; i++)
			System.out.println("");// 화면지우기 코드

	}
}
