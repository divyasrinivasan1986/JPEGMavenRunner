package jpegrename.metadatareader;

import static jpegrename.util.Constants.ABORT_APP;
import static jpegrename.util.Constants.IO_UNEXPECTED_ERROR;
import static jpegrename.util.Constants.NO_FILE_FOUND;
import static jpegrename.util.Constants.RENAME_FILENAMES;
import static jpegrename.util.Constants.RENAME_FINISHED;
import static jpegrename.util.Constants.USER_CONFIRM;
import static jpegrename.util.Constants.USER_INPUT;
import static jpegrename.util.Constants.YES;
import static jpegrename.util.Constants.NO_RENAME_FILENAMES;

import java.io.File;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import jpegrename.util.ExifMetaDataUtils;

/**
 * Searches all JPEG files in a given directory and renames them to the format yyyy-MM-dd HH.mm.ss
 *
 */
public class JpegRenameRunner {

	public static void main(String[] args) {
		ExifMetaDataUtils exifObj = new ExifMetaDataUtils();
		Scanner sc = null;
		try {
			sc = new Scanner(System.in); // System.in is a standard input stream
			System.out.print(USER_INPUT);
			String dirpath = sc.next();
			File jpegDirectory = new File(dirpath);
			Collection<File> files = null;
			try {
				files = FileUtils.listFilesAndDirs(jpegDirectory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
			} catch (UncheckedIOException e) {
				System.out.println(IO_UNEXPECTED_ERROR + e.getMessage());
			}
			if (files != null && files.size() > 0) {
				System.out.println(USER_CONFIRM);
				String response = sc.next();
				if (YES.equalsIgnoreCase(response)) {
					List<String> renamedFiles = exifObj.renameFiles(files);
					if (!renamedFiles.isEmpty()) {
						System.out.println(RENAME_FILENAMES);
						System.out.println(renamedFiles);
						System.out.println(RENAME_FINISHED);
					}else {
						System.out.println(NO_RENAME_FILENAMES);
					}
					
				} else {
					System.out.println(ABORT_APP);
				}
			} else {
				System.out.println(NO_FILE_FOUND);
			}

		} finally {
			if (sc != null) {
				sc.close();
			}

		}
	}
}
