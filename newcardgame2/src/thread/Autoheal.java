package thread;

public class Autoheal implements Runnable {
	public int total;
	public boolean isLoop = false;
	public int healing=0;

	public Autoheal(int total, boolean isLoop) {
		this.total = total;
		this.isLoop = isLoop;
		
	}

	@Override
	public void run() {
		if (total < 50 && total >= 0) {
			System.out.println();
			System.out.println("힐링의 샘은 체력이 50미만일때 차오릅니다.");
			System.out.println();
			System.out.println("현재의 체력이 "+total+"이므로 힐링의 샘에 물이 채워지고 있습니다");
			System.out.println();
			System.out.println("힐링의 샘은 최대 10까지만 차오릅니다.");
			System.out.println();
			System.out.println("(주의)생명의 샘의 물은 다른곳을 다녀오면 채워진 물이 사라지게됩니다.");
			System.out.println();
			while (isLoop) {

				try {

					if (total < 50 && total >= 0) {

						Thread.sleep(5000);
						healing = healing+ 1;

					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
