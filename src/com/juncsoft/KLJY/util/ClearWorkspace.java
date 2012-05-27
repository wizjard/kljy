package com.juncsoft.KLJY.util;

import java.io.File;

public class ClearWorkspace {
	public void deleteSVN(File file) {
		if (file.isDirectory()) {
			String name = file.getName();
			if (name.equalsIgnoreCase(".svn")) {
				deleteFolder(file);
				if (file.delete()) {
					System.out.println("Delete Folder: " + file.getPath());
				}
			} else {
				File[] children = file.listFiles();
				for (File child : children) {
					deleteSVN(child);
				}
			}
		} else {
			if (file.getName().startsWith(".tmp")) {
				file.delete();
				System.out.println("Delete File: " + file.getPath());
			}
		}
	}

	public void deleteFolder(File file) {
		if (file.isDirectory()) {
			if (file.delete()) {
				System.out.println("Delete Folder: " + file.getPath());
			} else {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						if (temp.delete()) {
							System.out.println("Delete Folder: "
									+ temp.getPath());
						} else
							deleteFolder(temp);
					} else {
						temp.delete();
						System.out.println("Delete File: " + file.getPath());
					}
				}
			}
		} else {
			if (file.delete()) {
				System.out.println("Delete File: " + file.getPath());
			}
		}
	}

	public void run() {
		String path = ClearWorkspace.class.getClassLoader().getResource("")
				.getPath();
		File file = new File(path).getParentFile().getParentFile()
				.getParentFile();
		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		for (File temp : file.listFiles()) {
			deleteSVN(temp);
		}
	}

	public static void main(String[] args) {
		ClearWorkspace cw = new ClearWorkspace();
		cw.run();
	}
}
