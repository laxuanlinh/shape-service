package com.linhlx.shapeservice.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ShapeCategoryTest {

    private ShapeCategory shapeCategory;

    @Test
    public void shouldCreateShapeCategory(){
        whenCreateCategory();
        categoryShouldBeCreatedWithProperties();
    }

    private void categoryShouldBeCreatedWithProperties() {
        assertEquals("Rectangle", shapeCategory.getShapeCategoryName());
        assertEquals("width * length", shapeCategory.getFormula());
        assertEquals("width > 0 && length > 0", shapeCategory.getRules());
        assertEquals(2, shapeCategory.getDimensions().size());
        assertNotNull(shapeCategory.getShapes());
        assertEquals(1, shapeCategory.getConditionsOtherCategories().size());
    }

    private void whenCreateCategory() {
        Map<String,String> conditions = Maps.newHashMap();
        conditions.put("Square", "width == length");
        shapeCategory = new ShapeCategory("Rectangle",
                Sets.newHashSet(Arrays.asList("width", "length")),
                Lists.newArrayList(),
                "width * length",
                "width > 0 && length > 0",
                conditions);
        shapeCategory.setShapes(Lists.newArrayList());
    }


}
