package galvin;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest
{
    @Test
    public void testCopyDir() throws Exception{
        File srcDir = new File( "target/src" );
        
        //should not be copied
        File srcOne = new File( srcDir, "hello.txt" );
        FileUtils.write( srcOne, "Hello, world!" );
        
        //should be copied
        File srcTwo = new File( srcDir, "hello2.txt" );
        FileUtils.write( srcTwo, "Hello again, world!" );
        
        //should be copied
        File srcThree = new File( srcDir, "hello3.txt" );
        FileUtils.write( srcThree, "Okay, you're getting kind of clingy." );
        
        File subDir = new File( srcDir, "goodbye" );
        subDir.mkdirs();
        
        //should be copied
        File subOne = new File( subDir, "goodbye.txt" );
        FileUtils.write( subOne, "Goodbye!" );
        
        //should be copied
        File subTwo = new File( subDir, "goodbye2.txt" );
        FileUtils.write( subTwo, "Goodbye!!" );
        
        //should be copied
        File subThree = new File( subDir, "goodbye3.txt" );
        FileUtils.write( subThree, "Goodbye!!!" );
        
        File subDirTwo = new File( srcDir, "tmp" );
        subDirTwo.mkdirs();
        
        //should not be copied
        File subFour = new File( subDirTwo, "1.txt" );
        FileUtils.write( subFour, "12345" );
        
        //should be copied
        File subFive = new File( subDirTwo, "2.txt" );
        FileUtils.write( subFive, "67890" );
        
        //should be copied
        File subSix = new File( subDirTwo, "3.txt" );
        FileUtils.write( subSix, "abcde" );
        
        File destDir = new File( "target/dest" );
        
        //should not be overwritten
        File destOne = new File( destDir, "hello.txt" );
        FileUtils.write( destOne, "Hello, world!" );
        
        //should be overritten
        File destTwo = new File( destDir, "hello2.txt" );
        FileUtils.write( destTwo, "Hello again, world!!!" );
        
        File destDirTwo = new File( destDir, "tmp" );
        destDirTwo.mkdirs();
        
        //should not be overwritten
        File destThree = new File( destDirTwo, "1.txt" );
        FileUtils.write( destThree, "12345" );
        
        //should be overwritten
        File destFour = new File( destDirTwo, "2.txt" );
        FileUtils.write( destFour, "12345" );
        
        Assert.assertFalse( "directories should not have been equal", FileUtils.contentEqualsDir( srcDir, destDir) );
        FileUtils.copyDirectoryToDirectory( srcDir, destDir, true );
        Assert.assertTrue( "directories should have been equal", FileUtils.contentEqualsDir( srcDir, destDir) );
        
    }
}
