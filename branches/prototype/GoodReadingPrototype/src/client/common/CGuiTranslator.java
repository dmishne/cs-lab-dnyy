package client.common;

public class CGuiTranslator {

	private static CGuiTranslator GuiTranslator;
	
	
	public static CGuiTranslator getInstance()
	{
		if(GuiTranslator == null)
		{
			GuiTranslator = new CGuiTranslator();
		}
		return GuiTranslator;
	}
	
	
	
	public String TranslateForGui(Object m_serverResponce)
	{
		
		
		/*
		 *   convert Object from server to string...
		 *   what is "Object from server" ?
		 */
		
		String res = m_serverResponce.toString();
		return res;
	}
	
	
}
