import java.util.*;

public class Agent 
{
	private int agentNum;
	private boolean active;
	private int vision;
	private int metabolism;
	private double wealth;
	private double beta;
	Random rn = new Random();
	
	public Agent(int agentNum, int vision, int metabolism, double wealth, double beta)
	{
		this.agentNum = agentNum;
		this.vision = vision;
		this.metabolism = metabolism;
		this.wealth = wealth;
		this.beta = beta;
		this.active = true;
	}
	
	public int getAgentNum()
	{
		return agentNum;
	}
	
	public double getWealth()
	{
		return wealth;
	}
	
	public void setWealth(double newWealth)
	{
		this.wealth = newWealth;
	}
	
	public boolean getStatus()
	{
		return active;
	}
	
	public int getVision()
	{
		return vision;
	}
	
	public double getBeta()
	{
		return beta;
	}
	
	public int getMetabolism()
	{
		return metabolism;
	}
	
	public void kill()
	{
		this.active = false;
	}
}
