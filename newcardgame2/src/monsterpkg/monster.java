package monsterpkg;

import java.util.Random;



public class monster{

	public String name;
	public int def;
	public int atk;
	public int Lv;
	public int sppoint;
	public String skillname;
public void set(int atk,int def,int lv,String name)//기본셋팅
	{
		this.atk=atk;
		this.def=def;
		this.Lv=lv;
		this.name=name;
	}

Random random = new Random();






public int storyatk(int atk)//몬스터 공격,수비 출력코드 랜덤넘버 를 받아서 출력
{
	int randatk=random.nextInt(5) + 1;
	System.out.println("내 몬스터의 공격력은 "+atk/randatk);
	return (atk/randatk);
}//기본공격

public int storydef(int def)
{
	int randdef=random.nextInt(4) + 1;
	System.out.println("내 몬스터의 수비력은 "+def/randdef);
	return (def/randdef);
}//기본방어




public int monsteratk(int atk,int randomno,int allitematk)//몬스터 공격,수비 출력코드 랜덤넘버 를 받아서 출력
{
	int randatk=random.nextInt(randomno) + 1;
	System.out.println("내 몬스터의 공격력은 "+((atk/randatk)+allitematk));
	return (atk/randatk);
}//기본공격

public int monsterdef(int def,int randomno,int allitemdef)
{
	int randdef=random.nextInt(randomno-1) + 1;
	System.out.println("내 몬스터의 수비력은 "+((def/randdef)+allitemdef));
	return (def/randdef);
}//기본방어
//////////////////공격 수비////////////////////////////
///////////////////////////////유희//////////////////////////////
public void levelup1(int lvpoint,int Lv) {

	if(Lv==0)
	{
		if(lvpoint>=10)
		{
			this.atk=1900;
			this.def=1200;
			this.name="마도전사 브레이커";
			this.Lv=1;
			System.out.println("나와라 마법사의 견습생!!");
		}
	else
		{
			System.out.println("경험치가 부족합니다 돌아가주세요");
		}
	}//1-1번 진화



	else if(Lv==1){
	if(lvpoint>=10)
	{
		this.atk=2000;
		this.def=1200;
		this.name="블랙 매지션걸";
		this.Lv=2;
		System.out.println("나와라 마법사의 제자!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
	}//1-2번 진화

	else if(Lv==2){
	if(lvpoint>=10)
	{
		this.atk=2500;
		this.def=2000;
		this.name="블랙매지션";
		this.Lv=3;
		System.out.println("최강의 마법사 블랙 매지션!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
	}//1-3번 진화

	else if(Lv==3){
	if(lvpoint>=10)
	{
		this.atk=9000;
		this.def=9000;
		this.name="오시리스의 천공룡(패 9장)";
		this.sppoint=1;
		this.Lv=4;
		System.out.println("소뢰탄!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//1-4번 진화
	else
	{
		System.out.println("최고단계입니다.");
	}
}	//유희 레벨업 끝
///////////////////////////////유희//////////////////////////////

//////////////////////////////카이바//////////////////////////////
public void levelup2(int lvpoint,int Lv) {
	if(Lv==0) {

	if(lvpoint>=10)
	{
		this.atk=2800;
		this.def=1000;
		this.name="xyz케논";
		this.Lv=1;
		System.out.println("xyz를 각각 합체 나와라 xyz케논");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//2-1번 진화

	if(Lv==1) {
	if(lvpoint>=10)
	{
		this.atk=3000;
		this.def=2500;
		this.name="푸른눈의 백룡";
		this.Lv=2;
		System.out.println("내 최강의 카드 푸른눈의 백룡!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
	}//2-2번진화 마무리

	if(Lv==2) { 
	if(lvpoint>=10)
	{
		this.atk=4500;
		this.def=4500;
		this.name="나와라 궁극의 푸른눈의 백룡";
		this.Lv=3;
		System.out.println("나와라 궁극의 푸른눈의 백룡!!!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//2-3번 진화
	if(Lv==3) {
		if(lvpoint>=10)
		{
			this.atk=5000;
			this.def=5000;
			this.name="오벨리스크의 거신병(갓핸드 크러쉬)";
			this.sppoint=1;
			this.Lv=4;
			System.out.println("신의 주먹을 받아라");
		}
		else
		{
		System.out.println("경험치가 부족합니다 돌아가주세요");
		}
	}//2-4번 진화
	else
	{
		System.out.println("최고단계입니다.");
	}
}//카이바 끝
//////////////////////////////카이바//////////////////////////////


///////////////////////////////조이//////////////////////////////
public void levelup3(int lvpoint,int Lv) {
	
	if(Lv==0) {
	if(lvpoint>=10)
	{
		this.atk=1800;
		this.def=1000;
		this.name="철의기사 기어프리드";
		this.Lv=1;
		System.out.println("가라 강철의 기사 기어프리드!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//3-1번 진화

	if(Lv==1){
	if(lvpoint>=10)
	{
		this.atk=2400;
		this.def=2000;
		this.name="천년용";
		this.Lv=2;
		System.out.println("베이비 드래곤은 천년의 시간이 지나 레벨업!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//3-2번 진화

	if(Lv==2){
	if(lvpoint>=10)
	{
		this.atk=3300;
		this.def=2400;
		this.name="붉은눈의 메탈 드래곤";
		this.Lv=3;
		System.out.println("붉은눈은 메탈화가 되어 더 강해진다!");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//3-3번 진화

	if(Lv==3){
	if(lvpoint>=10)
	{
		this.atk=6000;
		this.def=6000;
		this.name="라의 익신룡-갓 피닉스";
		this.sppoint=1;
		this.Lv=4;
		System.out.println("신중에서도 가장 강력한신 라의 익신룡");
	}
	else
	{
		System.out.println("경험치가 부족합니다 돌아가주세요");
	}
}//3-4번 진화
	else
	{
		
	}
}//조이 끝

///////////////////////////////조이//////////////////////////////

///////////////////////////////라이벌//////////////////////////////
public void Rivallevelup(int lvpoint,int Lv) {

	if(Lv==0)
	{
	
			this.atk=1600;
			this.def=1000;
			this.name="툰 마도전사";
			
			
	
	}//라이벌1번 진화



	else if(Lv==1){
	
		this.atk=2000;
		this.def=1200;
		this.sppoint=0;
		this.name="용골귀";
	
	
	}//라이벌2번 진화

	else if(Lv==2){
	
		this.atk=2800;
		this.def=2500;
		this.sppoint=0;
		this.name="이집트 수호병";
		
	
	}//라이벌3번 진화

	else if(Lv==3){
	
		this.atk=3200;
		this.def=3800;
		this.sppoint=0;
		this.name="어둠의 지배자 조크";
		
	
	}//라이벌4번 진화
	else if(Lv==4){
		
		this.atk=4000;
		this.def=4000;
		this.name="호르아크티";
		this.sppoint=1;
		
	
	}//라이벌5번 진화
	else
	{
	
	}
}	
///////////////////////////////라이벌//////////////////////////////





}


