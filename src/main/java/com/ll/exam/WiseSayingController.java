package com.ll.exam;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
	private Scanner sc;
	private WiseSayingService wiseSayingService;

	public WiseSayingController(Scanner sc) {
		wiseSayingService = new WiseSayingService();
		this.sc = sc;
	}

	public void write() {
		System.out.print("명언 : ");
		String content = sc.nextLine();
		System.out.print("작가 : ");
		String author = sc.nextLine();
		WiseSaying wiseSaying = wiseSayingService.write(content, author);

		System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.id);
	}

	public void list() {
		System.out.println("번호 / 작가 / 명언");
		System.out.println("----------------------");

		List<WiseSaying> wiseSayings = wiseSayingService.findAll();

		for (int i = wiseSayings.size() - 1; i >= 0; i--) {
			System.out.printf("%d / %s / %s", wiseSayings.get(i).id, wiseSayings.get(i).author,
				wiseSayings.get(i).content);
		}
	}

	public void remove(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("번호를 입력해주세요.");
			return;
		}

		WiseSaying wiseSaying = wiseSayingService.findById(id);

		if (wiseSaying == null) {
			System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
			return;
		}

		wiseSayingService.remove(id);
		System.out.printf("%d번 명언이 삭제되었습니다.\n", id);

	}

	public void modify(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.print("번호를 입력해주세요.");
			return;
		}

		WiseSaying wiseSaying = wiseSayingService.findById(id);

		if (wiseSaying == null) {
			System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("명언(기존) : %s\n", wiseSaying.content);
		System.out.print("명언 : ");
		String content = sc.nextLine();

		System.out.printf("작가(기존) : %s", wiseSaying.author);
		System.out.print("작가 : ");
		String author = sc.nextLine();

		wiseSayingService.modify(id, content, author);
		System.out.printf("%d번 명언이 수정되었습니다.\n", id);
	}


}
