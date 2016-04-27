package api;

import filmr.domain.Movie;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by luffarvante on 2016-04-26.
 */
public class MockitoTutorial {

    @Test
    public void test1() {
        Movie test = Mockito.mock(Movie.class);

        when(test.getId()).thenReturn(new Long(1));

        assertEquals(test.getId(), new Long(1));
    }

    @Test
    public void testMoreThanOneReturnValue() {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("BOBBY").thenReturn("BOB");
        String result = i.next()+" "+i.next();
        assertEquals("BOBBY BOB", result);
    }

    @Test
    public void testReturnValueDependentOnMethodParameter() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo("BOB")).thenReturn(1);
        when(c.compareTo("BOBBY")).thenReturn(2);
        assertEquals(1, c.compareTo("BOB"));
    }

    @Test
    public void testReturnValueInDependentOnMethodParameter() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        assertEquals(-1, c.compareTo(9));
    }

    @Test(expected = IOException.class)
    public void testForIOException() {
        OutputStream mockStream = mock(OutputStream.class);
        try {
            doThrow(new IOException()).when(mockStream).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStreamWriter steamWriter = new OutputStreamWriter(mockStream);
        try {
            steamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerify() {
        Movie test = Mockito.mock(Movie.class);
        when(test.getTitle()).thenReturn("Bob in da house");

        test.setDescription("Yo Homes");
        test.getDescription();
        test.getDescription();

        verify(test).setDescription(Matchers.eq("Yo Homes"));
        verify(test, times(2)).getDescription();

        verify(test, never()).setDescription("never called");
        verify(test, atLeastOnce()).setDescription("calle at least once");
        verify(test, atLeast(2)).setDescription("called at least twice");
        verify(test, times(5)).setDescription("called five times");
        verify(test, atMost(3)).setDescription("called at most three times");
    }
}
