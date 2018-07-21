package aiwolf_kwb;

import org.aiwolf.sample.lib.AbstractRoleAssignPlayer;

public class kwb_RoleAssignPlayer extends AbstractRoleAssignPlayer {

	public kwb_RoleAssignPlayer() {
		setWerewolfPlayer(new kwb_Werewolf());
		setVillagerPlayer(new kwb_Villager());
		setBodyguardPlayer(new kwb_Bodyguard());
		setMediumPlayer(new kwb_Medium());
		setPossessedPlayer(new kwb_Possessed());
		setSeerPlayer(new kwb_Seer());
	}
	@Override
	public String getName() {
		return "riverside";
	}
	
}
