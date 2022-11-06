package com.ll.exam;

import java.util.Scanner;

public class App {

	private Scanner sc;

	public App(Scanner sc) {
		this.sc = sc;
	}

	public void run() {
		WiseSayingController wiseSayingController = new WiseSayingController(sc);
		System.out.println("== 명언 SSG ==");

		outer:
		while (true) {
			System.out.print("명령) ");
			String cmd = sc.nextLine().trim();
			Rq rq = new Rq(cmd);
			switch (rq.getPath()) {
				case "등록":
					wiseSayingController.write();
					break;
				case "목록":
					wiseSayingController.list();
					break;
				case "삭제":
					wiseSayingController.remove(rq);
					break;
				case "수정":
					wiseSayingController.modify(rq);
					break;
				case "종료":
					break outer;
			}
		}
	}
}
