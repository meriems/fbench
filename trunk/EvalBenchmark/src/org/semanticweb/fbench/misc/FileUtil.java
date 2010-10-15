package org.semanticweb.fbench.misc;

import java.io.File;

import org.semanticweb.fbench.Config;



/**
 * Utility class for file operations.
 * 
 * @author as
 *
 */
public class FileUtil {

	
	/**
	 * location utility.<p>
	 * 
	 *  if the specified path is absolute, it is returned as is, 
	 *  otherwise a location relative to {@link Config#getBaseDir()} is returned<p>
	 *  
	 *  examples:<p>
	 *  
	 *  <code>
	 *  /home/data/myPath -> absolute linux path
	 *  c:\\data -> absolute windows path
	 *  \\\\myserver\data -> absolute windows network path (see {@link File#isAbsolute()})
	 *  data/myPath -> relative path (relative location to baseDir is returned)
	 *  </code>
	 *  
	 * @param path
	 * @return
	 * 			the file corresponding to the abstract path
	 */
	public static File getFileLocation(String path) {
		
		// check if path is an absolute path that already exists
		File f = new File(path);
		
		if (f.isAbsolute())
			return f;
		
		f = new File( Config.getConfig().getBaseDir() + path);
		return f;
	}
}
