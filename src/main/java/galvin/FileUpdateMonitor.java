package galvin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpdateMonitor
{
    private static final Logger logger = LoggerFactory.getLogger( FileUpdateMonitor.class );
    private final File src;
    private final HashMap<String, Long> modified = new HashMap();
    private final HashMap<String, Long> checksum = new HashMap();

    public FileUpdateMonitor( File src ) {
        this.src = src;
    }
    
    public void updateTimestamps(){
        modified.clear();
        checksum.clear();
        modified();
    }
    
    public List<File> modified(){
        return modified( src );
    }
    
    private List<File> modified( File file ){
        List<File> result = new ArrayList();
        
        if( file.isDirectory() ){
            for( File child : file.listFiles() ){
                result.addAll( modified( child ) );
            }
            return result;
        }
        
        String filePath = file.getAbsolutePath();
        
        Long timestamp = file.lastModified();
        Long originalModified = modified.get( filePath );
        if( !equals( timestamp, originalModified) ){
            result.add( file );
            modified.put( filePath, timestamp );
        }
        
        return result;
    }
    
    private boolean equals( Long check, Long original ){
        if( check == null && original != null ){
            return false;
        }
        else if( check != null && original == null ){
            return false;
        }
        else if( check == null && original == null ){
            return true;
        }
        else {
            boolean result = check.equals( original );
            return result;
        }
    }
}
