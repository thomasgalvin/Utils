package galvin;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public final class NetUtils {
    private NetUtils() {
    }

    public static final int BUFFER_SIZE = 4096;

    public static void copyURLToFile( URL url, File destination )
        throws IOException {
        FileUtils.copyURLToFile( url, destination );
    }

    public static String readStringFromURL( String url )
        throws IOException {
        return readStringFromURL( new URL( url ) );
    }

    public static String readStringFromURL( URL url )
        throws IOException {
        return IOUtils.toString( url );
    }

    public static String getHostName() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostName = addr.getHostName();
        return hostName;
    }

    public static String getIPAddress( String hostName ) throws UnknownHostException {
        InetAddress addr = InetAddress.getByName( hostName );
        String ip = addr.getHostAddress();
        return ip;
    }

}
