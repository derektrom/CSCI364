CXX = g++
CXXFLAGS = -g -Wall -std=c++11 -fopenmp

INCLUDES =
LDFLAGS  =
LDLIBS   =

all: pi pisync piredux1 piredux2 piredux3 pireduxbad
	@echo All pi programs compiled

pi: pi.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

pisync: pisync.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

piredux1: piredux1.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

piredux2: piredux2.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

piredux3: piredux3.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

pireduxbad: pireduxbad.cpp
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ $? $(LDFLAGS) $(LDLIBS)

.PHONY: clean
clean:
	$(RM) -r pi pisync piredux1 piredux2 piredux3 pireduxbad


