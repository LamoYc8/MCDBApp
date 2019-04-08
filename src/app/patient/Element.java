package app.patient;

public class Element
{
    
    protected String numDossier;
    protected String numElement;
    
    
    
    public Element (String numDossier, String numElement) { 
        this.numDossier = numDossier;
        this.numElement = numElement;
    }
    
    public Element (String numDossier) { 
        this.numDossier = numDossier;
        this.numElement = null;
    } 
    
    
    public String getNumDossier()
    {
        return numDossier;
    }
    
    public String getNumElement()
    {
        return numElement;
    }
    
    
    public void setNumElement(String n)
    {
        numElement = n;
    }

    
}
