package com.ll.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	private Scanner sc;
	private int WiseSayingLastId;
	private List<WiseSaying> wiseSayings;

	public App(Scanner sc) {
		this.sc = sc;
		WiseSayingLastId = 0;
		wiseSayings = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 명언 SSG ==");

		outer:
		while (true) {
			System.out.println("명령) ");
			String cmd = sc.nextLine().trim();
			Rq rq = new Rq(cmd);
			switch (rq.getPath()) {
				case "등록":
					int id = ++WiseSayingLastId;
					System.out.println("명언 : ");
					String content = sc.nextLine();
					System.out.println("작가 : ");
					String author = sc.nextLine();
					wiseSayings.add(new WiseSaying(id, content, author));
					System.out.printf("%d번 명언이 등록되었습니다.\n", id);
					break;
				case "목록":
					System.out.println("번호 / 작가 / 명언");
					System.out.println("----------------------");
					for (int i = wiseSayings.size() - 1; i >= 0; i--) {
						System.out.printf("%d / %s / %s", wiseSayings.get(i).id, wiseSayings.get(i).author,
							wiseSayings.get(i).content);
					}
				case "삭제":
					remove(rq);
					break;
				case "종료":
					break outer;
			}
		}

	}

	private void remove(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("번호를 입력해주세요.");
			return;
		}

		WiseSaying wiseSaying = findById(id);
		if (wiseSaying == null) {
			System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
			return;
		}

		wiseSayings.remove(wiseSaying);
		System.out.printf("%d번 명언이 삭제되었습니다.\n", id);

	}

	private WiseSaying findById(int id) {
		for (WiseSaying wiseSaying : wiseSayings) {
			if (wiseSaying.id == id) {
				return wiseSaying;
			}
		}
		return null;
	}

}
