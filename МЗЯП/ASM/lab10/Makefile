CC := gcc
ASM := nasm
FLAGS := -std=c99 -w -o0 #-masm=intel

SRCS := $(wildcard *.c)
OBJS := $(SRCS:%.c=%.o)

app.exe: FLAGS += -DFPUX87
app.exe: $(OBJS)
	$(CC) $(FLAGS) $(OBJS) -o  $@
	rm -f *.o

%.o: %.c *.h 
	$(CC) $(FLAGS) -c $< -o $@
clean:
	rm -f *.o *.exe 