package kr.or.ddit.controller.file.item03;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		
		String savedName = uuid.toString() + "_" + originalName;
		// /2023/03/07과 같은 폴더 경로를 만들고, /2023/03/07 폴더 경로를 리턴한다.
		String savedPath = calcPath(uploadPath);
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target);	// 파일 복사
		
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);	// 확장자 추출
		// \2023\03\07 폴더 경로를 만들고, /2023/03/07 폴더 경로를 리턴한다.
		String uploadedFileName = savedPath.replace(File.separatorChar, '/') + "/" + savedName;
		
		// 확장자가 이미지 파일이면 s_가 붙은 파일 썸네일 이미지 파일을 생성한다.
		if(MediaUtils.getMediaType(formatName) != null) {
			makeThumbnail(uploadPath, savedPath, savedName);
		}
		return uploadedFileName;
	}
	// s_가 붙은 썸네일 파일로 만들어준다.
	private static void makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);	// 확장자 추출
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
	}
	
	// 2023/03/06 폴더들을 만들어준다. (폴더명을 만들어주는곳)
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		// DecimalFormat("00") ::: 두자리에서 빈자리는 0으로 채워준다.
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		return datePath;
	}
	
	// 가변인자
	// 키워드 '...' 를 사용한다.
	// [사용법] 타입...변수명 혀앹로 사용
	// 순서대로 yearPath, monthPath, datePath가 배열로 들어온다.
	
	// calcPath함수에서 만들어진 폴더명을 가지고 실제 폴더 경로에 폴더들을 만들어주는곳
	private static void makeDir(String uploadPath, String...paths) {
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(!dirPath.exists()) {
				dirPath.mkdirs();
			}
		}
	}
}
