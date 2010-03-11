package fr.viveladietetique.server.core;

public interface AppUserDao {
	AppUser findByLogin (String login);
}
