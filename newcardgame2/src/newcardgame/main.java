package newcardgame;

import java.util.Scanner;

import humanpkg.human;
import humanpkg.nomalchar;
import itempkg.cloth;
import itempkg.fairy;
import itempkg.item;
import monsterpkg.Rivalmonster;
import monsterpkg.boss0;
import monsterpkg.joymonster;
import monsterpkg.kaimonster;
import monsterpkg.monster;
import monsterpkg.storyLvmoster0;
import monsterpkg.storyLvmoster1;
import monsterpkg.storyLvmoster2;
import monsterpkg.storyLvmoster3;
import monsterpkg.storyLvmoster4;
import monsterpkg.yugimonster;
import thread.Autoheal;
import thread.Bossattack;
import thread.Buffer;
import thread.Event;
import thread.EventMatch;
import thread.Music;
import thread.darkdual;
import thread.endingstory;
import thread.newevent;
import thread.open;
import thread.timeattack;
import thread.weather;

public class main {
	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		/////////////////////////// 객체 생성//////////////
		human myhuman = new human();
		monster mymonster;
		Rivalmonster rivalmonster = new Rivalmonster();
		item myitem = new item();
		cloth myitem1 = new cloth();
		fairy myitem2 = new fairy();
		nomalchar rival = new nomalchar();
		int Musicselect = 0;
		boolean storyloop = true;
		///////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
		System.out.println("			스킵을 원하시면 0번을 눌러주세요");
		Music intromusic = new Music("introMusic.mp3", false);
		intromusic.start();
		open open = new open();
		Thread op = new Thread(open);
		op.start();
/////////////////////////////////////////////////// 내아이템 라이벌 기본 셋팅////////////////////
		while (storyloop) {
			int skipnum = sc.nextInt();
			if (skipnum == 0) {
				for (int j = 0; j < 80; j++)
					System.out.println("");
				intromusic.close();
				op.stop();
				storyloop = false;
			} else {
				intromusic.close();
			}
		}
		///////////// 로딩하는 시간 동안 잠재움/////////
		try {
			op.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//////////////////////////////////////
		intromusic.close();
		Loop2: while (true) {
			System.out.println("					당신이 플레이 할 캐릭터를 골라주시게");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.print("			1.유희");
			System.out.print("			2.카이바");
			System.out.print("			3.조이");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			int num = sc.nextInt();
			rivalmonster.set(1600, 1200, 0, "걸어다니는좀비");
			rival.nomalset("마리크", 0, 0);
			myitem.itemset(0, 0, "기본듀얼디스크");// 0번아이템 듀얼디스크
			myitem1.itemset(0, 0, "기본옷");// 1번아이템 옷
			myitem2.itemset(0, 0, "정령이 안보입니다.");// 2번아이템정령
			myitem2.randomno = 5;
			/////////////////////////////////////////////

			if (num == 1) { // 캐릭터 선택코드//유희선택시 기본셋팅

				mymonster = new yugimonster();// 유희형변화

				System.out.println("\r\n" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#;@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*;;@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@;;;;@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@;;#;;=@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@;;*=;;$@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@@$;*.!;;@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@@@@@*;;.-!;;$@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@*@@@@@@@@@;;* @#!;;@@@@@@@@@$#!;$@@\r\n"
						+ "@@@@@@@@@@@@@@@*;@@@@@@@$;;; $@$;;@@@@@@@*;;;=!@@@\r\n"
						+ "@@@@@@@@@@@@@@@**;@@@@@@;;!  $#$;;@@@$$;;;;;,;@@@@\r\n"
						+ "@@@@@@@@@@@@@@@;;;;=@@@@;!$=.$$$;;=@=;;;;;:-;$@@@@\r\n"
						+ "@@@@@@@@@@@@@@@*;=;;;@@!;!= #$$$;;;;;;;;$.~;*@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@;:*;;;=;*$# #$$$*;;!;!$$ ~;;@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@;;$$$;;;#$  $$$$=;$=#$- ;;;$@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@;*.$$@;;$#  $$$$=@$$$: :*;;@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@;! ;#$$;$=  $=#@$$$#  !$;;$@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@!;@  @$$$@; $ ~ $$#= ~$#;!@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@*;$  !.#-.$. *:;!  -$$;;@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@;;#$ .~, $  !-,   $$=;;;;!!;;#@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@*;*@=@ ~ ~# - -~.***;;;;;;;;;;*@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@;;$ @=, ~!=       ==:@@=$#=;#@@\r\n"
						+ "@@@@@@@@@@@@@#$!;;;;;=.. #@-   =.;$$$=,, .$#;*@@@@\r\n"
						+ "@@@@@@@@@@@@@!;*#$=$$ ~ ,    .      ...:@#*!@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@;;$$# #       =    @@@#   .*@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@=;#*~ ;$     ;@!=@==;-$#=~-.@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@!;$.;! @   $.* #. . -@;@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@!$*:.#$    :*;. - .!@=!;@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@$-# # ,   ,  :~.  @##;#@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@  ~. ,        ~-@$#*@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@  #  !       ~#$*$@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@@ ;          # #;@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@*.@@@@@@@@ @ @!    -;=;;*@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@#@$;;;$@@@@@@@@=@.@@$    #    .@@@@@@@@@@@@@\r\n"
						+ "@@@@@@$=;;;;@@@@@@@@@=@~@*@@:!-#     ;@@@@@@@@@@@@\r\n"
						+ "@@@@#;!=;#$;!@@@@@@@@#@@@: :~#-#     ;@@@@@@@@@@@@\r\n"
						+ "@@@$;!@:=@=;=@@@@@@@@@!@:  ;@=-#     ;@@@@@@@@@@@@\r\n"
						+ "@@@:!$=@=#=!=#@@@@@@@@@@#! ~@=-# *$~~@@@@@@@@@@@@@\r\n"
						+ "@$*;$,=;#~=!!=@@@@@@@@@@@~.$@#=,;     :@@@@@@@@@@@\r\n"
						+ "@=;@#,:#@ $;;@@@@@@@@@@.  #;   !   .   !@@@@@@@@@@\r\n"
						+ "-;;@@@ *; !;!*@@@@@@@@@.  @;.    :  .   @@@@@@@@@@\r\n"
						+ "@@*;-*~ :,$@=@@@@@@@@@@# *!$     ~!~ ; #@@@@@@@@@@\r\n"
						+ "@@-#*.@;;   ;@@@@@@@@@@. #;#  , ;.$~-: =@@@@@@@@@@\r\n"
						+ "@@@@~#-;    ~@@@@@@@@@@.:*;$    ;     -@@@@@@@@@@@\r\n"
						+ "@@@@@@! !-~  :@@@@@@@@~ #;!# , *--  ;*-@@@@@@@@@@@\r\n"
						+ "@@@@@@@.#.:   -@@@@@@@@.@#@;   . ~ .   !@@@@@@@@@@\r\n"
						+ "@@@@@@@@,      :-@@@@@~$;@#;   :.~!;:  @@@@@@@@@@@");
				System.out.println("			유희가 선택 되었습니다.");
				myhuman.set("유희", 0, 0, 100); // 유희 셋팅
				mymonster.set(1400, 1200, 0, "엘프검사");// 몬스터 셋팅
				((yugimonster) mymonster).stanset(100, 100, 100);/// 스텟 새팅

			} else if (num == 2) {// 카이바선택시 기본셋팅
				mymonster = new kaimonster();
				System.out.println("@@@@@@@@**@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@$==@@@#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@*==@@$=@@@@@@@@;$~#:=@@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@==~*@@=*@@@==@@@@@@@@:!!!!!*@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@*;-~$@##@@@!@@@@@@@@!;;!;!;!$@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "~-!,:~#@#@@@#@@@@@@@@:;;;;;:;*@@@@@@@@@@@@@@@@@@@@\r\n"
						+ ";:  --*#=$@@#@@@@@@ :::::;;!;!!!@@@@@@@@@@@@@@@@@@\r\n"
						+ "!-, ;:=#=!==#@#@@@! !!;*;;!!*;=@@@@@@@@@@@@@@@@@@@\r\n"
						+ ",*;,--!###$*=*#$$* ~!!!!;*!!!==$@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@--  -$######*#*;-:$!*!$*$**=*$@@@@@@@@@@@@@@@@@@\r\n"
						+ "@=:, !:~####@###=!  !==;$===#$ #@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@--,   #@@####$$    :=:##;,.*= @@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@,   $@@@#@$#$.,  ~~,,#-  ~, =@@@ -   =@@@@@@@@\r\n"
						+ "@@@@,,..!-@@@@@@@#* ,--- :. -~,:~.. -  .,@@@@@@@@@\r\n"
						+ "@@@@ - ~-,~ -@@@@;.-   ;-~~,:,,~   *; ~--@@@@@@@@@\r\n"
						+ "@@@@ *.,#@,,,@@@@-,-.  :@-.  ,,      -*~-@@@@@@@@@\r\n"
						+ "@@@@ ,;#@@@@@@@@@@.    ;##@# -,     ,==:,@@@@@@@@@\r\n"
						+ "@@@@ -  @@@@@@@@@@=  ,:===#=;.   - ,==**@@@@==@@@@\r\n"
						+ "@@@@@!-:@@@@@@@@@@@  -!===,;~,   ; ~===#@@!===@@@@\r\n"
						+ "@@@@@@@.*@@@@@@@@@@ -,!$==*.~    ..#==##@#==@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@. ,=====!:!    ~#=$#$##@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@#!;$$#== ~,   .#$#$=;$@@@@*===@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@-*$$$==,   ,,;=$@!$*=@@#$!==@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@ #=##==.; ,*~~-~##$=!=:##;@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@ =~####= : ,,,-~,@##$!=#:@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@=!*$!$$##  ,-,.:@@@##$!$=$@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@;@@@=$=$$==$~ .,,:@@@@@@###$==@@@@@@\r\n");
				System.out.println("			카이바가 선택 되었습니다.");
				myhuman.set("카이바", 0, 0, 100);
				mymonster.set(1900, 1000, 0, "블러드 불스");
				((kaimonster) mymonster).stanset(100, 100, 100);

			} else if (num == 3) {// 조이선택시 기본셋팅
				mymonster = new joymonster();
				System.out.println("@@@@@@@@@@@@@@@@~-@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@---=@@@@@@@@#@@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@:,,:,----;!@$.@@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@,.,,--,,--::~ @@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@.-@@*-,,,-,-,,,-,,@@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@--,~,,-,,,,,,,,,:-@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@-,,,,--,,,,,,,,,,,@@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@,.,-,,,,,-,,-,,,,;;@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@.,,,,,,,,-,--,,,,--@@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@--,,,,--,,,.-,-~-.~:@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@-~-,-,,,-,,,,--~-.,~@@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@:,,-,-;~,~,--;,-~~:;-;@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@--~~~-;,,~:~~:.::~-*:::@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@~-;**,~:,--:*;=-;-.#;=@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@.;::~!=*!==!#!*:;*.::-@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@;!*:=:-!=;!~=:**--::~-@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@=;!::~-,----,-,:!;!-@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@*!!;,..: .. .;;::::$:@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@@=- .... ,;;:;;~,@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@@#.;, ..*-#!:==!*~@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@$!.;;...$!@#!;;:=@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@*;,~$...==@@$~=~-@@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@@@@@;!~,! ..!@@@@##:;@@~@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@:::@;#!~.,..,~#@@@*@;==@@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@=@:;*$,..~!!~;*==!****$@@@#!@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@~@~!:::.,!,!;;!****;$@@#$@#$$@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@#@;;::: ,-.~~::*!;::@@=!*$@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@;@#;::::....,~::::::@$;!**#@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@*!##::::;;~ .:!::::;:=;=*#@@@@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@*=;~:::-.   -::::*::*==;!*$*!@@@@@@@@\r\n"
						+ "@@@@@@@@@@@@@@$=::~:~.   ,;~~~:=~:::*$#$*=@@@@@@@@\r\n"
						+ "@@@@@@@@@@@:**@=::-:-    ,!:~~:@#=::~;*#@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@=:@@@*:::-*  ~!!:::~:@@@!-:;*@@@@@@@@@@\r\n"
						+ "@@@@@@@@@@@*:!*$;:::-    -:::::$!##@:~~;*#@@@@@@@@\r\n");
				System.out.println("			조이가 선택 되었습니다.");
				myhuman.set("조이", 0, 0, 100);
				mymonster.set(1200, 700, 0, "베이비 드래곤");
				((joymonster) mymonster).stanset(100, 100, 100);

			} // 캐릭터 선택코드 마무리
			else {

				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();

				continue Loop2;
			}
			break;
		} /// 캐릭터 선택창 종료
		Loop1: while (true) // 게임 진행코드
		{
			Buffer buffer = new Buffer();
			Thread buf = new Thread(buffer);
			buf.start();
			try {
				buf.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/////////////// 체력 0이하일때 관리////////////
			if (myhuman.health <= 0) {
				myhuman.health = 10;

				if (myhuman.money >= 500) {
					myhuman.money = myhuman.money - 500;
					System.out.println("가진돈이 500원 이하일시 부활하지 않고 게임이 종료됩니다.");
				} else if (myhuman.money < 500) {
					System.exit(0);
				}

			}

			//////////////////////////////////// 음악 관리
			Music introMusic = new Music("TitleMusic.mp3", false);// 뮤직에 false값을 넣어서 한번만 재생하도록

			if (Musicselect == 0) {

				introMusic.start();
			} // 음악이 실핼될지 말지 결정하는 코드

			///////////////////////////////////// 오토힐 코드
			Autoheal heal = new Autoheal(myhuman.health, true);
			Thread healop = new Thread(heal);

			///////////////////////////////////// 이벤트매치 코드
			EventMatch event = new EventMatch(myhuman.money, true);
			Thread op1 = new Thread(event);
			op1.start();
			////////////////////////////////////////////
			System.out.println("			__   __                _____  _          _____  _      _ \r\n"
					+ "			\\ \\ / /               |  __ \\(_)        |  _  || |    | |\r\n"
					+ "    			 \\ V /  _   _  ______ | |  \\/ _  ______ | | | || |__  | |\r\n"
					+ "   	  		  \\ /  | | | ||______|| | __ | ||______|| | | || '_ \\ | |\r\n"
					+ "   			  | |  | |_| |        | |_\\ \\| |        \\ \\_/ /| | | ||_|\r\n"
					+ "   			  \\_/   \\__,_|         \\____/|_|         \\___/ |_| |_|(_)\r\n");

			/////////////////////////////////////////////////////////////
			int allitematk = myitem.atk + myitem1.atk + myitem2.atk;
			int allitemdef = myitem.def + myitem1.def + myitem2.def;
			System.out.print("****************************************************");
			System.out.print("**********게임시작**********");
			System.out.print("****************************************************");
			System.out.println();
			System.out.println();
			System.out.println("		캐릭터 : " + myhuman.name + "     스토리 레벨 : " + myhuman.storyLv + "     체력 : "
					+ myhuman.health + "     돈 : " + myhuman.money);
			System.out.println();
			System.out.println("		체력이 0이하로 떨어질 가진돈이 500원 감소하고 체력 10으로 부활합니다.");
			System.out.println();
			System.out.println("		(가진돈이 500원 이하 일시 게임에서 패배하게됩니다.)");
			System.out.println();
			System.out.println("		카드이름 : " + mymonster.name + "     몬스터의 공격력  : " + mymonster.atk
					+ "     몬스터의 수비력 : " + mymonster.def + "     Lvpoint : " + myhuman.Lvpoint);
			System.out.println();
			System.out.println(
					"		듀얼디스크 : " + myitem.name + "     옷 : " + myitem1.name + "     듀얼 정령 : " + myitem2.name);
			System.out.println();
			System.out.println("		추가 공격력  : " + allitematk + "     추가 수비력 : " + allitemdef);
			System.out.println();

			System.out.print("*********************************************************");
			System.out.print("진행할 행동을 선택해주세요");
			System.out.println("********************************************************");

			System.out.print("		0.자동대결");
			System.out.print("			1.스토리 모드");
			System.out.print("		2.일반대결");
			System.out.print("			3.보스 대결");
			System.out.println();
			System.out.println();
			System.out.print("		4.상점");
			System.out.print("			5.피로 회복");
			System.out.print("		6.특성 상승");
			System.out.print("		7.가이드");
			System.out.println();
			System.out.println();
			System.out.print("		8.노래 on/off");
			System.out.println();
			System.out.println();
			healop.start();
			int num1 = sc.nextInt();
			if (num1 == 0) {// 8번 자동전투 생성쓰레드

				for (int i = 0; i < 80; i++)
					System.out.println("");// 줄띄우기
				introMusic.close();// 음악 종료
				event.isLoop = false;// 이벤트루프 종료

				int power = 0;// 내 데미지
				boolean doubleloop = true;
				myhuman.Life = (4000 * mymonster.Lv) + 4000;// 내라이프 셋팅 추후에 변할수도있음
				rival.Life = (4000 * mymonster.Lv) + 4000;// 라이벌 라이프 셋팅
				rival.rivalset(mymonster.Lv);// 라이벌 셋팅
				System.out.println(rival.name + "와의 대결이 시작됩니다.");
				System.out.println("자동전투가 진행됩니다.");//

				while (true) {
					if (myhuman.name == "유희") {

						power = ((yugimonster) mymonster).randpower(mymonster.atk, mymonster.def, myitem2.randomno,
								allitematk, allitemdef);
					} else if (myhuman.name == "카이바") {
						power = ((kaimonster) mymonster).randpower(mymonster.atk, mymonster.def, myitem2.randomno,
								allitematk, allitemdef);
					} else if (myhuman.name == "조이") {
						power = ((joymonster) mymonster).randpower(mymonster.atk, mymonster.def, myitem2.randomno,
								allitematk, allitemdef);
					}

					int rivalatk = rivalmonster.randatk(rivalmonster.atk, rivalmonster.def, myitem2.randomno);
					// 라이벌 공격력을 뽑아내는 코드
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					/////////////////////////////// 공격력 뽑아내기 기초셋팅 끝////////////////////////////////
					Event ev = new Event(power, doubleloop, rival.Life);
					Thread event1 = new Thread(ev);
					newevent ev2 = new newevent(rivalatk, ev.isloop, myhuman.Life);
					Thread event2 = new Thread(ev2);
					event1.start();
					event2.start();

					try {
						event1.join();
						event2.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					rival.Life = ev.rvLife;
					myhuman.Life = ev2.myLife;

					if (doubleloop == true) {
						doubleloop = false;
					} else if (doubleloop == false) {
						doubleloop = true;
					}

					if (myhuman.Life < 0 && rival.Life < 0) {
						System.out.println("무승부입니다.");
						System.out.println("메인 화면으로 돌아갑니다.");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					}

					else if (myhuman.Life < 0) {

						System.out.println("패배하였습니다.");
						System.out.println("보상획득에 실패하였습니다.");
						System.out.println("패배 패널티로 인해 체력이 감소합니다.");
						myhuman.health = myhuman.health - 3;
						event1.interrupt();
						event2.interrupt();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					} else if (rival.Life < 0) {
						System.out.println("승리하였습니다.");
						System.out.println("보상획득에 성공하였습니다.");
						System.out.println("보상으로 소량의 돈을 획득 하였습니다");
						System.out.println("패배 패널티로 인해 체력이 감소합니다.");
						myhuman.money = myhuman.money + rival.money;
						myhuman.health = myhuman.health - 1;
						event1.interrupt();
						event2.interrupt();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					}

				}

			} else if (num1 == 1) {
				event.isLoop = false;
				introMusic.close();
				Music storyMusic = new Music("storymodeLv4.mp3", false);
				storyMusic.start();
				int storypower = 0;
				int power = 0;
				int storypoint = 0;
				int temp = mymonster.sppoint;// 몬스터의 스킬포인트를 임시로 저장 추후에 돌려주기위해
				mymonster.sppoint = mymonster.sppoint + myitem1.skipppointup;// 캐릭터의 스킬포인트와 아이템스킬포인트결합
				while (true) {
					for (int i = 0; i < myhuman.storyLv + 1; i++) {
						if (myhuman.storyLv == 0) {
							storyLvmoster0 storymonster = new storyLvmoster0();
							storymonster.storyset(i, mymonster.atk);

							if (myhuman.name == "유희") {

								power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "카이바") {
								power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "조이") {
								power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							}
							if (power > 0) {
								System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);

							} else if (power == 0) {
								System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							}
							storypower = storymonster.storyatk(storymonster.atk, storymonster.def, i, myhuman.storyLv);

							if (power >= storypower) {
								System.out.println("승리하였습니다!");

								storypoint = storypoint + 1;
								try {

									Thread.sleep(1000);

								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								continue;

							} else {
								System.out.println("패배하였습니다ㅜㅜ");

								myhuman.health = myhuman.health - 1;

								break;

							}

						}
						////////////////////////////// 스토리 0단계 종료////////////////////////////

						else if (myhuman.storyLv == 1) {
							storyLvmoster1 storymonster = new storyLvmoster1();
							storymonster.storyset(i, mymonster.atk);

							if (myhuman.name == "유희") {

								power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "카이바") {
								power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "조이") {
								power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							}
							if (power > 0) {
								System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);
							} else if (power == 0) {
								System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							}
							storypower = storymonster.storyatk(storymonster.atk, storymonster.def, i, myhuman.storyLv);

							if (power >= storypower) {
								System.out.println("승리하였습니다!");

								storypoint = storypoint + 1;
								continue;

							} else {
								System.out.println("패배하였습니다ㅜㅜ");

								myhuman.health = myhuman.health - 2;

								break;

							}

						}
//////////////////////////////스토리 1단계 종료////////////////////////////

						else if (myhuman.storyLv == 2) {
							storyLvmoster2 storymonster = new storyLvmoster2();
							storymonster.storyset(i, mymonster.atk);

							if (myhuman.name == "유희") {

								power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "카이바") {
								power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "조이") {
								power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							}
							if (power > 0) {
								System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);

							} else if (power == 0) {
								System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							}
							storypower = storymonster.storyatk(storymonster.atk, storymonster.def, i, myhuman.storyLv);

							if (power >= storypower) {

								System.out.println("승리하였습니다!");
								storypoint = storypoint + 1;
								continue;

							} else if (power < storypower) {

								System.out.println("패배하였습니다ㅜㅜ");
								myhuman.health = myhuman.health - 3;
								break;

							}

						}
//////////////////////////////스토리 2단계 종료////////////////////////////

						else if (myhuman.storyLv == 3) {
							storyLvmoster3 storymonster = new storyLvmoster3();
							storymonster.storyset(i, mymonster.atk);

							if (myhuman.name == "유희") {
								power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "카이바") {
								power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "조이") {
								power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							}
							if (power > 0) {
								System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);
							} else if (power == 0) {
								System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							}
							storypower = storymonster.storyatk(storymonster.atk, storymonster.def, i, myhuman.storyLv);

							if (power >= storypower) {

								System.out.println("승리하였습니다!");
								storypoint = storypoint + 1;
								continue;

							} else {

								System.out.println("패배하였습니다ㅜㅜ");
								myhuman.health = myhuman.health - 2;
								break;

							}
						}
//////////////////////////////스토리 3단계 종료////////////////////////////

						else if (myhuman.storyLv == 4) {
							storyLvmoster4 storymonster = new storyLvmoster4();
							storymonster.storyset(i, mymonster.atk);

							if (myhuman.name == "유희") {

								power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "카이바") {
								power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							} else if (myhuman.name == "조이") {
								power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
										myitem2.randomno, allitematk, allitemdef);
							}
							if (power > 0) {
								System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);
							} else if (power == 0) {
								System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							}
							storypower = storymonster.storyatk(storymonster.atk, storymonster.def, i, myhuman.storyLv);

							if (power >= storypower) {

								System.out.println("승리하였습니다!");
								storypoint = storypoint + 1;
								continue;

							} else {

								System.out.println("패배하였습니다ㅜㅜ");
								myhuman.health = myhuman.health - 2;

								break;

							}

						}

					}

					if (storypoint == myhuman.storyLv + 1) {
						storyMusic.close();
						System.out.println("축하드립니다 승리하셨습니다!");
						myhuman.storyLv = myhuman.storyLv + 1;
						myhuman.health = myhuman.health - 1;
						try {

							Thread.sleep(1000);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (myhuman.storyLv == 5) {

							endingstory end = new endingstory();
							Thread en = new Thread(end);
							en.start();
							try {
								en.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.exit(0);
						}
						if (mymonster.Lv == 4) {
							mymonster.sppoint = temp;// 스킬포인트 돌려주기
						}

						continue Loop1;
					} else if (storypoint < myhuman.storyLv + 1) {
						storyMusic.close();
						System.out.println("스토리모드 도전에 실패하셨습니다.!");
						myhuman.health = myhuman.health - 3;
						try {

							Thread.sleep(1000);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (mymonster.Lv == 4) {
							mymonster.sppoint = temp;// 스킬포인트 돌려주기
						}
						continue Loop1;
					}
					storyMusic.close();
				}

			} // 스토리모드 완료//////////////////////////////////////

			//////////////////////// 라이벌 대결 시작
			else if (num1 == 2) {
				for (int i = 0; i < 80; i++)
					System.out.println("");
				introMusic.close();// 음악 종료
				event.isLoop = false;// 이벤트루프 종료
				Music rivalMusic = new Music("rivalMusic.mp3", false);
				rivalMusic.start();
				darkdual dark = new darkdual(myhuman.health, true, 1);
				Thread thread = new Thread(dark);

				int healthtemp = myhuman.health;
				int power = 0;// 내 데미지

				myhuman.Life = 4000;// 내라이프 셋팅 추후에 변할수도있음
				rival.Life = (4000 * mymonster.Lv) + 3000;// 라이벌 라이프 셋팅
				rival.rivalset(mymonster.Lv);// 라이벌 셋팅
				System.out.println(rival.name + "와의 대결이 시작됩니다.");
				System.out.println("");
				System.out.println("내 라이프 : 4000 " + rival.name + " 라이프 : " + rival.Life);
				System.out.println("");
				int temp = mymonster.sppoint;// 몬스터의 스킬포인트를 임시로 저장 추후에 돌려주기위해
				mymonster.sppoint = mymonster.sppoint + myitem1.skipppointup;// 캐릭터의 스킬포인트와 아이템스킬포인트결합

				System.out.println("1.일반듀얼  2. 어둠의 듀얼");
				System.out.println("");
				System.out.println("어둠의 듀얼이란?");
				System.out.println("");
				System.out.println("어둠의 듀얼실행시 일정시간마다 체력이 줄어들게됩니다(체력은 라이프와 다릅니다.)");
				System.out.println("");
				System.out.println("체력이 0이 되면 남은 라이프와 상관없이 패배하게됩니다.");
				System.out.println("");
				System.out.println("어둠의 듀얼을 상태에서 승리하게 되면 보상이 5배가 됩니다.");
				System.out.println("");

				int num2 = sc.nextInt();
				{
					if (num2 == 2) {
						dark.darkdualpoint = 5;
						System.out.println("모든 보상이 5배가 됩니다.");
						thread.start();

					}
				}

				while (true) {

					if (myhuman.name == "유희") {

						power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					} else if (myhuman.name == "카이바") {
						power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					} else if (myhuman.name == "조이") {
						power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					}

					int rivalatk = rivalmonster.rivalatk(rivalmonster.atk, rivalmonster.def, myitem2.randomno);
					// 라이벌 공격력을 뽑아내는 코드

					myhuman.health = dark.total;

					if (myhuman.health <= 0) {
						dark.close();
						rivalMusic.close();
						System.out.println(dark.isLoop);

						myhuman.health = (healthtemp / 2);
						System.out.println("어둠의 듀얼로인해 패배하였습니다 체력 " + myhuman.health + "으로 부활합니다.");
						try {

							Thread.sleep(1000);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue Loop1;
					} else {
						if (power > 0) {
							System.out.println("");
							System.out.println("");
							System.out.println("가라 내 몬스터는 " + mymonster.skillname + " 전투력은 " + power);
							System.out.println("");
							System.out.println("라이벌몬스터는  " + rivalmonster.skillname + " 전투력은" + rivalatk);
							System.out.println("");
						} else if (power == 0) {
							System.out.println("");
							System.out.println("");
							System.out.println("잘못된 선택으로 공격기회를 잃어버렸습니다.");
							System.out.println("");
							System.out.println("라이벌몬스터는  " + rivalmonster.skillname + " 전투력은" + rivalatk);
							System.out.println("");
						}

						if (rivalatk <= power) {
							rival.Life = rival.Life - (power - rivalatk);
							System.out.println("");
							System.out.println("");
							System.out.println("나의 남은 라이프 포인트은" + myhuman.Life);
							System.out.println("");
							System.out.println("라이벌의 남은 라이프 포인트은" + rival.Life);
							System.out.println("");
						} // 파워를 비교하고 라이프 대입

						else {
							myhuman.Life = myhuman.Life - (rivalatk - power);
							System.out.println("");
							System.out.println("");
							System.out.println("나의 남은 라이프 포인트은" + myhuman.Life);
							System.out.println("");
							System.out.println("라이벌의 남은 라이프 포인트은" + rival.Life);
							System.out.println("");
						} // 파워를 비교하고 라이프 대입

						if (rival.Life <= 0) {
							dark.close();// 스레드를 멈춰주기위해 필요
							rivalMusic.close();// 음악재생 멈추기
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("축하드립니다 승리하셨습니다!");
							myhuman.money = myhuman.money + (rival.money * dark.darkdualpoint);// 라이벌의 돈을 가져옴

							myhuman.health = myhuman.health - 1;// 내 캐릭터의 체력이 깎임
							try {

								Thread.sleep(1000);

							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (mymonster.Lv == 4) {
								mymonster.sppoint = temp;
							} // 스킬포인트를 사용한만큼 다시 돌려줌
							break;
						} else if (myhuman.Life <= 0) {
							dark.close();// 어둠의 듀얼 멈추기
							rivalMusic.close();// 음악재생멈추기
							try {

								Thread.sleep(1000);

							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("패배 하였습니다!");
							myhuman.health = dark.total;
							myhuman.health = myhuman.health - 3;// 졌을시 체력 감소

							if (mymonster.Lv == 4) {
								mymonster.sppoint = temp;
							} // 스킬포인트를 사용한만큼 다시 돌려줌
							break;

						}

					}

				}

			} // 일반대결

//////////////////////////////일반대결 마무리/////////////////////	

			else if (num1 == 3) {
				Music bossmusic = new Music("bossMusic.mp3", false);
				Thread bossop = new Thread(bossmusic);
				bossop.start();
				introMusic.close();
				int power = 0;
				myhuman.Life = 4000;

				event.isLoop = false;
				////////////////////////////////

				for (int i = 0; i < 80; i++)
					System.out.println("");
				boss0 boss = new boss0();

				int HP1 = (mymonster.Lv * 3000) + 5000;// 보스몬스터 체력 생성 기준표
				boss.set(2000 * mymonster.Lv, 1000 * mymonster.Lv, mymonster.Lv, "암흑의왕 조커");
				int temp = mymonster.sppoint;// 몬스터의 스킬포인트를 임시로 저장 추후에 돌려주기위해
				mymonster.sppoint = mymonster.sppoint + myitem1.skipppointup;// 캐릭터의 스킬포인트와 아이템스킬포인트결합
				System.out.println("보스대결을 시작합니다.");
				System.out.println("");
				System.out.println("");
				System.out.println("시작 라이프:4000");
				System.out.println("");
				System.out.println("");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				///////////////////////// 보스공격
				Bossattack bossattack = new Bossattack(myhuman.Life, mymonster.Lv, true, 1, 0);
				Thread op4 = new Thread(bossattack);

				////////////////////////////////////////
				System.out.println("1. 노말모드 2. 하드모드");
				System.out.println("");
				System.out.println("하드모드란?");
				System.out.println("");
				System.out.println("일정시간마다 보스가 자신의 몬스터를 공격합니다.");
				System.out.println("");
				System.out.println("라이프가 0이 되면 패배하게 됩니다.");
				System.out.println("");
				System.out.println("하드모드에서 승리하게 되면 노말모드보다 보상이 3배가 됩니다.");
				System.out.println("");
				int num2 = sc.nextInt();
				{
					if (num2 == 2) {
						bossattack.point = 3;
						System.out.println("모든 보상이 3배가 됩니다.");
						op4.start();

					}
				}
				////////////////////////// 보스공격 쓰레드
				for (int i = 0; i < 5; i++) {// 보스몬스터의 공격은 총 5회
					System.out.println("");
					System.out.println("");
					System.out.println(mymonster.Lv + "번 보스와 전투가 시작됩니다(남은체력 : " + HP1 + ")");
					System.out.println("");
					System.out.println("");
					weather wea = new weather();
					Thread op2 = new Thread(wea);// 날씨 스레드 실행
					op2.start();
					//////////////////////// 날씨쓰레드
					timeattack time = new timeattack();
					Thread op3 = new Thread(time);// 시간재는 쓰레드 실행
					op3.start();
					//////////////////////// 시간제한 쓰레드

					if (myhuman.name == "유희") {
						power = ((yugimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					} else if (myhuman.name == "카이바") {
						power = ((kaimonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					} else if (myhuman.name == "조이") {
						power = ((joymonster) mymonster).power(mymonster.atk, mymonster.def, mymonster.sppoint,
								myitem2.randomno, allitematk, allitemdef);
					}
					myhuman.Life = bossattack.life;
					if (myhuman.Life <= 0) {
						time.isloop = false;
						wea.isLoop = false;
						bossattack.isLoop = false;
						break;
					}
					HP1 = boss.attack(power, HP1, boss.atk, boss.Lv, wea.weatherpoint, time.atkchance);

					time.isloop = false;
					wea.isLoop = false;
					wea.weatherpoint = 1;

					if (HP1 < 0) {
						time.isloop = false;
						wea.weatherpoint = 1;
						break;
					} // 체력이 0이하로 된다면 끝

				} // 공격선택의 끝

				if (HP1 <= 0) {
					System.out.println("축하드립니다 승리하셨습니다!");
					myhuman.Lvpoint = (myhuman.Lvpoint + 1);
					myhuman.health = myhuman.health - 1;

					try {

						Thread.sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mymonster.Lv == 4) {
						mymonster.sppoint = temp;// 스킬포인트 돌려주기
					}
					bossattack.isLoop = false;// 보스어택루프끄기
					bossmusic.close();
				} // 승급경험치를 얻고 체력을 잃음
				else {
					System.out.println("패배하셨습니다!");
					myhuman.health = myhuman.health - 3 - bossattack.minushealth;
					bossmusic.close();

					bossattack.isLoop = false;
					try {

						Thread.sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (mymonster.Lv == 4) {
						mymonster.sppoint = temp;
					} // 스킬포인트 돌려주기
				} // 승급경험치를 얻고 체력을 잃음
			} // 보스대결 마무리

			else if (num1 == 4)// 아이템 구매코드
			{
				event.isLoop = false;
				for (int i = 0; i < 80; i++)
					System.out.println("");
				introMusic.close();
				System.out.println("무엇을 하시겠습니까");
				System.out.println("1.아이템 구매");
				System.out.println("2.승급 하기(주의 승급시 lvpoint는 무조건 0으로 초기화 됩니다)");
				int num2 = sc.nextInt();// 상점안에 키입력문
				if (num2 == 1) {
					System.out.println("1.듀얼디스크 구매 2.옷 구매하기 3.정령구매하기");
					System.out.println("내가 가진 돈 " + myhuman.money);

					int num3 = sc.nextInt();// 어떤걸 구매할지 들어가는 코드

					if (num3 == 1) {

						System.out.println("1. 오시리스 레드 듀얼디스크 가격  1000원");
						System.out.println("2. 라의 옐로우 듀얼디스크 가격  5000원");
						System.out.println("3. 오벨리스크 블루 듀얼디스크 가격 10000원");
						int num4 = sc.nextInt();
						int temp = myitem.myitembuy(myhuman.money, num4);// 듀얼디스크 구매과정
						{
							if (temp == 5) {
								continue Loop1;
							} else {
								myhuman.money = temp;// 거스름돈을 temp에 저장한후 내 돈에 저장함
							}
						} // 듀얼디스크 구매후 거스름돈 저장코드
					} // 1번아이템 구매 마무리

					else if (num3 == 2) {
						System.out.println("1. 오시리스 레드의 옷가격  1000원");
						System.out.println("2. 라의 옐로우 옷가격  5000원");
						System.out.println("3. 오벨리스크 블루 옷가격 10000원");
						System.out.println("4. 유희의 옷가격 30000원");
						int num4 = sc.nextInt();
						int temp = myitem1.clothbuy(myhuman.money, num4);// 옷구매과정
						{
							if (temp == 5) {
								continue Loop1;
							} else {
								myhuman.money = temp;// 거스름돈을 temp에 저장한후 내 돈에 저장함
							}
						}
					} // 옷구매 마무리

					else if (num3 == 3) {
						System.out.println("1. 방해꾼 옐로  1000원");
						System.out.println("2. 보옥수 루비 카방클  10000원");
						System.out.println("3. 하늘크리보 30000원");
						System.out.println("4. 유벨 100000원");
						int num4 = sc.nextInt();
						int temp = myitem2.fairybuy(myhuman.money, num4);// 정령구매과정
						{
							if (temp == 5) {
								continue Loop1;
							} else {
								myhuman.money = temp;// 거스름돈을 temp에 저장한후 내 돈에 저장함
							}
						}
					} // 옷구매 마무리

				} // 아이템구매 if문 마무리
				if (num2 == 2)// 승급처리 if문 각각 외부의 값을 보고 처리 if문을 따라 맞는 선언문 작용
				{
					if (myhuman.name == "유희") {
						rivalmonster.Rivallevelup(myhuman.Lvpoint, mymonster.Lv);
						mymonster.levelup1(myhuman.Lvpoint, mymonster.Lv);
						myhuman.Lvpoint = 0;// 승급시
					} else if (myhuman.name == "카이바") {
						rivalmonster.Rivallevelup(myhuman.Lvpoint, mymonster.Lv);
						mymonster.levelup2(myhuman.Lvpoint, mymonster.Lv);
						myhuman.Lvpoint = 0;// 승급시
					} else if (myhuman.name == "조이") {
						rivalmonster.Rivallevelup(myhuman.Lvpoint, mymonster.Lv);
						mymonster.levelup3(myhuman.Lvpoint, mymonster.Lv);
						myhuman.Lvpoint = 0;// 승급시
					} else {
						System.out.println("잘못된 선택입니다.");
					}
				} // 승급전마무리

			} // 상점코드 마무리
			else if (num1 == 5) {
				event.isLoop = false;
				for (int i = 0; i < 80; i++)
					System.out.println("");
				introMusic.close();
				System.out.println("1.기본 휴식 500원 체력 +1");
				System.out.println("2.고급 휴양지 1500원 체력 +5");
				System.out.println("3.생명의 샘");
				int num3 = sc.nextInt();
				myhuman.myhealing(num3, myhuman.health, myhuman.money, heal.healing);// 체력회복코드 간단함
				System.out.println("생명의 샘을 마시고 체력을 " + heal.healing + "의 체력을 회복하였습니다..(최대 50)");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (myhuman.health >= 50) {
					heal.isLoop = false;
				}

			} // 체력회복 루트

			else if (num1 == 6) {
				event.isLoop = false;
				for (int i = 0; i < 80; i++)
					System.out.println("");
				introMusic.close();
				System.out.println("카이바-주스텟 : 용기 - 부스텟 : 지능");
				System.out.println("유희 - 주스텟 : 지혜 - 부스텟 : 행운");
				System.out.println("조이 - 주스텟 : 행운 - 부스텟 : 용기");
				System.out.println("1.용기 상승 +5 1000원(카이바)");
				System.out.println("2.지능 상승 +5 1000원(유희)");
				System.out.println("3.행운 상승 +5 1000원(조이)");
				int num3 = sc.nextInt();
				if (myhuman.money >= 1000) {// 돈이 일정금액이상인지 먼저 확인후 동작
					if (myhuman.name == "유희") {
						((yugimonster) mymonster).statbuy(num3);
						myhuman.money = myhuman.money - 1000;
						System.out.println("내 캐릭터의 용기는 " + ((yugimonster) mymonster).brave);
						System.out.println("내 캐릭터의 지능은 " + ((yugimonster) mymonster).brain);
						System.out.println("내 캐릭터의 행운은 " + ((yugimonster) mymonster).lucky);
					} // 유희였을시 스탯 마무리
					else if (myhuman.name == "카이바") {
						((kaimonster) mymonster).statbuy(num3);
						myhuman.money = myhuman.money - 1000;
						System.out.println("내 캐릭터의 용기는 " + ((kaimonster) mymonster).brave);
						System.out.println("내 캐릭터의 지능은 " + ((kaimonster) mymonster).brain);
						System.out.println("내 캐릭터의 행운은 " + ((kaimonster) mymonster).lucky);
					} // 카이바였을시 스탯 마무리
					else if (myhuman.name == "조이") {
						((joymonster) mymonster).statbuy(num3);
						myhuman.money = myhuman.money - 1000;
						System.out.println("내 캐릭터의 용기는 " + ((joymonster) mymonster).brave);
						System.out.println("내 캐릭터의 지능은 " + ((joymonster) mymonster).brain);
						System.out.println("내 캐릭터의 행운은 " + ((joymonster) mymonster).lucky);
					} // 조이였을시 스탯 마무리
				} else
					System.out.println("돈도없는게 저리 꺼져");
			}

			else if (num1 == 7) {
				event.isLoop = false;
				introMusic.close();
				for (int i = 0; i < 80; i++)
					System.out.println("");
				System.out.println("1.스토리 모드 -> 일반몬스터와 보스몬서트를 격파시 StoryLv상승 최고레벨 달성시 게임 클리어");
				System.out.println(
						"2.라이벌 모드 -> 라이벌과의 전투 상대의 라이프를 먼저 0으로 만드는 사람이 승리 승리시 돈을 얻게됨 어둠의 듀얼시 지속적으로 체력감소 하지만 보상이 2배");
				System.out.println("3.보스레이드  -> 5번의 공격으로 보스몬스터의 체력을 0으로 만든다면 승리 승리시 Lvpoint를 얻게됨 Lvpoint로 승급 진행 가능");

				System.out.println("4.상점 -> 듀얼디스크 : 오시리스 레드 공수 500 라의 옐로우 공수 1000 오벨리스크 블루 공수 1500 (특수효과 X)");
				System.out.println(
						"     -> 옷             : 오시리스 레드 공수 300 라의 옐로우 공수 500 오벨리스크 블루 공수 1000 유희의 옷 1500 1500 (특수효과 Sppoint+1");
				System.out.println(
						"     -> 정령          : 방해꾼 옐로 공수 500 랜덤값 : 5 루비 카방클 공수 100 랜덤값 : 4 하늘크리보 공수 300 랜덤값 : 3 유벨 공수 500 랜덤값 : 2");
				System.out.println("4.상점 -> 승급 : 레벨포인트 10이 모이면 다음 단계로 승급이 가능 승급시 Lvpoint는 무조건 0으로 초기화 (주의)");
				System.out.println("5.휴식 : 일정돈을 소모하여 체력을 회복");
				System.out.println("  휴식 : 일정시간마다 체력이 회복된것을 체력으로 전환 가능");
				System.out.println("6.스텟상점 : 일정돈을 스텟을 구매 자신에게 맞는 스텟을 구매시 최종 스킬데미지, 기본 공수 데미지 증가");
				System.out.println("공격력 산출방식 : (( 몬스터의 공격력 / 정령이 가진 랜덤값 (ex 5일시 1~5))+아이템의 공격력)*(캐릭터 부스텟/100)");
				System.out.println("수비력 산출방식 : (( 몬스터의 공격력 / 정령이 가진 랜덤값 -1 (ex 5일시 1~4))+아이템의 수비력)*(캐릭터 부스텟/100)");
				System.out.println("스킬 공격력      : (( ? )+아이템의 공격력)*(캐릭터 주스텟/100)");
				System.out.println("메인화면으로 돌아가실려면 아무 숫자를 입력해주세요");
				int num2 = sc.nextInt();

			}

			else if (num1 == 8) {
				event.isLoop = false;
				introMusic.close();
				for (int i = 0; i < 80; i++)
					System.out.println("");
				if (Musicselect == 0) {
					Musicselect++;
				} else {
					Musicselect = 0;
				}

			} else if (num1 == 9) {
				event.isLoop = false;
				introMusic.close();
				for (int i = 0; i < 80; i++)
					System.out.println("");
				myhuman.money = myhuman.money + 150000;
				myhuman.Lvpoint = myhuman.Lvpoint + 10;
				myhuman.storyLv = 4;

			} else if (num1 == 10) {
				event.isLoop = false;
				introMusic.close();
				endingstory end = new endingstory();
				Thread en = new Thread(end);
				en.start();
				try {
					en.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else if (num1 == event.rand1 && num1 == event.rand) {

				introMusic.close();

				event.isLoop = false;

				System.out.println("			카드를 주웠습니다");
				System.out.println();
				System.out.println("			카드샵에 가서 주운 카드를 판매하고 있습니다");
				System.out.println();
				System.out.println("			카드가격을 샵 주인이 정하고 있습니다");
				System.out.println();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("			카드가격은 " + event.eventmoney);
				System.out.println();
				System.out.println("			카드를 팔아 돈을 벌었지만 찝찝함을 느껴 체력이 감소합니다");
				System.out.println();
				myhuman.money = myhuman.money + event.eventmoney;
				myhuman.health = myhuman.health - 3;
				System.out.println("			이벤트로 인해 가진돈이 증가하였습니다 + " + event.eventmoney);
				System.out.println();
				System.out.println("			이벤트로 인해 체력이 감소하였습니다 - 3 ");
				System.out.println();
				try {

					Thread.sleep(3000);

				}

				catch (InterruptedException e) {
					e.printStackTrace();

				}

			}

			else {

				event.isLoop = false;

				introMusic.close();

				System.out.println("잘못 선택 하셨습니다");

				continue Loop1;

			}

		} // 게임 진행종료

	}// 퍼블릭 스태틱 보이드 종료

}// 메인클래스 종료