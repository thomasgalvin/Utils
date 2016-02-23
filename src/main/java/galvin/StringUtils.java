package galvin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class StringUtils
    extends org.apache.commons.lang3.StringUtils {

    private StringUtils() {
    }

    public static String addLeadingZeroIfNecessary( int integer ) {
        if( integer < 10 ) {
            return "0" + integer;
        }
        else {
            return "" + integer;
        }
    }

    public static String appendIfNecessary( String target, String append ) {
        if( target.endsWith( append ) ) {
            return target;
        }
        else {
            return target + append;
        }
    }

    public static String capitalize( String word ) {
        if( word.length() > 0 ) {
            return word.substring( 0, 1 ).toUpperCase()
                   + word.substring( 1, word.length() );
        }
        else {
            return "";
        }
    }

    public static String cat( List<String> strings ) {
        StringBuilder result = new StringBuilder();

        if( strings != null ) {
            for( int i = 0; i < strings.size(); i++ ) {
                result.append( strings.get( i ) );
                if( i + 1 < strings.size() ) {
                    result.append( "\n" );
                }
            }
        }

        return result.toString();
    }

    public static String cat( String[] strings ) {
        StringBuilder result = new StringBuilder();

        if( strings != null ) {
            for( int i = 0; i < strings.length; i++ ) {
                result.append( strings[i] );
                if( i + 1 < strings.length ) {
                    result.append( "\n" );
                }
            }
        }

        return result.toString();
    }

    public static String cat( String[] strings, String delimiter ) {
        StringBuilder result = new StringBuilder();

        if( strings != null ) {
            for( int i = 0; i < strings.length; i++ ) {
                result.append( strings[i] );
                if( i + 1 < strings.length ) {
                    result.append( delimiter );
                }
            }
        }

        return result.toString();
    }

    public static String camelCase( String string ) {
        StringBuilder builder = new StringBuilder( string.length() );
        string = string.toLowerCase();

        boolean whitespaceMode = true;
        for( char c : string.toCharArray() ) {
            if( whitespaceMode ) {
                if( !Character.isWhitespace( c ) ) {
                    c = Character.toUpperCase( c );
                    whitespaceMode = false;
                }
            }
            else {
                if( Character.isWhitespace( c ) ) {
                    whitespaceMode = true;
                }
            }

            builder.append( c );
        }

        return builder.toString();
    }
    
    public static boolean contains( String[] array, String target ){
        for( String string : array ){
            if( target.equals( string ) ){
                return true;
            }
        }
        
        return false;
    }

    public static String csv( Collection<String> strings ) {
        StringBuilder builder = new StringBuilder();

        Iterator<String> iterator = strings.iterator();
        while( iterator.hasNext() ) {
            String string = iterator.next();
            builder.append( string );

            if( iterator.hasNext() ) {
                builder.append( ", " );
            }
        }

        return builder.toString();
    }

    public static boolean isEmpty( String string ) {
        return empty( string );
    }

    public static boolean empty( String string ) {
        if( string == null || string.trim().length() <= 0 ) {
            return true;
        }
        return false;
    }

    public static String getOrdinal( int number ) {
        String numberText = "" + number;

        if( number == 11
            || number == 12
            || number == 13 ) {
            return "th";
        }
        else if( numberText.endsWith( "1" ) ) {
            return "st";
        }
        else if( numberText.endsWith( "2" ) ) {
            return "nd";
        }
        else if( numberText.endsWith( "3" ) ) {
            return "rd";
        }
        else {
            return "th";
        }
    }

    public static String markup( String text, String markup ) {
        return markup( text, markup, markup );
    }

    public static String markup( String text, String startMarkup, String endMarkup ) {
        StringBuilder builder = new StringBuilder( startMarkup );
        builder.append( text );
        builder.append( endMarkup );
        return builder.toString();
    }

    public static String neverNull( String string ) {
        return ( string == null ? "" : string );
    }

    public static String replaceAll( String target,
                                     String oldText,
                                     String newText ) {
        return replaceAll( target, oldText, newText, false );
    }

    public static String replaceAll( String target,
                                     String oldText,
                                     String newText,
                                     boolean ignorCase ) {
        if( target != null && oldText != null && newText != null ) {
            StringBuilder result = new StringBuilder( target );
            replaceAll( result, oldText, newText );
            return result.toString();
        }
        else {
            return target;
        }
    }

    public static String replaceAll( StringBuilder target,
                                     String oldText,
                                     String newText ) {
        replaceAll( target, oldText, newText, false );
        return target.toString();
    }

    public static void replaceAll( StringBuilder target,
                                   String oldText,
                                   String newText,
                                   boolean ignorCase ) {
        if( target != null && oldText != null && newText != null ) {
            if( ignorCase ) {
                StringBuilder noCaseTarget = new StringBuilder( target.toString().toLowerCase() );
                String noCaseOldText = oldText.toLowerCase();

                int index = index = noCaseTarget.indexOf( noCaseOldText );
                while( index != -1 ) {
                    int endIndex = index + oldText.length();
                    target.replace( index, endIndex, newText );
                    noCaseTarget.replace( index, endIndex, newText );
                    endIndex = index + newText.length();
                    index = noCaseTarget.indexOf( noCaseOldText, endIndex );
                }
            }
            else {
                int index = index = target.indexOf( oldText );
                while( index != -1 ) {
                    int endIndex = index + oldText.length();
                    target.replace( index, endIndex, newText );
                    endIndex = index + newText.length();
                    index = target.indexOf( oldText, endIndex );
                }
            }
        }
    }

    public static String reverseChars( String target ) {
        byte[] bytes = target.getBytes();
        byte[] result = new byte[ bytes.length ];
        for( int i = 0; i < bytes.length; i++ ) {
            result[( bytes.length - i - 1 )] = bytes[i];
        }
        return new String( result );
    }

    public static String rot13( String s ) {
        //'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
        //'nopqrstuvwxyzabcdefghijklmNOPQRSTUVWXYZABCDEFGHIJKLM'
        if( s != null ) {
            if( s.length() > 0 ) {
                int length = s.length();
                StringBuilder buffer = new StringBuilder( length );
                for( int i = 0; i < length; i++ ) {
                    char c = s.charAt( i );
                    if( c >= 'a' && c <= 'z' ) {
                        c += 13;
                        if( c > 'z' ) {
                            c -= 26;
                        }
                    }
                    else if( c >= 'A' && c <= 'Z' ) {
                        c += 13;
                        if( c > 'Z' ) {
                            c -= 26;
                        }
                    }
                    buffer.insert( i, c );
                }
                return buffer.toString();
            }
        }
        return s;
    }

    /**
     * Like ROT13, but it also encodes numeric characters.
     * 1234567890
     * will become
     * 6789012345
     */
    public static String rot135( String s ) {
        if( s != null ) {
            s = rot13( s );
            int length = s.length();
            StringBuilder buffer = new StringBuilder( length );
            for( int i = 0; i < length; i++ ) {
                char c = s.charAt( i );
                if( c >= '0' && c <= '4' ) {
                    c += 5;
                }
                else if( c >= '5' && c <= '9' ) {
                    c -= 5;
                }
                buffer.insert( i, c );
            }
            return buffer.toString();
        }
        return s;
    }

    public static String spacesToTabs( String text, int spacesPerTab ) {
        if( text != null
            && text.length() > 0
            && spacesPerTab > 0 ) {
            String spaces = "";
            for( int i = 0; i < spacesPerTab; i++ ) {
                spaces += " ";
            }
            return replaceAll( text, spaces, "\t" );
        }
        return text;
    }

    public static String tabsToSpaces( String text, int spacesPerTab ) {
        if( text != null
            && text.length() > 0
            && spacesPerTab > 0 ) {
            String spaces = "";
            for( int i = 0; i < spacesPerTab; i++ ) {
                spaces += " ";
            }
            return replaceAll( text, "\t", spaces );
        }
        return text;
    }

    public static String[] tokenize( String target, String delimiter ) {
        List tokens = new ArrayList();

        int lastIndex = 0;
        int index = 0;
        while( index > -1 ) {
            index = target.indexOf( delimiter, lastIndex );

            if( index != -1 ) {
                String token = target.substring( lastIndex, index );
                lastIndex = index + delimiter.length();
                tokens.add( token );
            }
            else {
                String token = target.substring( lastIndex, target.length() );
                if( token.length() != 0 ) {
                    tokens.add( token );
                }
            }
        }

        int numTokens = tokens.size();
        String[] result = new String[ numTokens ];
        for( int i = 0; i < numTokens; i++ ) {
            result[i] = (String)tokens.get( i );
        }

        return result;
    }

    public static List<String> tokenizeToList( String target, String delimiter ) {
        List<String> result = new ArrayList();

        String[] tokens = tokenize( target, delimiter );
        result.addAll( Arrays.asList( tokens ) );

        return result;
    }

    public static String[] toArray( List target ) {
        return toStringArray( target );
    }

    public static String[] toStringArray( List target ) {
        int size = target.size();
        String[] result = new String[ size ];
        for( int i = 0; i < size; i++ ) {
            result[i] = target.get( i ).toString();
        }
        return result;
    }

}
