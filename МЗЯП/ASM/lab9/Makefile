CC := gcc-10
ASM := nasm
FLAGS := -std=c99 -w -o0 #-masm=intel
OPTION := -m80387

SRCS := $(wildcard *.c)
OBJS := $(SRCS:%.c=%.o)

app.exe: FLAGS += -DFPUX87 $(OPTION)
app.exe: $(OBJS) *.h
	$(CC) $(FLAGS) $(OBJS) -o  $@
	rm -f *.o

%.o: %.c *.h 
	$(CC) $(FLAGS) -c $< -o $@
clean:
	rm -f *.o *.exe 