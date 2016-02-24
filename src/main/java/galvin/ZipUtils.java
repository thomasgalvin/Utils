package galvin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils
{
    private static final int BUFFER = 2048;

    public static void unzip( File zipFile, File destinationDir ) throws IOException
    {
        if( destinationDir.exists() && !destinationDir.isDirectory() )
        {
            throw new IOException( "Cannot unzip to a file." );
        }

        destinationDir.mkdirs();

        ZipFile zip = new ZipFile( zipFile );
        Enumeration zipFileEntries = zip.entries();
        while( zipFileEntries.hasMoreElements() )
        {
            ZipEntry entry = ( ZipEntry ) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File( destinationDir, currentEntry );

            File parentFile = destFile.getParentFile();
            parentFile.mkdirs();

            if( !entry.isDirectory() )
            {
                FileOutputStream fos = new FileOutputStream( destFile );
                BufferedOutputStream dest = new BufferedOutputStream( fos, BUFFER );

                int currentByte;
                byte data[] = new byte[ BUFFER ];
                BufferedInputStream is = new BufferedInputStream( zip.getInputStream( entry ) );
                while( ( currentByte = is.read( data, 0, BUFFER ) ) != -1 )
                {
                    dest.write( data, 0, currentByte );
                }
                dest.flush();
                dest.close();
                is.close();
            }
            else
            {
                destFile.mkdirs();
            }
        }
    }
}
