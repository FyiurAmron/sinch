package jobs.sinch;

import java.util.List;
import java.util.Map;

record QueryPair( String first, String second ) {

}

public class SynonymUtils {
    public static String[] splitQuery( String query ) {
        return query.trim().split( "\\s+" );
    }

    // ??? how is the data provided exactly (serialization format etc.)

    // ??? can we have (and how should we handle) multiple (nested) synonyms? (cyclic graph problems etc.)
    // ??? how should we handle plurals/particles etc.
    // ??? how should we handle word order
    // ??? is this performance-critical or ease-of-maintenance code? (assuming performance)
    public static boolean areSynonymous( String query1, String query2, Map<String, String> synonyms ) {
        String[] splitQuery1 = splitQuery( query1 );
        String[] splitQuery2 = splitQuery( query2 );

        if ( splitQuery1.length != splitQuery2.length ) {
            return false;
        }

        for ( int i = 0; i < splitQuery1.length; i++ ) {
            String word1 = splitQuery1[i];
            String word1synonym = synonyms.get( word1 );
            if ( word1synonym != null ) {
                word1 = word1synonym;
            }

            String word2 = splitQuery2[i];
            String word2synonym = synonyms.get( word1 );
            if ( word2synonym != null ) {
                word2 = word2synonym;
            }

            if ( !word1.equals( word2 ) ) {
                return false;
            }
        }

        return true;
    }

    public static List<Boolean> areSynonymous( List<QueryPair> queries, Map<String, String> synonyms ) {
        return queries.stream().map( ( query ) -> areSynonymous( query.first(), query.second(), synonyms ) ).toList();
    }
}
