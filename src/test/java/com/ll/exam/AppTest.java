package com.ll.exam;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class AppTest {
	@Test
	public void 테스트() {
		assertTrue(1 == 1);
		assertEquals(1, 1);
	}

	@Test
	public void 스캐너에_키보드가_아닌_문자열을_입력으로_설정() {
		Scanner sc = TestUtil.genScanner("안녕");

		String cmd = sc.nextLine().trim();
		assertEquals("안녕", cmd);
	}

	@Test
	public void 출력을_모니터에_하지_않고_문자열로_얻기() {
		ByteArrayOutputStream output = TestUtil.setOutToByteArray();
		System.out.print("안녕");
		String rs = output.toString();
		TestUtil.clearSetOutToByteArray(output);

		assertEquals("안녕", rs);
	}

	@Test
	public void 문자열을_파일에_저장() {
		Util.file.mkdir(App.getDataBaseDir());
		Util.file.saveToFile(App.getDataBaseDir()+ "/1.txt", "안녕");
		String body = Util.file.readFromFile(App.getDataBaseDir()+"/1.txt", "");

		assertEquals("안녕", body);
	}
}
