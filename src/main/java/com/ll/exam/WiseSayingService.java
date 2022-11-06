package com.ll.exam;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {


	private WiseSayingRepository wiseSayingRepository;

	public WiseSayingService() {
		wiseSayingRepository = new WiseSayingRepository();
	}
	public WiseSaying write(String content, String author) {
		return wiseSayingRepository.write(content, author);
	}

	public List<WiseSaying> findAll() {
		return wiseSayingRepository.findAll();
	}

	public boolean remove(int id) {
		return wiseSayingRepository.remove(id);
	}
	public WiseSaying findById(int id) {
		return wiseSayingRepository.findById(id);
	}

	public void modify(int id, String content, String author) {
		wiseSayingRepository.modify(id, content, author);
	}
}
