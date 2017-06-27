package galvin;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils extends org.apache.commons.io.FileUtils {
    private static final Logger logger = LoggerFactory.getLogger( FileUtils.class );
    
    public static void copyDirectoryToDirectory( File srcDir, File destDir, boolean parseChecsums ) throws IOException {
        copyDirectoryToDirectory( srcDir, destDir, parseChecsums, null, false );
    }
    
    public static void copyDirectoryToDirectory( File srcDir, File destDir, boolean parseChecsums, FileFilter fileFilter ) throws IOException {
        copyDirectoryToDirectory( srcDir, destDir, parseChecsums, null, false );
    }
    
    /**
     * Merges the contents of srcDir into destDir. If two files with the same
     * relative path exist and parseChecsums is true,  the source file will only 
     * be copied over if the two files have different checksums
     * @param srcDir the source dir
     * @param destDir the target dir
     * @param parseChecsums parse checksums of conflicting files
     * @param fileFilter used to ignore files
     * @param verbose if true, each file copied will be logged
     * @throws IOException on error
     */
    public static void copyDirectoryToDirectory( File srcDir, File destDir, boolean parseChecsums, FileFilter fileFilter, boolean verbose ) throws IOException {
    
        if( !parseChecsums && fileFilter == null ) {
            copyDirectoryToDirectory( srcDir, destDir );
        }
        else {
            if( !srcDir.exists() ){
                throw new IOException( "srcDir does not exist: " + srcDir.getAbsolutePath() );
            }
            
            if( !srcDir.isDirectory() ){
                throw new IOException( "srcDir exists but is not a directory: " + srcDir.getAbsolutePath() );
            }
                
            if( destDir.exists() && !destDir.isDirectory() ){
                throw new IOException( "destDir exists but is not a directory: " + destDir.getAbsolutePath() );
            }
            destDir.mkdirs();
            
            File[] srcFiles = srcDir.listFiles();
            File[] destFiles = destDir.listFiles();

            for( File srcFile : srcFiles ) {
                if( fileFilter == null || fileFilter.accept( srcFile ) )
                {
                    if( srcFile.isDirectory() ){
                        if( verbose ){
                            logger.info( "copying: " + srcFile.getName() + " to " + destDir.getAbsolutePath() );
                        }
                        
                        File newDest = new File( destDir, srcFile.getName() );
                        copyDirectoryToDirectory( srcFile, newDest, parseChecsums, fileFilter, verbose );
                    }
                    else {
                        File matchingFile = findMatchingName( srcFile, destFiles );
                        boolean doCopy = true;

                        if( matchingFile != null ){
                            long checksumOne = checksumCRC32( srcFile );
                            long checksumTwo = checksumCRC32( matchingFile );
                            doCopy = checksumOne != checksumTwo;
                        }

                        if( doCopy ){
                            if( verbose ){
                                logger.info( "copying: " + srcFile.getName() + " to " + destDir.getAbsolutePath() );
                            }
                            
                            File destFile = new File( destDir, srcFile.getName() );
                            copyFile( srcFile, destFile );
                        }
                    }
                }
            }
        }
    }

    public static File findMatchingName( File srcFile, File[] destFiles ) {
        String fileName = srcFile.getName();
        File matchingFile = null;

        for( File destFile : destFiles ) {
            if( destFile.getName().equals( fileName ) ) {
                matchingFile = destFile;
                break;
            }
        }

        if( matchingFile == null && SystemUtils.IS_MAC ) {
            for( File destFile : destFiles ) {
                if( destFile.getName().equalsIgnoreCase( fileName ) ) {
                    matchingFile = destFile;
                    break;
                }
            }
        }

        return matchingFile;
    }

    public static boolean contentEqualsDir( File srcDir, File destDir ) throws IOException {
        FileNameComparator comparator = new FileNameComparator();
        
        if( !srcDir.exists() ){
            throw new IOException( "srcDir does not exist: " + srcDir.getAbsolutePath() );
        }
        
        if( !srcDir.isDirectory() ){
            throw new IOException( "srcDir exists and is not a directory: " + srcDir.getAbsolutePath() );
        }
        
        if( srcDir.equals( destDir ) ){
            return true;
        }
        
        if( !destDir.exists() ){
            throw new IOException( "destDir does not exist: " + destDir.getAbsolutePath() );
        }
        
        if( !destDir.isDirectory() ){
            throw new IOException( "destDir exists and is not a directory: " + destDir.getAbsolutePath() );
        }
        
        if( srcDir.equals( destDir ) ){
            return true;
        }
        
        File[] srcFiles = srcDir.listFiles();
        File[] destFiles = destDir.listFiles();
        
        for( File srcFile : srcFiles ){
            File matching = null;
            
            for( File destFile : destFiles ){
                if( comparator.compare( srcFile, destFile ) == 0 ){
                    matching = destFile;
                    break;
                }
            }
            
            if( matching == null ){
                return false;
            }
            else if( srcFile.isDirectory() ){
                if( matching.isDirectory() ){
                    boolean subDirMatches = contentEqualsDir( srcFile, matching );
                    if( !subDirMatches ){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else if( !contentEquals( srcFile, matching ) ){
                return false;
            }
        }
        
        return true;
    }
    
    public static String readFile(String fileName){
        return readFile( fileName, Charset.defaultCharset() );
    }
    
    public static String readFile(String fileName, Charset charset){
        try{
            File file = new File(fileName);
            FileInputStream fin = new FileInputStream(file);
            String result = IOUtils.toString(fin, charset);
            IOUtils.closeQuietly(fin);
            return result;
        }
        catch(IOException ioe){
            logger.debug( "Error reading resource", ioe );
            return null;
        }
    }
    
    public static String readResource(String name){
        return readResource( name, Charset.defaultCharset() );
    }
    
    public static String readResource(String name, Charset charset){
        try{
            URL url = FileUtils.class.getClassLoader().getResource(name);
            if( url == null ){
                logger.debug( "Unable to locate resourc: " + name );
                return null;
            }
            String content = IOUtils.toString( url, charset );
            return content;
        }
        catch(IOException ioe){
            logger.debug( "Error reading resource", ioe );
            return null;
        }
    }
}
