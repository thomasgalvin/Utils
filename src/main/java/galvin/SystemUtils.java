package galvin;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public final class SystemUtils {

    private SystemUtils() {
    }

    public static final boolean IS_MAC = ( System.getProperty( "os.name" ).toLowerCase().contains( "mac" ) );
    public static final boolean IS_WINDOWS = ( System.getProperty( "os.name" ).toLowerCase().contains( "windows" ) );
    public static final int PREFERED_MODIFIER_KEY = ( IS_MAC ? ActionEvent.META_MASK : ActionEvent.CTRL_MASK );
    public static final int SECONDARY_MODIFIER_KEY = ( IS_MAC ? ActionEvent.CTRL_MASK : ActionEvent.ALT_MASK );
    public static final String QUIT_STRING = ( IS_MAC ? "Quit" : "Exit" );

    public static File getUserHome() {
        String result = System.getProperty( "user.home" );
        return new File( result );
    }

    public static File getPreferencesDirectory( String programName,
                                                String version ) {
        File preferencesDirectory = getPreferencesDirectory( programName );
        File result = new File( preferencesDirectory, version );
        result.mkdirs();
        return result;
    }

    public static File getPreferencesDirectory( String programName ) {
        String result = System.getProperty( "user.home" );
        result += File.separatorChar;

        if( IS_MAC ) {
            result += "Library" + File.separatorChar + programName + File.separatorChar;
        }
        else if( IS_WINDOWS ) {
            result += "Application Data" + File.separatorChar + programName + File.separatorChar;
        }
        else {
            //assuming a unix variant
            result += "." + programName + File.separatorChar;
        }

        File resultFile = new File( result );
        resultFile.mkdirs();
        return resultFile;
    }

    public static File getSystemPreferencesDirectory( String programName,
                                                      String version ) {
        File preferencesDirectory = getSystemPreferencesDirectory( programName );
        return new File( preferencesDirectory, version );
    }

    public static File getSystemPreferencesDirectory( String programName ) {
        String result = "";

        if( IS_MAC ) {
            result += "/Library/" + programName;
        }
        else if( IS_WINDOWS ) {
            result = "C:\\Program Files\\" + programName;
        }
        else {
            result = "/etc/" + programName;
        }

        result += File.separatorChar;

        return new File( result );
    }

    public static File getTempDir( String name ) {
        String result = System.getProperty( "java.io.tmpdir" );
        result += File.separatorChar;
        result += name;
        result += File.separatorChar;
        return new File( result );
    }

    public static File getTempDir( String programName, String programVersion ) {
        File result = getTempDir( programName );
        result = new File( result, programVersion );
        return result;
    }

    public static File getRandomTempDir() {
        File tmpFile = new File( System.getProperty( "java.io.tmpdir" ), java.util.UUID.randomUUID().toString() );
        tmpFile.mkdirs();
        return tmpFile;
    }

    public static File getRandomTempDir( String dirName ) {
        File tmpFile = new File( System.getProperty( "java.io.tmpdir" ), java.util.UUID.randomUUID().toString() );
        tmpFile = new File( tmpFile, dirName );
        tmpFile.mkdirs();
        return tmpFile;
    }

    public static File getTempFile() {
        return getTempFile( java.util.UUID.randomUUID().toString() );
    }

    public static File getTempFileWithExtension( String extension ) {
        return getTempFile( java.util.UUID.randomUUID().toString() + extension );
    }

    public static File getTempFile( String fileName ) {
        File tmpFile = new File( System.getProperty( "java.io.tmpdir" ), java.util.UUID.randomUUID().toString() );
        tmpFile.mkdirs();
        tmpFile = new File( tmpFile, java.util.UUID.randomUUID().toString() + fileName );
        return tmpFile;
    }

    public static void displaySystemProperties() {
        Properties properties = System.getProperties();
        Enumeration names = properties.propertyNames();
        while( names.hasMoreElements() ) {
            String name = names.nextElement().toString();
            String value = properties.getProperty( name );

            System.out.println( name + ": " + value );
        }

    }

    public static void printMemory() {
        System.out.println( "Available processors (cores): "
                            + Runtime.getRuntime().availableProcessors() );

        long free = Runtime.getRuntime().freeMemory() / 1048576;
        System.out.println( "Free memory (megabytes): "
                            + free );

        long maxMemory = Runtime.getRuntime().maxMemory() / 1048576;
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println( "Maximum memory (megabytes): "
                            + ( maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory ) );

        long total = Runtime.getRuntime().totalMemory() / 1048576;
        System.out.println( "Total memory available to JVM (megabytes): "
                            + total );
    }

    public static void selectFileInBrowser( File file )
        throws IOException {
        if( SystemUtils.IS_WINDOWS ) {
            String command = "explorer.exe /select,\"" + file.getAbsolutePath() + "\"";
            Runtime.getRuntime().exec( command );
        }
        else if( SystemUtils.IS_MAC ) {
            String[] args = new String[]{
                "open", "-R", file.getAbsolutePath()
            };

            Process p = Runtime.getRuntime().exec( args );

            try {
                p.waitFor();
            }
            catch( Throwable t ) {
                t.printStackTrace();
            }

//            InputStream error = p.getErrorStream();
//            String errorMessage = StringIo.readStringFromInputStream( error );
//            System.out.println( "process error:" );
//            System.out.println( errorMessage );
//
//            InputStream processInput = p.getInputStream();
//            String processMessage = StringIo.readStringFromInputStream( processInput );
//            System.out.println( "process message:" );
//            System.out.println( processMessage );
        }
        else {
            File parent = file.getParentFile();
            if( parent != null ) {
                Desktop.getDesktop().open( file );
            }
        }
    }

}
