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
        return string == null || string.trim().length() == 0;
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
    
    public static int longestLength( String ... strings ){
        int result = 0;
        for( String string : strings ){
            if( string != null ){
                result = Math.max( result, string.length() );
            }
        }
        return result;
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
    
    /**
     * Pads a string with ' ' until it is the given minimum length.
     * 
     * If the string is already long enough, it will not be modified.
     * 
     * @param text
     * @param minLength
     * @return 
     */
    public static String padTo( String text, int minLength ){
        StringBuilder result = new StringBuilder( minLength );
        int remainder = minLength;
        
        if( text != null){
            int length = text.length();
            if( length <= minLength ){
                remainder = minLength - length;
            }
            else{
                remainder = 0;
            }
            
            result.append(text);
        }
        
        for( int i = 0; i < remainder; i++ ){
            result.append(' ');
        }
        
        return result.toString();
    }
    
    public static List<String> padTo( List<String> strings ){
        int count = strings.size();
        List<String> result = new ArrayList<>(count);
        
        int length = 0;
        for( String string : strings ){
            if( string != null ){
                length = Math.max( length, string.length() );
            }
        }
        
        for( String string : strings ){
            result.add( padTo( string, length ) );
        }
        
        return result;
    }
    
    public static String paddedLayout( char tableHeaderSeparator, List<String> ... lists ){
        String padded = paddedLayout( lists );
        StringBuilder separator = new StringBuilder();
        
        String[] lines = padded.split("\n");
        if( lines.length > 0 ){
            int length = lines[0].length();
            for( int i = 0; i < length; i++ ){
                separator.append(tableHeaderSeparator);
            }
            
            StringBuilder result = new StringBuilder();
            result.append( lines[0] );
            result.append( "\n" );
            result.append( separator );
            result.append( "\n" );
            
            for( int i = 1; i < lines.length; i++ ){
                result.append( lines[i] );
                
                if( i+1 < lines.length ){
                    result.append( "\n" );
                }
            }
            return result.toString();
        }
        
        return padded;
    }
    
    public static String paddedLayout( List<String> ... lists ){
        // copy the list to a clone. we're gonna be playing around with it,
        // and this prevents side effects
        List<String>[] clone = new List[lists.length];
        for( int i = 0; i < clone.length; i++ ){
            List _list = new ArrayList<>( lists[i].size() );
            _list.addAll( lists[i] );
            
            clone[i] = _list;
        }
        
        //calculate the columns and rows of the table
        int columns = clone.length;
        int rows = 0;
        for( List<String> list : clone ){
            rows = Math.max( rows, list.size() );
        }
        
        //make sure every list is the same length
        for( List<String> list : clone ){
            if( list.size() < rows ){
                for( int i = list.size(); i < rows; i++ ){
                    list.add(" ");
                }
            }
        }
        
        //create padded text for every column
        List<String>[] padded = new List[columns];
        int index = 0;
        for( List<String> list : clone ){
            padded[index] = padTo(list);
            index++;
        }
        
        //create the table
        
        StringBuilder result = new StringBuilder();
        
        for( int row = 0; row < rows; row++ ){
            for( int column = 0; column < columns; column++ ){
                List<String> columnStrings = padded[column];
                result.append( columnStrings.get(row) );
                
                if( column+1 < columns ){
                    result.append( " " );
                }
                else if( row+1 < rows ){
                    result.append( "\n" );
                }
            }
        }
        
        return result.toString();
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

    public static int replaceAll( StringBuilder target,
                                   String oldText,
                                   String newText,
                                   boolean ignorCase ) {
        int count = 0;
        
        if( target != null && oldText != null && newText != null ) {
            if( ignorCase ) {
                StringBuilder noCaseTarget = new StringBuilder( target.toString().toLowerCase() );
                String noCaseOldText = oldText.toLowerCase();

                int index = noCaseTarget.indexOf( noCaseOldText );
                while( index != -1 ) {
                    count++;
                    int endIndex = index + oldText.length();
                    target.replace( index, endIndex, newText );
                    noCaseTarget.replace( index, endIndex, newText );
                    endIndex = index + newText.length();
                    index = noCaseTarget.indexOf( noCaseOldText, endIndex );
                }
            }
            else {
                int index = target.indexOf( oldText );
                while( index != -1 ) {
                    count++;
                    int endIndex = index + oldText.length();
                    target.replace( index, endIndex, newText );
                    endIndex = index + newText.length();
                    index = target.indexOf( oldText, endIndex );
                }
            }
        }
        
        return count;
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

    public static String sideBySide( String left, String right ){
        final String separator = "   |   ";
        
        String[] one = left.split( "\n" );
        String[] two = right.split( "\n" );
        int lineCount = Math.max(one.length, two.length);
        int longestLine = 0;
        
        for( String line : one ){
            longestLine = Math.max(longestLine, line.length() );
        }
        
        for( String line : two ){
            longestLine = Math.max(longestLine, line.length() );
        }
        
        int length = longestLine * lineCount * 2;
        length += (separator.length()+1) * lineCount;
        StringBuilder result = new StringBuilder(length);
        
        for( int i = 0; i < lineCount; i++ ){
            String a = padTo( i < one.length ? one[i] : "", longestLine );
            String b = padTo( i < two.length ? two[i] : "", longestLine );
            
            result.append(a);
            result.append(separator);
            result.append(b);
            result.append("\n");
        }
        
        return result.toString();
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
