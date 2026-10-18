SRC := $(shell find src -name '*.java')
OUT := out

.PHONY: build run clean

build:
	javac -d $(OUT) $(SRC)

run: build
	java -cp $(OUT) Main

clean:
	rm -rf $(OUT)