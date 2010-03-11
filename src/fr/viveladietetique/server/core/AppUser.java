package fr.viveladietetique.server.core;

import java.util.List;

import fr.viveladietetique.shared.data.Role;
import fr.viveladietetique.shared.data.RoleSet;
import fr.viveladietetique.util.New;

public class AppUser {

	public static final String USER_KEY = "app-user";
	
	public static AppUser getAnonymous() {
		AppUser anonymous = new AppUser().withRole(Role.Anonymous);
		return anonymous;
	}
	
	private RoleSet roles = new RoleSet();
	private String email, password;
	private List<Profile.ID> profiles = New.arrayList();
	
	public boolean hasRole (Role role) {
		return roles.hasRole(role);
	}
	public AppUser withRole (Role role) {
		roles.addRole(role);
		return this;
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AppUser withEmail(String email) {
		this.setEmail(email);
		return this;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AppUser withPassword(String password) {
		this.setPassword(password);
		return this;
	}
}
