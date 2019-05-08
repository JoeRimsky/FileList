/*
*	Author: Joe Rimsky
*	Date: 4/8/19
*/

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.UIManager;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class FileList {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory to start the list:");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		List<File> files = new ArrayList<File>();
		int returnVal = jfc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			if(jfc.getSelectedFile().isDirectory()) {
				files = getAllItems(jfc.getSelectedFile().getAbsolutePath(), files);
			}
		}

		printToTXT(files);
	}

	public static void printToTXT(List<File> files) {
		try {
			String desktopDir = System.getProperty("user.home") + "/Desktop";
			File resultsFile = new File(desktopDir, "FileList.txt");
			String fmt = "%-250s %-50s %-50s%n";
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
			BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFile));
			writer.write(String.format(fmt, "File Path", "Last Modified Date", "File Size (KB)"));
			for(File file : files)
				writer.write(String.format(fmt, file.getAbsolutePath(), sdf.format(file.lastModified()), ((file.length()/1024 + 1) + "KB")));
			writer.close();
		} catch (Exception ioe) {}
	}

	public static List getAllItems(String path, List<File> files) {
		File directory = new File(path);
		File[] listofNames = directory.listFiles();
		if(listofNames != null) {
			for(File file : listofNames) {
				if(file.isFile())
					files.add(file);
				else if(file.isDirectory())
					getAllItems(file.getAbsolutePath(), files);
			}
		}
		return files;
	}
}