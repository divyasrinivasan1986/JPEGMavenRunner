/**
 * 
 */
package jpegrename.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author srinivasan
 *
 */
public class Constants {
	public static final String FILE_SEPARATOR = ".";
	public static final DateFormat DFTIME = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	public static final DateFormat DFTIMEINPUT = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	public static final String USER_INPUT = "Enter canonical(absolute) path of the JPEG directory: ";
	public static final String USER_CONFIRM = "All the JPEG files in the entered path will be renamed to their recording Timestamp (format:yyyy-MM-dd HH.mm.ss). Do you want to continue (Y/N)):";
	public static final String ABORT_APP = "Rename aborted.";
	public static final String RENAME_FINISHED = "Rename finished.";
	public static final String IO_UNEXPECTED_ERROR = "Unexpected Error reading directory:";
	public static final String RENAME_FILENAMES = "The following files were renamed:";
	public static final String NO_FILE_FOUND = "No Files found. Rename aborted.";
	public static final String TIFF_DATETIME_TAGNAME = "DateTime";
	public static final String JPEG = "JPEG";
	public static final String JPG = "JPG";
	public static final String YES = "Y";
}
