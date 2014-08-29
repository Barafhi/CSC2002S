JAVAC = javac
JFLAGS = -g

SOURCES = Pair.java Serial.java Parallel.java ParallelSplit.java Parallel2.java ParallelSplit2.java MaxSplit.java Chart.java Assignment_1.java

CLASSPATH = ../../lib/jcommon-1.0.22.jar:../../lib/jfreechart-1.0.18.jar:

.SUFFIXES: .java .class

.java.class:
	$(JAVAC) -cp $(CLASSPATH) $(JFLAGS) $<

Assignment_1.class: Serial.class Parallel.class Parallel2.class Chart.class Assignment_1.java
Parallel.class: ParallelSplit.class Parallel.java
Parallel2.class: ParallelSplit2.class MaxSplit.class Parallel2.java
ParallelSplit.class: Pair.class ParallelSplit.java
MaxSplit.class: Pair.class MaxSplit.java

clean:
	@rm –f $(SOURCES:.java=.class)