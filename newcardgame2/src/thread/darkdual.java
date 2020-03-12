package thread;

public class darkdual implements Runnable {

	public int total;
	public boolean isLoop = false;
	public int darkdualpoint;
	public darkdual(int total, boolean isLoop,int darkdualpoint) {
		this.total = total;
		this.isLoop=isLoop;
		this.darkdualpoint=darkdualpoint;
	}

	public void close() {
		isLoop = false;
		
	}

	@Override
	public void run() {
		while (isLoop) {
			
			try {
				
				if(total>0) {
					System.out.println("");
					System.out.println("					어둠의 듀얼로 인해 체력이 서서히 줄어듭니다");
					System.out.println("");
					System.out.println("						                   현재 나의 체력은 " + total);
					System.out.println("");
				Thread.sleep(5000);
				total = total - 10;
				
				
				

				if (total <= 50&&total>0) {
					System.out.println("					체력이 50이하입니다 주의가 필요합니다");
				}
				else if(total<=0) {
					System.out.println("				어둠의 듀얼로 인해 체력이 0이 되어 사망하였습니다.");
					System.out.println("				아무 숫자나 입력해주세요.");
				}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}