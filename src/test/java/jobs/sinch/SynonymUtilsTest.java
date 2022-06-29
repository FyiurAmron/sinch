package jobs.sinch;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SynonymUtilsTest {

    @Test
    void identifiesSynonyms() {
        List<QueryPair> queries = new ArrayList<>() {
            {
                add( new QueryPair( "previous location eurovision", "last venue eurovision" ) );
                add( new QueryPair( "previous locations eurovision", "last venue eurovision" ) );
                add( new QueryPair( "previous location eurovision", "eurovision last venue" ) );
            }
        };

        Map<String, String> synonyms = new HashMap<>() {
            {
                put( "previous", "last" );
                put( "location", "venue" );
            }
        };

        List<Boolean> result = SynonymUtils.areSynonymous( queries, synonyms );
        List<Boolean> expectedResult = Arrays.stream( new Boolean[]{ true, false, false } ).toList();

        assertEquals( expectedResult, result );
    }
}
