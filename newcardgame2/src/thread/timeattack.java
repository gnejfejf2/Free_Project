package thread;

public class timeattack implements Runnable{
public boolean isloop=true;

public boolean atkchance=true;
	
	
	@Override
	public void run() {
		int timecount=30;
		while(isloop) {
			try {
				Thread.sleep(1000);
			timecount=timecount-1;
			if(timecount<=3&&timecount>0)
			{
				System.out.println("남은시간은 "+timecount+"초입니다 서둘러주세요!");
			}
			
			
			else if(timecount==0) {
				System.out.println("시간이 다되었습니다. 아무키나 입력해주세요.");
				atkchance=false;
				break;
				
			}
			
			
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
