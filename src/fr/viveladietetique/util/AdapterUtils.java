package fr.viveladietetique.util;

public class AdapterUtils {

	@SuppressWarnings("unchecked")
	public static <T> T adaptTo(Object object, Class<T> type) {
		if(object==null)
			return null;
		else
		if(object instanceof Adaptable)
			return adaptTo((Adaptable)object, type);
		else
		if(type.isInstance(object))
			return (T)object;
		else
			return null;
	}
	
	public static <T> T adaptTo(Adaptable adaptable, Class<T> type) {
		return (T)adaptable.adaptTo(type);
	}
}
