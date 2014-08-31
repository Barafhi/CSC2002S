JAVAC=javac
JFLAGS=-g

CLASSES=Vector.java \
	Particle.java \
	WordDictionary.java \
	Score.java \
	WordRecord.java \
	ParticleUpdater.java \
	WordUpdater.java \
	WordPanel.java \
	WordApp.java \

.SUFFIXES:.java .class

.java.class:
	$(JAVAC) $(JFLAG) $*.java

default:	classes
classes:	$(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	java WordApp 5 3 nofile
