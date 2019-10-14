package com.linhlx.shapeservice.dto;

import com.google.common.collect.Maps;
import org.assertj.core.util.Sets;
import org.junit.Test;

import static java.lang.Double.valueOf;
import static org.junit.Assert.*;

public class AreaDTOTest {

    private AreaDTO areaDTO;

    @Test
    public void shouldCreateAreaDTO(){
        whenCreateAreaDTO();
        areaDTOShouldBeCreatedWithProperties();
    }

    private void whenCreateAreaDTO() {
        areaDTO = new AreaDTO();
        areaDTO.setArea(100.0);
        ShapeCategoryDTO shapeCategoryDTO = new ShapeCategoryDTO("Rectangle",
                Sets.newHashSet(),
                "formula",
                "rules",
                Maps.newHashMap());
        areaDTO.setPossibleCategory(shapeCategoryDTO);
    }

    private void areaDTOShouldBeCreatedWithProperties(){
        assertTrue(valueOf(100.0).equals(areaDTO.getArea()));
        assertEquals("Rectangle", areaDTO.getPossibleCategory().getShapeCategoryName());
    }

}
