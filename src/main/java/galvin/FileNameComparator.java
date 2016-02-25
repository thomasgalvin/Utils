package galvin;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator
{
    @Override
    public int compare( Object o1, Object o2 ) {
        String filenameOne = getFileName( o1 );
        String filenameTwo = getFileName( o2 );
        
        if( filenameOne == null && filenameTwo != null ){
            return 1;
        }
        else if( filenameOne != null && filenameTwo == null ){
            return -1;
        }
        else if( filenameOne == null && filenameTwo == null ){
            return 0;
        }
        else{
            return filenameOne.compareTo( filenameTwo );
        }
    }
    
    public static String getFileName( Object object ){
        if( object instanceof File ){
            File file = (File)object;
            return file.getName();
        }
        else if( object instanceof String ){
            return (String)object;
        }
        else if( object != null ){
            return object.toString();
        }
        return null;
    }
}
