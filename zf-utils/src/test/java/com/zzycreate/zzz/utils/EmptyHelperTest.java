package com.zzycreate.zzz.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
public class EmptyHelperTest {

    @Test
    public void isEmptyString() {
        assertTrue(EmptyHelper.isEmptyString(null));
        assertTrue(EmptyHelper.isEmptyString(""));
        assertFalse(EmptyHelper.isEmptyString(" "));
        assertFalse(EmptyHelper.isEmptyString(" \t"));
    }

    @Test
    public void isNotEmptyString() {
        assertFalse(EmptyHelper.isNotEmptyString(null));
        assertFalse(EmptyHelper.isNotEmptyString(""));
        assertTrue(EmptyHelper.isNotEmptyString(" "));
        assertTrue(EmptyHelper.isNotEmptyString(" \t"));
    }

    @Test
    public void isEmptyArray() {
        assertTrue(EmptyHelper.isEmptyArray(null));
        assertTrue(EmptyHelper.isEmptyArray(new String[]{}));
        assertFalse(EmptyHelper.isEmptyArray(new String[]{null}));
    }

    @Test
    public void isNotEmptyArray() {
        assertFalse(EmptyHelper.isNotEmptyArray(null));
        assertFalse(EmptyHelper.isNotEmptyArray(new String[]{}));
        assertTrue(EmptyHelper.isNotEmptyArray(new String[]{null}));
    }

    @Test
    public void isEmptyList() {
        assertTrue(EmptyHelper.isEmptyList(null));
        assertTrue(EmptyHelper.isEmptyList(new ArrayList<>()));
        assertFalse(EmptyHelper.isEmptyList(CastHelper.toList((String) null)));
    }

    @Test
    public void isNotEmptyList() {
        assertFalse(EmptyHelper.isNotEmptyList(null));
        assertFalse(EmptyHelper.isNotEmptyList(new ArrayList<>()));
        assertTrue(EmptyHelper.isNotEmptyList(CastHelper.toList((String) null)));
    }

    @Test
    public void isEmptySet() {
        assertTrue(EmptyHelper.isEmptySet(null));
        assertTrue(EmptyHelper.isEmptySet(new HashSet<>()));
        assertFalse(EmptyHelper.isEmptySet(CastHelper.toSet((String) null)));
    }

    @Test
    public void isNotEmptySet() {
        assertFalse(EmptyHelper.isNotEmptySet(null));
        assertFalse(EmptyHelper.isNotEmptySet(new HashSet<>()));
        assertTrue(EmptyHelper.isNotEmptySet(CastHelper.toSet((String) null)));
    }

    @Test
    public void isEmptyCollection() {
        assertTrue(EmptyHelper.isEmptyCollection(null));
        assertTrue(EmptyHelper.isEmptyCollection(new ArrayList<>()));
        assertFalse(EmptyHelper.isEmptyCollection(CastHelper.toList((String) null)));
        assertTrue(EmptyHelper.isEmptyCollection(null));
        assertTrue(EmptyHelper.isEmptyCollection(new HashSet<>()));
        assertFalse(EmptyHelper.isEmptyCollection(CastHelper.toSet((String) null)));
    }

    @Test
    public void isNotEmptyCollection() {
        assertFalse(EmptyHelper.isNotEmptyCollection(null));
        assertFalse(EmptyHelper.isNotEmptyCollection(new ArrayList<>()));
        assertTrue(EmptyHelper.isNotEmptyCollection(CastHelper.toList((String) null)));
        assertFalse(EmptyHelper.isNotEmptyCollection(null));
        assertFalse(EmptyHelper.isNotEmptyCollection(new HashSet<>()));
        assertTrue(EmptyHelper.isNotEmptyCollection(CastHelper.toSet((String) null)));
    }

    @Test
    public void isEmptyMap() {
        assertTrue(EmptyHelper.isEmptyMap(null));
        assertTrue(EmptyHelper.isEmptyMap(new HashMap<>(0)));
    }

    @Test
    public void isEmpty() {
        assertTrue(EmptyHelper.isEmpty(null));
        assertTrue(EmptyHelper.isEmpty(""));
        assertFalse(EmptyHelper.isEmpty(" "));
        assertFalse(EmptyHelper.isEmpty(" \t"));

        assertTrue(EmptyHelper.isEmpty(null));
        assertTrue(EmptyHelper.isEmpty(new String[]{}));
        assertFalse(EmptyHelper.isEmpty(new String[]{null}));

        assertTrue(EmptyHelper.isEmpty(null));
        assertTrue(EmptyHelper.isEmpty(new ArrayList<>()));
        assertFalse(EmptyHelper.isEmpty(CastHelper.toList((String) null)));
        assertTrue(EmptyHelper.isEmpty(null));
        assertTrue(EmptyHelper.isEmpty(new HashSet<>()));
        assertFalse(EmptyHelper.isEmpty(CastHelper.toSet((String) null)));

        assertTrue(EmptyHelper.isEmpty(null));
        assertTrue(EmptyHelper.isEmpty(new HashMap<>(0)));
    }

    @Test
    public void isNotEmpty() {
        assertFalse(EmptyHelper.isNotEmpty(null));
        assertFalse(EmptyHelper.isNotEmpty(""));
        assertTrue(EmptyHelper.isNotEmpty(" "));
        assertTrue(EmptyHelper.isNotEmpty(" \t"));

        assertFalse(EmptyHelper.isNotEmpty(null));
        assertFalse(EmptyHelper.isNotEmpty(new String[]{}));
        assertTrue(EmptyHelper.isNotEmpty(new String[]{null}));

        assertFalse(EmptyHelper.isNotEmpty(null));
        assertFalse(EmptyHelper.isNotEmpty(new ArrayList<>()));
        assertTrue(EmptyHelper.isNotEmpty(CastHelper.toList((String) null)));
        assertFalse(EmptyHelper.isNotEmpty(null));
        assertFalse(EmptyHelper.isNotEmpty(new HashSet<>()));
        assertTrue(EmptyHelper.isNotEmpty(CastHelper.toSet((String) null)));

        assertFalse(EmptyHelper.isNotEmpty(null));
        assertFalse(EmptyHelper.isNotEmpty(new HashMap<>(0)));
    }

    @Test
    public void isBlankString() {
        assertTrue(EmptyHelper.isBlankString(null));
        assertTrue(EmptyHelper.isBlankString(""));
        assertTrue(EmptyHelper.isBlankString(" "));
        assertTrue(EmptyHelper.isBlankString(" \t"));
    }

    @Test
    public void isNotBlankString() {
        assertFalse(EmptyHelper.isNotBlankString(null));
        assertFalse(EmptyHelper.isNotBlankString(""));
        assertFalse(EmptyHelper.isNotBlankString(" "));
        assertFalse(EmptyHelper.isNotBlankString(" \t"));
    }

    @Test
    public void isBlank() {
        assertTrue(EmptyHelper.isBlank(null));
        assertTrue(EmptyHelper.isBlank(""));
        assertTrue(EmptyHelper.isBlank(" "));
        assertTrue(EmptyHelper.isBlank(" \t"));
        assertTrue(EmptyHelper.isBlank(new ArrayList<>()));
    }

    @Test
    public void isNotBlank() {
        assertFalse(EmptyHelper.isNotBlank(null));
        assertFalse(EmptyHelper.isNotBlank(""));
        assertFalse(EmptyHelper.isNotBlank(" "));
        assertFalse(EmptyHelper.isNotBlank(" \t"));
        assertFalse(EmptyHelper.isNotBlank(new ArrayList<>()));
    }
}