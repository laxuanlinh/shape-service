package com.linhlx.shapeservice.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.dto.ShapeDTO;
import com.linhlx.shapeservice.exception.ShapeException;
import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.repository.ShapeCategoryRepository;
import com.linhlx.shapeservice.repository.ShapeRepository;
import com.linhlx.shapeservice.repository.UserRepository;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeServiceTest {

    private ShapeService shapeService;
    private UserRepository userRepository;
    private ShapeCategoryRepository shapeCategoryRepository;
    private ShapeRepository shapeRepository;
    private List<ShapeDTO> shapeDTOS;
    private Shape shape;
    private Shape createdShape;
    private ShapeCategory rectangleShapeCategory;
    private ShapeCategory circleShapeCategory;
    private List<ShapeCategoryDTO> categoryDTOS;
    private Shape calculatedShape;
    private AreaDTO areaDTO;
    private User user;

    @Before
    public void setUp(){
        shapeRepository = mock(ShapeRepository.class);
        shapeCategoryRepository = mock(ShapeCategoryRepository.class);
        userRepository = mock(UserRepository.class);
        shapeService = new ShapeServiceImpl(shapeCategoryRepository, shapeRepository, userRepository);

        user = new User("username", "password", true, new Role());

        Shape shape1 = new Shape(1l, "test shape1", Maps.newHashMap(), new ShapeCategory(), user);
        Shape shape2 = new Shape(2l, "test shape2", Maps.newHashMap(), new ShapeCategory(), user);
        List<Shape> shapes = Lists.newArrayList(shape1, shape2);
        when(shapeRepository.findAll()).thenReturn(shapes);

        rectangleShapeCategory = new ShapeCategory();
        rectangleShapeCategory.setShapeCategoryName("Rectangle");
        rectangleShapeCategory.setDimensions(Sets.newHashSet(Arrays.asList("width", "length")));
        rectangleShapeCategory.setFormula("width * length");
        rectangleShapeCategory.setRules("width > 0,length > 0");
        when(shapeCategoryRepository.findById(anyString())).thenReturn(Optional.of(rectangleShapeCategory));

        Map<String, Double> sizes = Maps.newHashMap();
        sizes.put("width", 100.0);
        sizes.put("length", 200.0);
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user);
        when(shapeRepository.save(shape)).thenReturn(shape);

        ShapeCategory shapeCategory1 = new ShapeCategory();
        shapeCategory1.setShapeCategoryName("Rectangle");
        ShapeCategory shapeCategory2 = new ShapeCategory();
        shapeCategory2.setShapeCategoryName("Circle");
        when(shapeCategoryRepository.findAll()).thenReturn(Lists.newArrayList(shapeCategory1, shapeCategory2));

        circleShapeCategory = new ShapeCategory();
        circleShapeCategory.setShapeCategoryName("Rectangle");
        circleShapeCategory.setDimensions(Sets.newHashSet(Arrays.asList("radius")));
        circleShapeCategory.setFormula("radius * radius * 3.14");
        circleShapeCategory.setRules("radius > 0");
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList(circleShapeCategory));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
    }

    @Test
    public void shouldReturnAllShapeDTOS(){
        whenGetAllShapes();
        shouldGetAllShapeDTOS();
    }

    @Test
    public void shouldCreateShapeForOtherUser(){
        whenCreateShapeForOtherUser();
        shouldReturnShape();
    }

    @Test
    public void shouldCreateShapeWithCurrentUser(){
        whenCreateShapeWithCurrentUser();
        shouldReturnShape();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenShapeIsInvalid(){
        givenShapeWithOnlyLength();
        whenCreateShape();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenShapeHasTooManySizes(){
        givenShapeWithTooManySizes();
        whenCreateShape();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenCategoryNotFound(){
        givenShapeWithNotFoundCategory();
        whenCreateShape();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenShapeIsNull(){
        givenShapeIsNull();
        whenCreateShapeForOtherUser();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenShapeIsNullAndCreateForCurrentUser(){
        givenShapeIsNull();
        whenCreateShapeWithCurrentUser();
    }

    @Test
    public void shouldReturnAllCategoryDTOs(){
        whenGetAllShapeCategories();
        shouldGetAllCategories();
    }

    @Test
    public void shouldCalculateArea(){
        givenCalculatedShape();
        withRadius(10.0);
        whenGetArea();
        areaShouldBe(314.0);
        possibleCategoryShouldBe("Rectangle");
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenCalculateAndCategoryNotFound(){
        givenCalculatedShape();
        withRadius(10.0);
        givenInvalidCateogory();
        whenGetArea();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenFormulaInvalid(){
        givenCalculatedShape();
        withRadius(10.0);
        butFormulaInvalid();
        whenGetArea();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenNotSatisfyRules(){
        givenCalculatedShape();
        withRadius(-10.0);
        whenGetArea();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenRulesInvalid(){
        givenCalculatedShape();
        withRadius(10.0);
        butRulesInvalid();
        whenGetArea();
    }

    private void whenCreateShapeWithCurrentUser() {
        createdShape = shapeService.createShapeForCurrentUser(shape, "username");
    }

    private void whenCreateShapeForOtherUser() {
        createdShape = shapeService.createShapeForOtherUser(shape);
    }

    private void butRulesInvalid() {
        circleShapeCategory.setRules("radius > 0 abcabc");
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList(circleShapeCategory));
    }

    private void butFormulaInvalid() {
        circleShapeCategory.setFormula("radius * radius * 3.14 abcabc");
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList(circleShapeCategory));
    }

    private void givenInvalidCateogory() {
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList());
    }

    private void possibleCategoryShouldBe(String name) {
        assertEquals(name, areaDTO.getPossibleCategory().getShapeCategoryName());
    }

    private void areaShouldBe(Double value) {
        assertTrue(Double.valueOf(value).equals(areaDTO.getArea()));
    }

    private void givenCalculatedShape() {
        Map<String, Double> sizes = Maps.newHashMap();
        calculatedShape = new Shape();
        calculatedShape.setShapeName("Calculated shape");
        calculatedShape.setSizes(sizes);
    }

    private void withRadius(Double radius){
        calculatedShape.getSizes().put("radius", radius);
    }

    private void whenGetArea(){
        areaDTO = shapeService.getArea(calculatedShape);
    }

    private void whenGetAllShapeCategories() {
        categoryDTOS = shapeService.getAllShapeCategories();
    }

    private void shouldGetAllCategories(){
        assertEquals(2, categoryDTOS.size());

    }

    private void givenShapeWithTooManySizes() {
        Map<String, Double> sizes = Maps.newHashMap();
        sizes.put("width", 100.0);
        sizes.put("length", 200.0);
        sizes.put("radius", 90.0);
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user);
    }

    private void givenShapeIsNull() {
        shape = null;
    }

    private void givenShapeWithNotFoundCategory() {
        when(shapeCategoryRepository.findById(anyString())).thenReturn(Optional.empty());
    }

    private void givenShapeWithOnlyLength() {
        Map<String, Double> sizes = Maps.newHashMap();
        sizes.put("length", 200.0);
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user);
        when(shapeRepository.save(shape)).thenReturn(shape);
    }

    private void whenCreateShape() {
        createdShape = shapeService.createShapeForOtherUser(shape);
    }

    private void givenShapeWithNoUsername() {
        shape.setUser(null);
    }

    private void shouldReturnShape(){
        assertTrue(Long.valueOf(1l).equals(createdShape.getId()));
        assertEquals("created shape", createdShape.getShapeName());
        assertEquals("username", createdShape.getUser().getUsername());
    }

    private void whenGetAllShapes() {
        shapeDTOS = shapeService.getAllShapes();
    }

    private void shouldGetAllShapeDTOS(){
        assertEquals(2, shapeDTOS.size());
        assertTrue(Long.valueOf(1l).equals(shapeDTOS.get(0).getId()));
        assertTrue(Long.valueOf(2l).equals(shapeDTOS.get(1).getId()));
        assertEquals("test shape1", shapeDTOS.get(0).getShapeName());
        assertEquals("test shape2", shapeDTOS.get(1).getShapeName());
    }

}















