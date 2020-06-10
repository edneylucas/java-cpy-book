package test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.itau.cpy.CpyBookManager;
import br.com.itau.cpy.exception.CopybookException;

public class Main implements FileFilter {

	public static void main(String[] args) throws IOException, CopybookException {
		
		File[] listFiles = new File("C:\\Temp\\copybooks").listFiles(new Main());
			
		for (File file : listFiles) {
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			StringBuilder builder = new StringBuilder();
			while (inputStream.read(buffer) != -1) {
				builder.append(new String(buffer));
				buffer = new byte[1024];
			}
			inputStream.close();
			CpyBookManager.generate(builder.toString(), file.getName());
		}
	}

	@Override
	public boolean accept(File pathname) {
		return pathname.getAbsolutePath().endsWith(".cpy");
	}
}
