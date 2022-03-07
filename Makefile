all: run

clean:
	rm -f out/Main.jar out/Algorithm.jar

out/Main.jar: out/parcs.jar src/Main.java src/Image_SRZ.java
	@javac -cp out/parcs.jar src/Main.java src/Image_SRZ.java
	@jar cf out/Main.jar -C src Main.class -C src Image_SRZ.class
	@rm -f src/Main.class src/Image_SRZ.class

out/Algorithm.jar: out/parcs.jar src/Algorithm.java src/Image_SRZ.java
	@javac -cp out/parcs.jar src/Algorithm.java src/Image_SRZ.java
	@jar cf out/Algorithm.jar -C src Algorithm.class -C src Image_SRZ.class
	@rm -f src/Algorithm.class src/Image_SRZ.class

build: out/Main.jar out/Algorithm.jar

run: out/Main.jar out/Algorithm.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main
