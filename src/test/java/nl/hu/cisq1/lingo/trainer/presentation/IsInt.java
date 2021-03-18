package nl.hu.cisq1.lingo.trainer.presentation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

//gejat van https://stackoverflow.com/a/58100096
public class IsInt extends TypeSafeMatcher<String> {

	@Override
	protected boolean matchesSafely(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("is int");
	}

	public static Matcher<String> isInt() {
		return new IsInt();
	}
}

