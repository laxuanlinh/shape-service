package com.linhlx.shapeservice.dto;

import com.google.common.collect.Maps;
import org.assertj.core.util.Sets;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShapeCategoryDTOTest {

    private ShapeCategoryDTO shapeCategoryDTO;

    @Test
    public void shouldCreateShapeCategoryDTO(){
        whenCreateShapeCategoryDTO();
        shapeCategoryDTOShouldBeCreatedWithProperties();
    }

    private void shapeCategoryDTOShouldBeCreatedWithProperties() {
        assertEquals("Rectangle", shapeCategoryDTO.getShapeCategoryName());
        assertNotNull(shapeCategoryDTO.getDimensions());
        assertNotNull(shapeCategoryDTO.getConditionsOtherCategories());
        assertEquals("width * length", shapeCategoryDTO.getFormula());
        assertEquals("width > 0 && length > 0", shapeCategoryDTO.getRules());
    }

    private void whenCreateShapeCategoryDTO() {
        shapeCategoryDTO = new ShapeCategoryDTO();
        shapeCategoryDTO.setShapeCategoryName("Rectangle");
        shapeCategoryDTO.setFormula("width * length");
        shapeCategoryDTO.setRules("width > 0 && length > 0");
        shapeCategoryDTO.setDimensions(Sets.newHashSet());
        shapeCategoryDTO.setConditionsOtherCategories(Maps.newHashMap());
    }

}
