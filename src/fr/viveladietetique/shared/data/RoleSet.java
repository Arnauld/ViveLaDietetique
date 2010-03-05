package fr.viveladietetique.shared.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RoleSet {

	public static RoleSet wrap(Iterable<Role> roles) {
		RoleSet set = new RoleSet ();
		for(Role role : roles)
			set.addRole(role);
		return set;
	}

	private Set<Role> roleSet = new HashSet<Role>();
	
	public boolean hasRole(Role role) {
		return roleSet.contains(role);
	}

	public boolean noneOf(Role... roles) {
		return noneOf(Arrays.asList(roles));
	}
	
	public boolean noneOf(Iterable<Role> roles) {
		for(Role role : roles)
		{
			if(roleSet.contains(role))
				return false;
		}
		return true;
	}
	
	public boolean allOf(Role... roles) {
		return allOf(Arrays.asList(roles));
	}
	
	public boolean allOf(Iterable<Role> roles) {
		for(Role role : roles)
		{
			if(!roleSet.contains(role))
				return false;
		}
		return true;
	}

	public boolean oneOf(Role... roles) {
		return oneOf(Arrays.asList(roles));
	}
	
	public boolean oneOf(Iterable<Role> roles) {
		for(Role role : roles)
		{
			if(roleSet.contains(role))
				return true;
		}
		return false;
	}
	
	public boolean sameAs(Role...roles) {
		if(roleSet.size()!=roles.length)
			return false;
		for(Role role : roles)
			if(!roleSet.contains(role))
				return false;
		return true;
	}
	
	public void addRole(Role role) {
		roleSet.add(role);
	}

	public int count() {
		return roleSet.size();
	}


}
