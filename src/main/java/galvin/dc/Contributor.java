/**
Copyright &copy 2012 Thomas Galvin - All Rights Reserved.
 */
package galvin.dc;

import galvin.StringUtils;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType( name = "Contributor", namespace = "dc.galvin.com" )
@XmlRootElement( name = "Contributor" )
@XmlAccessorType( XmlAccessType.FIELD )
public class Contributor
{
    public static final ContributorRole DEFAULT_ROLE = ContributorRole.AUTHOR;
    private String name;
    private String sortByName;
    private ContributorRole role;

    public Contributor()
    {}
    
    public Contributor( String name, String sortByName )
    {
        this( name, sortByName, DEFAULT_ROLE );
    }
    
    public Contributor( String name, String sortByName, ContributorRole role )
    {
        this.name = name;
        this.sortByName = sortByName;
        this.role = role;
    }
    
    @Override
    public Contributor clone()
    {
        Contributor result = new Contributor(name, sortByName, role );
        return result;
    }
    
    public String getDublinCoreEntry()
    {
        StringBuilder metadataSection = new StringBuilder();
        metadataSection.append( "<dc:creator opf:file-as=\"" );
            metadataSection.append( StringUtils.neverNull( getSortByName() ) );
            metadataSection.append( "\"" );
            
            if( getRole() != null )
            {
                metadataSection.append( "\" opf:role=\"" );
                metadataSection.append( StringUtils.neverNull( getRole().getAbbreviation() ) );
                metadataSection.append( "\"" );
            }
            
            metadataSection.append( ">" );
            metadataSection.append( StringUtils.neverNull( getName() ) );
            metadataSection.append( "</dc:creator>\n" );
            
            return metadataSection.toString();
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public ContributorRole getRole()
    {
        return role;
    }

    public void setRole( ContributorRole role )
    {
        this.role = role;
    }

    public String getSortByName()
    {
        return sortByName;
    }

    public void setSortByName( String sortByName )
    {
        this.sortByName = sortByName;
    }

}
