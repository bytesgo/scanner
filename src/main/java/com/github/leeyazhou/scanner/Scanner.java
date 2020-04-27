/**
 * 
 */
package com.github.leeyazhou.scanner;

/**
 * @author leeyazhou
 */
public abstract class Scanner implements ClassScanner {

	protected ScannerBuilder builder;

	public Scanner(ScannerBuilder builder) {
		this.builder = builder;
	}

	public static ScannerBuilder builder() {
		return new ScannerBuilder();
	}

	static class ScannerBuilder {

		private String basePackage;
		private ClassLoader classLoader;

		public Scanner build() {
			return new DefaultClassScanner(this);
		}

		public String getBasePackage() {
			return basePackage;
		}

		/**
		 * @param classLoader the classLoader to set
		 */
		public ScannerBuilder setClassLoader(ClassLoader classLoader) {
			this.classLoader = classLoader;
			return this;
		}

		/**
		 * @return the classLoader
		 */
		public ClassLoader getClassLoader() {
			return classLoader;
		}

		/**
		 * @param basePackage the basePackage to set
		 */
		public ScannerBuilder setBasePackage(String basePackage) {
			this.basePackage = basePackage;
			return this;
		}

	}

}
