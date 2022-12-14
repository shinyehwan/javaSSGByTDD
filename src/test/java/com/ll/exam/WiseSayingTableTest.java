package com.ll.exam;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WiseSayingTableTest {
	private WiseSayingTable wiseSayingTable;
	@BeforeAll
	public void beforeAll() {
		App.setMode("test");
		wiseSayingTable = new WiseSayingTable();
	}
	@BeforeEach
	public void beforeEach() {
		Util.file.deleteDir(App.getDataBaseDir());

		wiseSayingTable.save("나에게 불가능이란 없다.", "나폴레옹");
		wiseSayingTable.save("나의 죽음을 적들에게 알리지 마라.", "이순신");
	}

	@Test
	public void 저장() {
		int newId = wiseSayingTable.getLastId() + 1;
		wiseSayingTable.save("자유가 아니면 죽음을 달라!", "패트릭 헨리");
		assertTrue(new File(WiseSayingTable.getTableDataFilePath(newId)).exists());
	}
	@Test
	public void 조회() {
		WiseSaying wiseSaying = wiseSayingTable.findById(1);

		assertEquals(1, wiseSaying.id);
		assertEquals("나에게 불가능이란 없다.", wiseSaying.content);
		assertEquals("나폴레옹", wiseSaying.author);
	}

	@Test
	public void 전체조회() {
		List<WiseSaying> wiseSayings = wiseSayingTable.findAll();

		assertEquals(2, wiseSayings.size());
		assertEquals(1, wiseSayings.get(0).id);
		assertEquals("나에게 불가능이란 없다.", wiseSayings.get(0).content);
		assertEquals("나폴레옹", wiseSayings.get(0).author);

		assertEquals(2, wiseSayings.get(1).id);
		assertEquals("나의 죽음을 적들에게 알리지 마라.", wiseSayings.get(1).content);
		assertEquals("이순신", wiseSayings.get(1).author);
	}

	@Test
	public void 삭제() {
		wiseSayingTable.removeById(1);

		WiseSaying wiseSaying = wiseSayingTable.findById(1);

		assertEquals(null, wiseSaying);
	}
}

