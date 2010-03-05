package fr.viveladietetique.util;

public interface Adaptable {
	<T> T adaptTo(Class<T> type);
}
