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
    private Shape shape1;
    private Shape shape2;
    private List<Shape> shapes;
    private Shape createdShape;
    private ShapeCategory rectangleShapeCategory;
    private ShapeCategory circleShapeCategory;
    private List<ShapeCategoryDTO> categoryDTOS;
    private Shape calculatedShape;
    private AreaDTO areaDTO;
    private User user;
    private ShapeCategoryDTO shapeCategoryDTO;
    private List<String> otherCategories;
    private Long deletedId;

    @Before
    public void setUp(){
        shapeRepository = mock(ShapeRepository.class);
        shapeCategoryRepository = mock(ShapeCategoryRepository.class);
        userRepository = mock(UserRepository.class);
        shapeService = new ShapeServiceImpl(shapeCategoryRepository, shapeRepository, userRepository);

        user = new User("username", "password", true, new Role());

        setUpShapeCategory();
        setUpShapes();
        setUpWhen();

    }

    private void setUpShapes(){
        shape1 = new Shape(1l, "test shape1", Maps.newHashMap(), new ShapeCategory(), user, true);
        shape2 = new Shape(2l, "test shape2", Maps.newHashMap(), new ShapeCategory(), user, true);
        shapes = Lists.newArrayList(shape1, shape2);

        Map<String, Double> sizes = Maps.newHashMap();
        sizes.put("width", 100.0);
        sizes.put("length", 200.0);
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user, true);
    }

    private void setUpShapeCategory(){
        rectangleShapeCategory = new ShapeCategory();
        rectangleShapeCategory.setShapeCategoryName("Rectangle");
        rectangleShapeCategory.setDimensions(Sets.newHashSet(Arrays.asList("width", "length")));
        rectangleShapeCategory.setFormula("width * length");
        rectangleShapeCategory.setRules("width > 0 && length > 0");
        Map<String,String> conditions = Maps.newHashMap();
        conditions.put("Square", "width == length");
        rectangleShapeCategory.setConditionsOtherCategories(conditions);

        circleShapeCategory = new ShapeCategory();
        circleShapeCategory.setShapeCategoryName("Rectangle");
        circleShapeCategory.setDimensions(Sets.newHashSet(Arrays.asList("radius")));
        circleShapeCategory.setFormula("radius * radius * 3.14");
        circleShapeCategory.setRules("radius > 0");
    }

    private void setUpWhen(){
        when(shapeRepository.findAll()).thenReturn(shapes);
        when(shapeCategoryRepository.findById(anyString())).thenReturn(Optional.of(rectangleShapeCategory));
        when(shapeRepository.save(shape)).thenReturn(shape);
        when(shapeCategoryRepository.findAll()).thenReturn(Lists.newArrayList(rectangleShapeCategory, circleShapeCategory));
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList(circleShapeCategory));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(shapeRepository.findAllByUsername(anyString())).thenReturn(shapes);
        when(shapeCategoryRepository.save(any())).thenReturn(rectangleShapeCategory);
        when(shapeRepository.findById(any())).thenReturn(Optional.of(shape));
    }

    @Test
    public void shouldReturnAllShapeDTOS(){
        whenGetAllShapes();
        shouldGetAllShapeDTOS();
    }

    @Test
    public void shouldReturnShapesForUser(){
        whenGetShapesForUsers();
        shouldGetAllShapeDTOS();
    }

    private void whenGetShapesForUsers() {
        shapeDTOS = shapeService.getAllShapesForUser("username");
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
        givenCategoryNotFound();
        whenGetArea();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenFormulaInvalid(){
        givenCalculatedShape();
        withRadius(10.0);
        butFormulaInvalid();
        givenReturnCircleWhenFindByDimensions();
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
        givenReturnCircleWhenFindByDimensions();
        whenGetArea();
    }

    @Test
    public void shouldCreateCategory(){
        whenCreateCategory();
        shouldReturnCategoryDTO();
    }

    @Test
    public void shouldGetOtherCategories(){
        givenRectangleWithEvenWidthAndLength();
        whenGetOtherCategories();
        shouldReturnOtherCategories();
    }

    @Test
    public void shouldDeleteShape(){
        whenDeleteShape();
        deletedIdShouldBeReturned();
    }

    @Test(expected = ShapeException.class)
    public void shouldThrowExceptionWhenShapeNotFound(){
        givenShapeNotFound();
        whenDeleteShape();
    }

    private void givenShapeNotFound() {
        when(shapeRepository.findById(any())).thenReturn(Optional.empty());
    }

    private void whenDeleteShape() {
        deletedId = shapeService.deleteShape(1l);
    }

    private void deletedIdShouldBeReturned(){
        assertTrue(Long.valueOf(1l).equals(deletedId));
    }

    private void shouldReturnOtherCategories() {
        assertEquals("Square", otherCategories.get(0));
    }

    private void whenGetOtherCategories() {
        otherCategories = shapeService.getOtherCategories(shape);
    }

    private void givenRectangleWithEvenWidthAndLength() {
        Map<String, Double> sizes = Maps.newHashMap();
        sizes.put("width", 100.0);
        sizes.put("length", 100.0);
        shape.setSizes(sizes);
    }

    private void shouldReturnCategoryDTO() {
        assertEquals("Rectangle", shapeCategoryDTO.getShapeCategoryName());
        assertEquals("width * length", shapeCategoryDTO.getFormula());
        assertEquals("width > 0 && length > 0", shapeCategoryDTO.getRules());
    }

    private void whenCreateCategory() {
        shapeCategoryDTO = shapeService.createCategory(rectangleShapeCategory);
    }

    private void whenCreateShapeWithCurrentUser() {
        createdShape = shapeService.saveShape(shape);
    }

    private void whenCreateShapeForOtherUser() {
        createdShape = shapeService.saveShape(shape);
    }

    private void butRulesInvalid() {
        circleShapeCategory.setRules("radius > 0 abcabc");
    }

    private void givenReturnCircleWhenFindByDimensions(){
        when(shapeCategoryRepository.getShapeCategoryByDimensions(anySet())).thenReturn(Lists.newArrayList(circleShapeCategory));
    }

    private void butFormulaInvalid() {
        circleShapeCategory.setFormula("radius * radius * 3.14 abcabc");
    }

    private void givenCategoryNotFound() {
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
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user, true);
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
        shape = new Shape(1l, "created shape", sizes, rectangleShapeCategory, user, true);
//        when(shapeRepository.save(shape)).thenReturn(shape);
    }

    private void whenCreateShape() {
        createdShape = shapeService.saveShape(shape);
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















