package galvin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamSucker
    extends Thread {
    private static final String STREAM_CLOSED = "Stream closed";

    private InputStream inputStream;
    private String type;

    public StreamSucker( InputStream is, String type ) {
        this.inputStream = is;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
            BufferedReader reader = new BufferedReader( inputStreamReader );
            String line = null;
            while( ( line = reader.readLine() ) != null ) {
                System.out.println( type + "> " + line );
            }
        }
        catch( IOException ioe ) {
            if( !ioe.getMessage().contains( STREAM_CLOSED ) ) {
                ioe.printStackTrace();
            }
        }
    }

}
