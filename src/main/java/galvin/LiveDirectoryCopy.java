package galvin;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveDirectoryCopy
{
    private static final Logger logger = LoggerFactory.getLogger( LiveDirectoryCopy.class );
    
    private final File srcDir;
    private final File destDir;
    private final long sleep;
    private Runner runner;

    public LiveDirectoryCopy( File srcDir, File destDir ) {
        this( srcDir, destDir, 1_000 );
    }
    
    public LiveDirectoryCopy( File srcDir, File destDir, long sleep ) {
        this.srcDir = srcDir;
        this.destDir = destDir;
        this.sleep = sleep;
    }
    
    private void doCopy() throws IOException {
        FileUtils.copyDirectoryToDirectory( srcDir, destDir, true );
    }
    
    public synchronized void start(){
        if( runner == null ){
            runner = new Runner();
            runner.start();
        }
    }
    
    public synchronized void stop(){
        if( runner != null ){
            runner.running = false;
            runner = null;
        }
    }
    
    private class Runner extends Thread{
        public boolean running = true;
        
        public void run(){
            try{
                while( running ){
                    if( running ){
                        doCopy();
                    }
                    
                    if( running ){
                        sleep( sleep );
                    }
                }
            }
            catch( Throwable t ){
                logger.error( "Error in runner", t );
            }
        }
    }
}
