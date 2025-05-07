package lv.rvt;

import static org.junit.Assert.*;
import org.junit.Test;

public class EnumsTest {

    @Test
    public void testSortCriteriaEnum() {
        // Test all enum values exist
        SortCriteria[] values = SortCriteria.values();
        assertEquals(6, values.length);
        
        // Test specific values
        assertEquals(SortCriteria.NUMBER, SortCriteria.valueOf("NUMBER"));
        assertEquals(SortCriteria.NAME, SortCriteria.valueOf("NAME"));
        assertEquals(SortCriteria.GOALS, SortCriteria.valueOf("GOALS"));
        assertEquals(SortCriteria.ASSISTS, SortCriteria.valueOf("ASSISTS"));
        assertEquals(SortCriteria.GAMES, SortCriteria.valueOf("GAMES"));
        assertEquals(SortCriteria.TOTAL_POINTS, SortCriteria.valueOf("TOTAL_POINTS"));
    }
    
    @Test
    public void testFilterCriteriaEnum() {
        // Test all enum values exist
        FilterCriteria[] values = FilterCriteria.values();
        assertEquals(2, values.length);
        
        // Test specific values
        assertEquals(FilterCriteria.NAME, FilterCriteria.valueOf("NAME"));
        assertEquals(FilterCriteria.NUMBER, FilterCriteria.valueOf("NUMBER"));
    }
    
    @Test
    public void testNumericFilterCriteriaEnum() {
        // Test all enum values exist
        NumericFilterCriteria[] values = NumericFilterCriteria.values();
        assertEquals(5, values.length);
        
        // Test specific values
        assertEquals(NumericFilterCriteria.NUMBER, NumericFilterCriteria.valueOf("NUMBER"));
        assertEquals(NumericFilterCriteria.GOALS, NumericFilterCriteria.valueOf("GOALS"));
        assertEquals(NumericFilterCriteria.ASSISTS, NumericFilterCriteria.valueOf("ASSISTS"));
        assertEquals(NumericFilterCriteria.GAMES, NumericFilterCriteria.valueOf("GAMES"));
        assertEquals(NumericFilterCriteria.TOTAL_POINTS, NumericFilterCriteria.valueOf("TOTAL_POINTS"));
    }
    
    @Test
    public void testComparisonOperatorEnum() {
        // Test all enum values exist
        ComparisonOperator[] values = ComparisonOperator.values();
        assertEquals(5, values.length);
        
        // Test specific values
        assertEquals(ComparisonOperator.EQUAL, ComparisonOperator.valueOf("EQUAL"));
        assertEquals(ComparisonOperator.GREATER_THAN, ComparisonOperator.valueOf("GREATER_THAN"));
        assertEquals(ComparisonOperator.LESS_THAN, ComparisonOperator.valueOf("LESS_THAN"));
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, ComparisonOperator.valueOf("GREATER_THAN_OR_EQUAL"));
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, ComparisonOperator.valueOf("LESS_THAN_OR_EQUAL"));
    }
}