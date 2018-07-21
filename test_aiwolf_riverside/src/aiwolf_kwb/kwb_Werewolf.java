package aiwolf_kwb;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;
import org.aiwolf.sample.lib.AbstractWerewolf;


@SuppressWarnings("deprecation")
public class kwb_Werewolf extends AbstractWerewolf {
	GameInfo currentGameInfo;
	Agent attack;
	
	@Override
	public Agent attack() {
		// TODO Auto-generated method stub
		Agent tmp = null;
		attack = currentGameInfo.getLastDeadAgentList().get(0);
		if(attack.equals(null)){
			System.err.println("NULL!!!!!!!!!!");
		}
		else {
			tmp = attack;
		}
		return tmp;
	}

	@Override
	public void dayStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(GameInfo arg0, GameSetting arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String talk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(GameInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Agent vote() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String whisper() {
		// TODO Auto-generated method stub
		return null;
	}

}
