package humanpkg;

public class nomalchar {
	public int health;
	public int money;
	public String name;
	public int Life;

		public void nomalset(String name,int money,int health)
		{
			this.name=name;
			this.money=money;
			this.health=health;
			
		}


	

		public void rivalset(int Lv) {//라이벌들 진화코드

			if(Lv==0)
			{
				
					health=30;
					money=100;
					name="페가수스";
					
					
			}//1-1번 진화



			else if(Lv==1){
			
				health=50;
				money=200;
				name="바쿠라 ";
				
			
			}//1-2번 진화

			else if(Lv==2){
			health=70;
				money=300;
				name="마리크 ";
		
			}

			else if(Lv==3){
				health=90;
				money=400;
				name="대사신 조크";
		
			}
		
			else if(Lv==4)
			{
				health=150;
				money=500;
				name="아템";
		
			}
		}	//라이벌 셋팅 끝





}
