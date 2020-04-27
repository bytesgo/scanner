/**
 * 
 */
package com.github.leeyazhou.scanner;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.leeyazhou.service.annotation.Service;

/**
 * @author leeyazhou
 */
public class ClassScannerTest {
	private Scanner scanner;

	@Before
	public void beforeAll() {
		scanner = Scanner.builder().setBasePackage("com.github.leeyazhou.service").build();
	}

	@Test
	public void testGet() {
		Set<Class<?>> classes = scanner.getByAnnotation(Service.class);
		 
		System.out.println(classes.size());
	}

	@Test
	public void testGetByAnnotation() {

	}

	@Test
	public void testGetBySuperclass() {

	}

	@Test
	public void testGetMethod() {

	}

	@Test
	public void testGetMethodByAnnotation() {

	}

	@Test
	public void testGetMethodByAnnotationInterface() {

	}

}
