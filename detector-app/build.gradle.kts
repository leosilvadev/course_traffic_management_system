plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "com.github.leosilvadev"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.commons:commons-lang3")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework:spring-web")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("io.projectreactor:reactor-core")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mock-server:mockserver-junit-jupiter:5.15.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
