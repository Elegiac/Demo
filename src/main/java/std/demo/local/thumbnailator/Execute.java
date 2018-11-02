package std.demo.local.thumbnailator;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class Execute {

	public static void main(String[] args) throws IOException {
		Thumbnails.of("C:\\Users\\yeahmobi\\Desktop\\b03533fa828ba61edaa8c81e4b34970a304e59b8.gif") 
        .scale(0.1) 
       .toFiles(new File("C:\\Users\\yeahmobi\\Desktop\\"), Rename.NO_CHANGE);

	}

}
