package com.cleo.mws.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.amazonservices.mws.client.MwsUtl;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.StatusList;
import com.amazonaws.mws.model.TypeList;

public class CommonUtils {

	public static XMLGregorianCalendar convertToGregorianCalendar(String date) throws ParseException {
		if(date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			Date inputDate = format.parse(date);
			GregorianCalendar gregCalendar = new GregorianCalendar();
			gregCalendar.setTime(inputDate);
			XMLGregorianCalendar xmlGregCalender = MwsUtl.getDTF().newXMLGregorianCalendar(gregCalendar);
			return xmlGregCalender;
		}else {
			return null;
		}
		
	}

	public static List<String> convertToList(String str) {
		return Arrays.asList(str.split("\\s*,\\s*"));
	}

	public static com.amazonaws.mws.model.IdList convertToIdList(String str) {
		IdList idList = null;
		if(!isNullOrEmpty(str))
			idList = new IdList(Arrays.asList(str.split("\\s*,\\s*")));
		return idList;
	}

	public static TypeList convertToTypeList(String str) {
		TypeList typeList = null;
		if(!isNullOrEmpty(str))
			typeList = new TypeList(Arrays.asList(str.split("\\s*,\\s*")));
		return typeList;
	}

	public static File createTempFile(InputStream inputStream) throws IOException {
		File targetFile = new File("targetFile.tmp");

		java.nio.file.Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		IOUtils.closeQuietly(inputStream);
		return targetFile;
	}

	public static String computeContentMD5Value(FileInputStream fis) throws IOException, NoSuchAlgorithmException {

		DigestInputStream dis = new DigestInputStream(fis, MessageDigest.getInstance("MD5"));

		byte[] buffer = new byte[8192];
		while (dis.read(buffer) > 0)
			;

		String md5Content = new String(
				org.apache.commons.codec.binary.Base64.encodeBase64(dis.getMessageDigest().digest()));

		// Effectively resets the stream to be beginning of the file
		// via a FileChannel.
		fis.getChannel().position(0);

		return md5Content;
	}

	public static StatusList convertToStatusList(String str) {
		StatusList statusList = null;
		if(!isNullOrEmpty(str))
			statusList = new StatusList(Arrays.asList(str.split("\\s*,\\s*")));
		return statusList;
	}
	
	public static boolean isNullOrEmpty(String str) {
		if(str == null || str.isEmpty())
			return true;
		else
			return false;
	}
}
