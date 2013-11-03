import java.io.*;

public class AgentModel 
{
	public static void main(String[] args) throws IOException
	{
		SugarScape sg = new SugarScape(50, 10, 0.9);
		sg.outputSugarScapeCSV("sgCSVb.csv");
		sg.performRounds(10);
		sg.outputSugarScapeWealthCSV("sgWealthCSVb.csv");	
	}
}
