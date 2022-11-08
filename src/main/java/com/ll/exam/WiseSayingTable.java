package com.ll.exam;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayingTable {
	public WiseSayingTable() {
	}
	private static String getTableDirPath() {
		return App.getDataBaseDir() + "/wise_saying";
	}
	public static String getTableDataFilePath(int id) {
		return getTableDirPath() + "/" + id + ".json";
	}

	public static String getTableLastIdFilePath() {
		return getTableDirPath() + "/last_id.txt";
	}

	public static String getTableDataDumpFilePath() {
		return getTableDirPath() + "/data.json";
	}

	public void save(WiseSaying wiseSaying) {
		Util.file.mkdir(getTableDirPath());
		String body = wiseSaying.toJson();
		Util.file.saveToFile(getTableDataFilePath(wiseSaying.id), body);
	}

	public WiseSaying save(String content, String author) {
		int id = getLastId() + 1;

		WiseSaying wiseSaying = new WiseSaying(id, content, author);
		save(wiseSaying);

		saveLastId(id);

		return wiseSaying;
	}

	public boolean save(int id, String content, String author) {
		WiseSaying wiseSaying = new WiseSaying(id, content, author);
		save(wiseSaying);

		return true;
	}

	private void saveLastId(int id) {
		Util.file.saveToFile(getTableLastIdFilePath(), id + "");
	}

	public int getLastId() {
		String lastId = Util.file.readFromFile(getTableLastIdFilePath(), "");

		if (lastId.isEmpty()) {
			return 0;
		}

		return Integer.parseInt(lastId);
	}

	public WiseSaying findById(int id) {
		String path = getTableDataFilePath(id);

		if (new File(path).exists() == false) {
			return null;
		}

		Map<String, Object> map = Util.json.jsonToMapFromFile(path);

		if (map == null) {
			return null;
		}

		return new WiseSaying((int) map.get("id"), (String) map.get("content"), (String) map.get("author"));
	}

	public List<WiseSaying> findAll() {
		List<Integer> fileIds = getFileIds();

		return fileIds
			.stream()
			.map(id -> findById(id))
			.collect(Collectors.toList());
	}

	private List<Integer> getFileIds() {
		String path = getTableDirPath();
		List<String> fileNames = Util.file.getFileNamesFromDir(path);

		return fileNames
			.stream()
			.filter(fileName -> !fileName.equals("last_id.txt"))
			.map(fileName -> fileName.replace(".json", ""))
			.mapToInt(Integer::parseInt)
			.boxed()
			.collect(Collectors.toList());
	}



	public boolean removeById(int id) {
		String path = getTableDataFilePath(id);

		File file = new File(path);

		if (file.exists() == false) {
			return false;
		}

		file.delete();

		return true;
	}


}
