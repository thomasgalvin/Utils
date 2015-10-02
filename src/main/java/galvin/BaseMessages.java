package galvin;

import java.util.Locale;
import java.util.ResourceBundle;

public class BaseMessages {
    private ResourceBundle messages;

    public BaseMessages( String messageBundleName ) {
        this( messageBundleName, new Locale( "en", "US" ) );
    }

    public BaseMessages( String messageBundleName, Locale locale ) {
        try {
            messages = ResourceBundle.getBundle( messageBundleName, locale );
        }
        catch( Throwable t ) {
            System.err.println( "Error loading message bundle: " + messageBundleName + " for locale: " + locale );
        }
    }

    public String getString( String key ) {
        return getString( key, "" );
    }

    public String getString( String key, String defaultValue ) {
        try {
            String result = messages.getString( key );
            if( result != null && !result.trim().isEmpty() ) {
                return result;
            }
        }
        catch( Throwable t ) {
            System.err.println( "Error retrieving key: " + key + ", returning default value of " + defaultValue );
            //t.printStackTrace();
        }

        return defaultValue;
    }

    public float getFloat( String key, float defaultValue ) {
        try {
            String result = messages.getString( key );
            if( result != null && !result.trim().isEmpty() ) {
                return Float.parseFloat( result );
            }
        }
        catch( Throwable t ) {
            System.err.println( "Error retrieving key: " + key + ", returning default value of " + defaultValue );
            //t.printStackTrace();
        }

        return defaultValue;
    }

}
