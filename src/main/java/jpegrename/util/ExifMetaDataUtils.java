/**
 * 
 */
package jpegrename.util;

import static jpegrename.util.Constants.DFTIME;
import static jpegrename.util.Constants.DFTIMEINPUT;
import static jpegrename.util.Constants.FILE_SEPARATOR;
import static jpegrename.util.Constants.JPEG;
import static jpegrename.util.Constants.JPG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.io.FilenameUtils;

/**
 * @author srinivasan
 *
 */
public class ExifMetaDataUtils {
	public String readExifTimestamp(final File jpegImageFile) throws IOException, ImageReadException {
		// metadata might be null if no metadata is found.
		final ImageMetadata metadata = Imaging.getMetadata(jpegImageFile);

		final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
		if (null != jpegMetadata) {
			// exif might be null if no Exif metadata is found.
			final TiffImageMetadata exif = jpegMetadata.getExif();
			if (null != exif) {
				// TIFF.TAG_DATE_TIME
				for (TiffField t : exif.getAllFields()) {
					if (t.getTagName().equals(Constants.TIFF_DATETIME_TAGNAME)) {
						String s = t.getTagInfo().getValue(t) != null ? (String) t.getTagInfo().getValue(t) : null;
						if (s != null) {
							return s;
						}

					}
				}
				// if that did not work,try this
				final String[] timestamp = exif.getFieldValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
				if (timestamp != null && timestamp.length > 0) {
					return timestamp[0];
				}

			}
		}
		return null;
	}

	public List<String> renameFiles(Collection<File> files) {
		List<String> renamedFiles = new ArrayList<>();
		String oldName = null;
		for (File jpegFile : files) {
			if (JPEG.equalsIgnoreCase(FilenameUtils.getExtension(jpegFile.getName()))||JPG.equalsIgnoreCase(FilenameUtils.getExtension(jpegFile.getName()))) {
				Path source = Paths.get(jpegFile.getPath());
				String timestamp = null;
				try {
					timestamp = readExifTimestamp(jpegFile);
				} catch (ImageReadException | IOException e) {
					System.out.println("Error reading Image metadata:" + e.getMessage());
				}
				if (timestamp != null) {
					String timestampFormatted = null;
					try {
						timestampFormatted = DFTIME.format(DFTIMEINPUT.parse(timestamp));
					} catch (ParseException e) {
						System.out.println("Error parsing Date:" + e.getMessage());
					}
					try {
						oldName = source.toString();
						Files.move(source, source.resolveSibling(timestampFormatted + FILE_SEPARATOR + FilenameUtils.getExtension(jpegFile.getName())), StandardCopyOption.REPLACE_EXISTING);
						renamedFiles.add(oldName);
					} catch (IllegalArgumentException | IOException e) {
						System.out.println("Error while renaming Files:" + e.getMessage());
					}
				}
			}
		}
		return renamedFiles;
	}
}
