import java.io.*;
import java.util.*;

class SugarScapeEntry
{
	private ArrayList<Agent> history;
	private double origSugarAmt;
	private double currentSugarAmt;
	private Agent currentAgent;
	private int x;
	private int y;
	private final static double MOVINGCOSTPCT = 0.1;
	
	public SugarScapeEntry(double origSugarAmt, Agent currentAgent, int x, int y)
	{
		history = new ArrayList<Agent>();
		this.currentAgent = currentAgent;
		this.origSugarAmt = origSugarAmt;
		this.currentSugarAmt = this.origSugarAmt;
		this.x = x;
		this.y = y;
	}
	
	public void agentLeave()
	{
		history.add(currentAgent);
		currentAgent = null;
	}
	
	public void takeover(Agent newAgent)
	{
		history.add(currentAgent);
		newAgent.setWealth((1 - MOVINGCOSTPCT) * (newAgent.getWealth() + currentAgent.getWealth()));
		currentAgent.setWealth(0);
		history.add(currentAgent);
		currentAgent.kill();
		currentAgent = newAgent;
	}
	
	public void setNewAgent(Agent newAgent)
	{
		if(currentAgent == null)
		{
			currentAgent = newAgent;
		}
		else
		{
			takeover(newAgent);
		}
	}
	
	public Agent getAgent()
	{
		return currentAgent;
	}
	
	public void depleteCurrentSugar()
	{
		currentSugarAmt = 0;
	}
	
	public void replenishCurrentSugar()
	{
		currentSugarAmt = origSugarAmt;
	}
	
	public double getCurrentSugarAmt()
	{
		return currentSugarAmt;
	}
	
	public double getOrigSugarAmt()
	{
		return origSugarAmt;
	}
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}



public class SugarScape 
{
	private SugarScapeEntry sugarscape[][];
	private final static int MAXVISION = 4;
	private final static int MAXMETABOLISM = 5;
	Random rn = new Random();
	private ArrayList<Agent> deadAgents;
	private final static double MOVINGCOSTPCT = 0.1;
	
	
	public SugarScape(int size, int maxSugar, double prop)
	{
		deadAgents = new ArrayList<Agent>();
		sugarscape = new SugarScapeEntry[size][size];
		
		int peak1x = size / 4;
		int peak1y = size / 4;
		int peak2x = (3 * size) / 4;
		int peak2y = (3 * size) / 4;
		
		int agentNumCounter = 0;
		for(int i = 0; i < sugarscape.length; i++)
		{
			for(int j = 0; j < sugarscape[0].length; j++)
			{
				int peak1Delta = Math.abs(i - peak1x) + Math.abs(j - peak1y);
				int peak2Delta = Math.abs(i - peak2x) + Math.abs(j - peak2y);
				int delta = peak1Delta < peak2Delta ? peak1Delta : peak2Delta;
				double sugarAmt = delta == 0 ? maxSugar : ((maxSugar * (1 + rn.nextDouble())) / delta);
				
				if(rn.nextDouble() < prop)
				{
					sugarscape[i][j] = new SugarScapeEntry(sugarAmt, new Agent(agentNumCounter, rn.nextInt(MAXVISION), rn.nextInt(MAXMETABOLISM), 0, rn.nextDouble()), i, j);
					agentNumCounter++;
				}
				else
				{
					sugarscape[i][j] = new SugarScapeEntry(sugarAmt, null, i, j);
				}
			}
		}
	}
	
	public void printSugarScape()
	{
		for(int i = 0; i < sugarscape.length; i++)
		{
			for (int j = 0; j < sugarscape[0].length; j++)
			{
				System.out.print(String.format("%1$.2f", sugarscape[i][j].getCurrentSugarAmt()) + " ");
			}
			System.out.println("\n");
		}
	}
	
	public void outputSugarScapeCSV(String outFileName) throws IOException
	{
		File outFile = new File(outFileName);
		if(outFile.exists())
		{
			outFile.delete();
		}
		
		PrintWriter outwriter = new PrintWriter(outFile);
		
		for(int i = 0; i < sugarscape.length; i++)
		{
			for (int j = 0; j < sugarscape[0].length; j++)
			{
				outwriter.print(sugarscape[i][j].getCurrentSugarAmt() + ",");
			}
			outwriter.print("\n");
		}
		
		outwriter.close();
	}
	
	public void printSugarScapeWealth()
	{
		for(int i = 0; i < sugarscape.length; i++)
		{
			for (int j = 0; j < sugarscape[0].length; j++)
			{
				if(sugarscape[i][j].getAgent() != null)
				{
					System.out.print(String.format("%1$7f", sugarscape[i][j].getAgent().getWealth()) + " ");
				}
				else
				{
					System.out.print("0000000 ");
				}
			}
			System.out.println("\n");
		}
	}

	public void outputSugarScapeWealthCSV(String outFileName) throws IOException
	{
		File outFile = new File(outFileName);
		if(outFile.exists())
		{
			outFile.delete();
		}
		
		PrintWriter outwriter = new PrintWriter(outFile);
		
		for(int i = 0; i < sugarscape.length; i++)
		{
			for (int j = 0; j < sugarscape[0].length; j++)
			{
				if(sugarscape[i][j].getAgent() != null)
				{
					outwriter.print(sugarscape[i][j].getAgent().getWealth() + ",");
				}
				else
				{
					outwriter.print("0,");
				}
			}
			outwriter.print("\n");
		}
		outwriter.close();
	}
	
	public void performRounds(int numRounds)
	{
		ArrayList<SugarScapeEntry> SGEntries = new ArrayList<SugarScapeEntry>();
		
		for(int i = 0; i < sugarscape.length; i++)
		{
			for(int j = 0; j < sugarscape[0].length; j++)
			{
				SGEntries.add(sugarscape[i][j]);
			}
		}
		
		System.out.println("");
		for(int i = 0; i < numRounds; i++)
		{
			Collections.shuffle(SGEntries);
			for(int SGindex = 0; SGindex < SGEntries.size(); SGindex++)
			{
				SGEntries.get(SGindex).replenishCurrentSugar();
			}
			
			for(int SGindex = 0; SGindex < SGEntries.size(); SGindex++)
			{
				Agent currentAgent = SGEntries.get(SGindex).getAgent();
				if(currentAgent != null && SGEntries.get(SGindex).getCurrentSugarAmt() > 0)
				{
					currentAgent.setWealth(currentAgent.getWealth() + SGEntries.get(SGindex).getCurrentSugarAmt() - currentAgent.getMetabolism());
					SGEntries.get(SGindex).depleteCurrentSugar();
					
					
					// If the agent's wealth < 0, the agent dies.
					if(currentAgent.getWealth() < 0)
					{
						SGEntries.get(SGindex).agentLeave();
						currentAgent.kill();
					}
					else
					{
						// This code determines the "search box for the agent to find new wealth"
						
						int leftBound, rightBound, upperBound, lowerBound, LRremain, ULremain;
						int x = SGEntries.get(SGindex).getX();
						int y = SGEntries.get(SGindex).getY();
						
						if((x - currentAgent.getVision()) < 0)
						{
							leftBound = 0;
							LRremain = currentAgent.getVision() - x;
							rightBound = x + currentAgent.getVision() + LRremain;
						}
						else if( (currentAgent.getVision() + x) > (sugarscape[0].length - 1) )
						{
							rightBound = sugarscape[0].length - 1;
							LRremain = (currentAgent.getVision() + x) - (sugarscape[0].length - 1);
							leftBound = x - currentAgent.getVision() - LRremain;
						}
						else
						{
							leftBound = x - currentAgent.getVision();
							rightBound = x + currentAgent.getVision();
						}
	
						if((y - currentAgent.getVision()) < 0)
						{
							lowerBound = 0;
							ULremain = currentAgent.getVision() - y;
							upperBound = y + currentAgent.getVision() + ULremain;
						}
						else if( (currentAgent.getVision() + y) > (sugarscape.length - 1) )
						{
							upperBound = sugarscape.length - 1;
							ULremain = (currentAgent.getVision() + y) - (sugarscape.length - 1);
							lowerBound = y - currentAgent.getVision() - ULremain;
						}
						else
						{
							lowerBound = y - currentAgent.getVision();
							upperBound = y + currentAgent.getVision();
						}
						
						
						// Within the searchbox, find the best candidate for takeover
						int bestX = leftBound;
						int bestY = lowerBound;
						for(int searchX = leftBound; searchX < rightBound; searchX++)
						{
							for(int searchY = lowerBound; searchY < upperBound; searchY++)
							{
								if((sugarscape[searchX][searchY].getOrigSugarAmt() > sugarscape[bestX][bestY].getOrigSugarAmt()))
								{
									bestX = searchX;
									bestY = searchY;
								}
							}
						}
						
						
						// There is no agent at this new location.  Move to it.
						if (sugarscape[bestX][bestY].getAgent() == null)
						{
							sugarscape[bestX][bestY].setNewAgent(currentAgent);
	
							// Deplete the current sugar so that the agent doesn't get another turn.
							sugarscape[bestX][bestY].depleteCurrentSugar();							
						}
						
						else
						{
							// If he's got better wealth than the best candidate, probabilistically take it over, based on the beta value
							if(((1 - MOVINGCOSTPCT) * sugarscape[bestX][bestY].getAgent().getWealth()) > currentAgent.getWealth())
							{
								if(rn.nextDouble() < currentAgent.getBeta())
								{
									// Agents : If they "die", append to the sugarscapeentry history arrayList, call their kill() method, and then set entry to null.
									
									deadAgents.add(sugarscape[bestX][bestY].getAgent());
									sugarscape[x][y].agentLeave();
									
									// Automatically kills old agent and adds it to history.  New agent takes wealth (minus moving costs).
									sugarscape[bestX][bestY].setNewAgent(currentAgent);
									
									// Deplete the current sugar so that the agent doesn't get another turn.
									sugarscape[bestX][bestY].depleteCurrentSugar();							
								}
							}
						}
				    }
				}
			}
		}
	}
}

