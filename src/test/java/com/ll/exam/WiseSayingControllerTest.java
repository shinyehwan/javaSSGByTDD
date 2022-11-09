package com.ll.exam;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WiseSayingControllerTest {
	@BeforeAll
	public void beforeAll() {
		App.setMode("test");
	}

	@BeforeEach
	public void beforeEach() {
		Util.file.deleteDir(App.getDataBaseDir());
	}
	@Test
	public void 등록을_하면_명언과_작가를_물어본다() {
		String rs = AppTestRunner.run("""
			등록
			나의 죽음을 적들에게 알리지 말라
			이순신
			""");
		System.out.println(rs);

		assertTrue(rs.contains("명언 : "));
		assertTrue(rs.contains("작가 : "));
		// assertTrue(true);

	}

	@Test
	public void 등록을_하면_생성된_명언의_번호가_출력되어야_한다() {
		String rs = AppTestRunner.run("""
			등록
			나의 죽음을 적들에게 알리지 말라
			이순신
			등록
			나에게 불가능이란 없다.
			나폴레옹
			""");
		System.out.println(rs);

		assertTrue(rs.contains("1번 명언이 등록되었습니다."));
		assertTrue(rs.contains("2번 명언이 등록되었습니다."));
		// assertTrue(true);

	}

	@Test
	public void 등록_후_목록에서_확인할_수_있어야_한다() {
		String rs = AppTestRunner.run("""
			등록
			나의 죽음을 적들에게 알리지 말라
			이순신
			등록
			나에게 불가능이란 없다.
			나폴레옹
			목록
			""");

		assertTrue(rs.contains("번호 / 작가 / 명언"));
		assertTrue(rs.contains("----------------------"));
		assertTrue(rs.contains("2 / 나폴레옹 / 나에게 불가능이란 없다."));
		assertTrue(rs.contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라"));
	}

	@Test
	public void 명언을_삭제할_수_있다() {
		String rs = AppTestRunner.run("""
			등록
			나의 죽음을 적들에게 알리지 말라
			이순신
			등록
			나에게 불가능이란 없다.
			나폴레옹
			삭제?id=1
			목록
			삭제?id=1
			""");

		assertTrue(rs.contains("1번 명언이 삭제되었습니다."));
		assertTrue(rs.contains("2 / 나폴레옹 / 나에게 불가능이란 없다."));
		assertFalse(rs.contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라"));
		assertTrue(rs.contains("1번 명언은 존재하지 않습니다."));
	}
	@Test
	public void 명언을_수정할_수_있다() {
		String rs = AppTestRunner.run("""
			등록
			나의 죽음을 적들에게 알리지 말라
			이순신
			등록
			나에게 불가능이란 없다.
			나폴레옹
			목록
			수정?id=1
			나의 죽음을 적들에게 알리지 마라!
			이순신장군
			목록
			""");

		assertTrue(rs.contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라"));
		assertTrue(rs.contains("1 / 이순신장군 / 나의 죽음을 적들에게 알리지 마라!"));
	}


	@Test
	public void 프로그램_시작시_타이틀_출력_그리고_종료() {
		String rs = AppTestRunner.run("");

		System.out.println(rs);

		assertTrue(rs.contains("== 명언 SSG =="));
		assertTrue(rs.contains("명령) "));
		// assertTrue(true);

	}
	@Test
	public void 빌드를_수행하면_결과물_파일이_생성된다() {
		String rs = AppTestRunner.run("빌드");

		File file = new File(WiseSayingTable.getTableDataDumpFilePath());
		assertTrue(file.exists());

		String dumpFileBody = Util.file.readFromFile(WiseSayingTable.getTableDataDumpFilePath(), "");

		assertEquals("[]", dumpFileBody);
	}
	@Test
	public void 명언을_등록한_후_빌드를_수행하면_등록한_내용을_포함하는_결과물_파일이_생성된다() {
		String rs = AppTestRunner.run("""
                 등록
                 나의 죽음을 적들에게 알리지 말라
                 이순신
                 등록
                 나에게 불가능이란 없다.
                 나폴레옹
                 빌드
                 """);

		String dumpFileBody = Util.file.readFromFile(WiseSayingTable.getTableDataDumpFilePath(), "");

		assertTrue(dumpFileBody.contains("""
                 "id": 1
                 """.stripIndent().trim()));

		assertTrue(dumpFileBody.contains("""
                 "content": "나의 죽음을 적들에게 알리지 말라"
                 """.stripIndent().trim()));

		assertTrue(dumpFileBody.contains("""
                 "author": "이순신"
                 """.stripIndent().trim()));

		assertTrue(dumpFileBody.contains("""
                 "id": 2
                 """.stripIndent().trim()));

		assertTrue(dumpFileBody.contains("""
                 "content": "나에게 불가능이란 없다."
                 """.stripIndent().trim()));

		assertTrue(dumpFileBody.contains("""
                 "author": "나폴레옹"
                 """.stripIndent().trim()));
	}
}

