package fr.viveladietetique.server.core;

import java.util.List;

import fr.viveladietetique.shared.data.Role;
import fr.viveladietetique.shared.data.RoleSet;
import fr.viveladietetique.util.New;

public class AppUser {

	public static final String USER_KEY = "app-user";
	
	public static AppUser getAnonymous() {
		AppUser anonymous = new AppUser();
		anonymous.getRoles().addRole(Role.Anonymous);
		return anonymous;
	}
	
	private RoleSet roles = new RoleSet() ;
	private List<Profile.ID> profiles = New.arrayList();
	
	public boolean hasRole (Role role) {
		return roles.hasRole(role);
	}
	public RoleSet getRoles() {
		return roles;
	}
	public List<Profile.ID> getProfiles() {
		return profiles;
	}
	public boolean isAnonymous() {
		// only the anonymous role
		return roles.sameAs(Role.Anonymous);
	}
	public int getRoleCount() {
		return roles.count();
	}
}
