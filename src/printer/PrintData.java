package printer;

public class PrintData 
{
	private String familyName;
	private String firstName;
	private String date;
	private String ifwt;
	private String mnaf;
	private String intern;
	private String extern;
	private String genInstr;
	private String labSetup;
	private String dangerSubst;
	
	public PrintData()
	{
		String familyName = null;
		String firstName = null;
		String date = null;
		String ifwt = null;
		String mnaf = null;
		String intern = null;
		String extern = null;
		String genInstr = null;
		String labSetup = null;
		String dangerSubst = null;
	}
	
	//Setter
	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setIfwt(String ifwt)
	{
		this.ifwt = ifwt;
	}
	
	public void setMNaF(String mnaf)
	{
		this.mnaf = mnaf;
	}
	
	public void setIntern(String intern)
	{
		this.intern = intern;
	}
	
	public void setExtern(String extern)
	{
		this.extern = extern;
	}
	
	public void setGeneralInstructions(String genInstr)
	{
		this.genInstr = genInstr;
	}
	
	public void setLabSetup(String labSetup)
	{
		this.labSetup = labSetup;
	}
	
	public void setDangerousSubstances(String dangerSubst)
	{
		this.dangerSubst = dangerSubst;
	}
	
	//Getter
	public String getFamilyName()
	{
		return familyName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getIfwt()
	{
		return ifwt;
	}
	
	public String getMNaF()
	{
		return mnaf;
	}
	
	public String getIntern()
	{
		return intern;
	}
	
	public String getExtern()
	{
		return extern;
	}
	
	public String getGeneralInstructions()
	{
		return genInstr;
	}
	
	public String getLabSetup()
	{
		return labSetup;
	}
	
	public String getDangerousSubstances()
	{
		return dangerSubst;
	}
}
