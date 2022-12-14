package com.ll.exam;

import java.util.Scanner;

public class App {
	private static String mode = "prod";
	private Scanner sc;

	public App(Scanner sc) {
		this.sc = sc;
	}

	public static String getDataBaseDir() {
		return mode + "_data";
	}

	public static void setMode(String mode) {
		App.mode = mode;
	}
	public void run() {
		System.out.println("== 명언 SSG ==");

		WiseSayingController wiseSayingController = new WiseSayingController(sc);

		outer:
		while (true) {
			System.out.print("명령) ");
			String cmd = sc.nextLine().trim();
			Rq rq = new Rq(cmd);

			switch (rq.getPath()) {
				case "등록":
					wiseSayingController.write(rq);
					break;
				case "목록":
					wiseSayingController.list(rq);
					break;
				case "삭제":
					wiseSayingController.remove(rq);
					break;
				case "수정":
					wiseSayingController.modify(rq);
					break;
				case "빌드":
					wiseSayingController.build(rq);
					break;
				case "종료":
					break outer;
			}
		}
	}
}
