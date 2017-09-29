package priv.weilinwu.codecollection.note.filedeletion;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDeletionUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileDeletionUtils.class);

	public boolean deleteDirectory(File dir) {
    	logger.info("Deleting {}", dir.getPath());
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}