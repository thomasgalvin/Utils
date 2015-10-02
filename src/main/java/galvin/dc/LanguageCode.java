/**
Copyright &copy 2012 Thomas Galvin - All Rights Reserved.
 */
package galvin.dc;

import galvin.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.io.IOUtils;

@XmlType( name = "LanguageCode", namespace = "dc.galvin.com" )
@XmlRootElement( name = "LanguageCode" )
@XmlAccessorType( XmlAccessType.FIELD )
public class LanguageCode
{
    public static final LanguageCode ENGLISH = new LanguageCode( "English", "en" );
    public static final LanguageCode ENGLISH_US = new LanguageCode( "English (United States)", "en-us" );
    public static final LanguageCode ENGLISH_UK = new LanguageCode( "English (United Kingdom)", "en-uk" );
    public static final LanguageCode SPANISH = new LanguageCode( "Spanish", "es" );
    public static final LanguageCode FRENCH = new LanguageCode( "French", "fr" );
    
    private String name;
    private String code;

    public LanguageCode()
    {
    }

    public LanguageCode( String name, String code )
    {
        this.name = name;
        this.code = code;
    }
    
    @Override
    public LanguageCode clone()
    {
        LanguageCode result = new LanguageCode( name, code );
        return result;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String toString()
    {
        return getName() + " (" + getCode() + ")";
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if( obj == null )
        {
            return false;
        }
        if( getClass() != obj.getClass() )
        {
            return false;
        }
        final LanguageCode other = (LanguageCode) obj;
        if( ( this.name == null ) ? ( other.name != null ) : !this.name.equals( other.name ) )
        {
            return false;
        }
        if( ( this.code == null ) ? ( other.code != null ) : !this.code.equals( other.code ) )
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 13 * hash + ( this.name != null ? this.name.hashCode() : 0 );
        hash = 13 * hash + ( this.code != null ? this.code.hashCode() : 0 );
        return hash;
    }
    
    public static List<LanguageCode> loadLanguageCodes() throws IOException
    {
        List<LanguageCode> codes = new ArrayList();
        
        InputStream stream = LanguageCode.class.getClassLoader().getResourceAsStream( "com/galvin/dc/language-codes.dat" );
        if( stream != null )
        {
            String data = IOUtils.toString( stream );
            String[] lines = StringUtils.tokenize( data, "\n" );
            if( lines.length %2 != 0 )
            {
                throw new IOException( "language-codes.dat is corrupt." );
            }
            
            for( int i = 0; i < lines.length; i++ )
            {
                String abb = lines[i];
                i++;
                String name = lines[i];
                LanguageCode code = new LanguageCode( name, abb );
                codes.add( code );
            }
        }
        else
        {
            throw new IOException( "Unable to loead language-codes.dat." );
        }
        
        return codes;
    }
    
    public static LanguageCode[] loadLanguageCodeArray() throws IOException
    {
        List<LanguageCode> codes = loadLanguageCodes();
        LanguageCode[] result = new LanguageCode[ codes.size() ];
        for( int i = 0; i < result.length; i++ )
        {
            result[i] = codes.get( i );
        }
        return result;
    }
}
