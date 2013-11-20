import java.io.*;

public class AgentModel 
{
	public static void main(String[] args) throws IOException
	{
		SugarScape sg = new SugarScape(50, 10, 0.9);
		sg.outputSugarScapeCSV("sgCSV.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV.csv");
		sg.performRounds(1);
		sg.outputSugarScapeWealthCSV("sgWealthCSV.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-100.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-100.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-200.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-200.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-300.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-300.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-400.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-400.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-500.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-500.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-600.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-600.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-700.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-700.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-800.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-800.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-900.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-900.csv");
		sg.performRounds(100);
		sg.outputSugarScapeWealthCSV("sgWealthCSV-1000.csv");
		sg.outputSugarScapeHistoryLengthCSV("sgHistoryLengthCSV-1000.csv");
	}
}