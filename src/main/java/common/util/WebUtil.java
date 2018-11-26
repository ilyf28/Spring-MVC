package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	private static final String ACTION_HTML_BEGIN = "<!DOCTYPE html>\n<html lang=\"ko\">\n<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" /><title>{0}</title></head>\n<body>\n<script type=\"text/javascript\">\n";
	private static final String ACTION_HTML_END = "\n</script>\n</body>\n</html>";
	private static final String DEFAULT_ALERT_TITLE = "Message";
	private static final String FILE_DOWNLOAD_PATH = "/tmp/";
	
	/**
	 * alert() 후 해당 url로 이동
	 * @param message
	 * @param retUrl
	 * @param w
	 */
	public static void alertAndForward(String message, String retUrl, PrintWriter w) {
		alertAndForward(message, retUrl, w, DEFAULT_ALERT_TITLE);
	}
	
	/**
	 * alert() 후 해당 url로 이동
	 * @param message
	 * @param retUrl
	 * @param w
	 * @param title
	 */
	public static void alertAndForward(String message, String retUrl, PrintWriter w, String title) {
		message = message.replaceAll("\"", "'").replaceAll("\\\\n", "<br>");
		
		w.write(MessageFormat.format(ACTION_HTML_BEGIN, title));
		if (!message.equals("")) {
			w.write("alert(\"" + message + "\");");
		}
		w.write("location.href = '" + retUrl + "';");
		w.write(ACTION_HTML_END);
		w.flush();
		w.close();
	}

	/**
	 * 파일 다운로드
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void fileDownload(HttpServletResponse response, String fileName) throws Exception {
		File file = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ServletOutputStream sout = null;
		BufferedOutputStream bos = null;
		
		try {
			file = new File(FILE_DOWNLOAD_PATH + fileName);
			
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			sout = response.getOutputStream();
			bos = new BufferedOutputStream(sout);
			
			byte buffer[] = new byte[2048];
			int bytesRead = 0;
			
			while ((bytesRead = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			
			bos.flush();
			
			bos.close();
			sout.close();
			bis.close();
			fis.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("파일 다운로드중에 오류가 발생하였습니다.");
		} finally {
			if (bos != null) { bos.close(); }
			if (sout != null) { sout.close(); }
			if (bis != null) { bis.close(); }
			if (fis != null) { fis.close(); }
			
			if (file.exists()) {
				if (file.delete()) {
					logger.debug("########## 파일삭제 성공");
				} else {
					logger.debug("########## 파일삭제 실패");
				}
			} else {
				logger.debug("########## 파일이 존재하지 않습니다.");
			}
		}
	}
}
