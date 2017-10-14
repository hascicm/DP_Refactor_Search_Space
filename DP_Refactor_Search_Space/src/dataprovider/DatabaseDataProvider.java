package dataprovider;

import java.util.ArrayList;
import java.util.List;

import dataprovider.dbsManager.PostgresManager;
import entities.Repair;
import entities.SmellType;
import entities.stateSpace.SmellOccurance;
import entities.stateSpace.State;

public class DatabaseDataProvider implements DataProvider {

	private List<Repair> repairs = null;
	private List<SmellType> smells = null;
	private State root;

	public DatabaseDataProvider() {
		smells = PostgresManager.getInstance().getSmellTypes();
		repairs = PostgresManager.getInstance().getRepairs(smells);
		initRoot();
	}

	@Override
	public List<Repair> getRepairs() {
		return repairs;
	}

	@Override
	public List<SmellType> getSmellTypes() {
		return smells;
	}

	@Override
	public State getRootState() {
		return root;
	}

	private void initRoot(){
		
		List<SmellOccurance> smellOccurances = new ArrayList<SmellOccurance>();
				
		//DataClumps
		smellOccurances.add(new SmellOccurance(this.smells.get(3)));
		//LazyClass
		smellOccurances.add(new SmellOccurance(this.smells.get(8)));
		//FeatureEnvy
		smellOccurances.add(new SmellOccurance(this.smells.get(9)));
		//IncompleteLibraryPath
		smellOccurances.add(new SmellOccurance(this.smells.get(6)));
		//LongParameterList
		smellOccurances.add(new SmellOccurance(this.smells.get(2)));
		//DataClass	
		smellOccurances.add(new SmellOccurance(this.smells.get(7)));
		//Large class
		smellOccurances.add(new SmellOccurance(this.smells.get(0)));
		//Large class
		smellOccurances.add(new SmellOccurance(this.smells.get(0)));
		//Divergent change
		smellOccurances.add(new SmellOccurance(this.smells.get(12)));
		//Feature envy
		smellOccurances.add(new SmellOccurance(this.smells.get(9)));
		//Spectacular Generality
		smellOccurances.add(new SmellOccurance(this.smells.get(18)));
		/*//Large Class
		smellOccurances.add(new SmellOccurance(this.smells.get(0)));
		//DataClumps
		smellOccurances.add(new SmellOccurance(this.smells.get(3)));
		//Large Class
		smellOccurances.add(new SmellOccurance(this.smells.get(0)));
		//Shotgun Surgery
		smellOccurances.add(new SmellOccurance(this.smells.get(13)));
		//Switch statement
		smellOccurances.add(new SmellOccurance(this.smells.get(14)));
		//Inpropriate intimicity
		smellOccurances.add(new SmellOccurance(this.smells.get(19)));*/
		
		
		this.root = new State();
		this.root.setSmells(smellOccurances);	
	}
}
